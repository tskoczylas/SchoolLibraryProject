package com.tomsapp.Toms.V2.controller;

import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.http.HttpResponse;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.Order;
import com.tomsapp.Toms.V2.dto.BorrowDto;
import com.tomsapp.Toms.V2.entity.Borrow;
import com.tomsapp.Toms.V2.enums.BorrowStatusEnum;
import com.tomsapp.Toms.V2.paypal.PaypalService;
import com.tomsapp.Toms.V2.repository.BorrowRepository;
import com.tomsapp.Toms.V2.service.BorrowService;
import com.tomsapp.Toms.V2.service.EmailService;
import com.tomsapp.Toms.V2.service.StudentService;
import com.tomsapp.Toms.V2.session.BasketSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Optional;
import static com.tomsapp.Toms.V2.utils.Message.createMessageMap;
import static com.tomsapp.Toms.V2.utils.Message.createMessageMap2Links;

@Controller
public class PaymentController {

    @Value("${host.name}")
    String hostName;

   protected BasketSession basketSession;
    protected StudentService studentService;
   protected BorrowService borrowService;
    protected EmailService emailService;
    protected PaypalService paypalService;
   protected BorrowRepository borrowRepository;

   protected final String cancelPaypalUrl="/cancel";
    protected final String successPaypalUrl="/payment_success";

    @Autowired
    public PaymentController(BasketSession basketSession, StudentService studentService, BorrowService borrowService, EmailService emailService, PaypalService paypalService, BorrowRepository borrowRepository) {
        this.basketSession = basketSession;
        this.studentService = studentService;
        this.borrowService = borrowService;
        this.emailService = emailService;
        this.paypalService = paypalService;
        this.borrowRepository = borrowRepository;
    }

    @GetMapping("/process")

    public String processOrder(Model model) {
        if (basketSession.isEmpty()) {
            //tile //text  //link //link text
            String message = "Sorry;" +
                    "You can not process with empty basket;" +
                    "/checkout;" +
                    "Go back to basket";
            model.addAllAttributes(createMessageMap(message));

        } else if (borrowService.logInStudentHasRightToOrder() && borrowService.createBorrow().isPresent()) {

            BorrowDto borrowDto = borrowService.saveBorrowDto(borrowService.createBorrow().get());
            try {

                HttpResponse<Order> response = paypalService.createPaymentHttpRes(borrowDto,hostName +successPaypalUrl,hostName +cancelPaypalUrl);
                String successHref=null;
                if(response!=null ){
                    for (LinkDescription link : response.result().links()) {
                        if(link.rel().equals("approve")){
                            successHref=link.href();}
                    }
                    borrowDto.setPayPalPaymentId(response.result().id());
                    borrowService.saveBorrowDto(borrowDto);
                    emailService.sendConformationMessageNewOrder(borrowDto);
                    basketSession.cleanBooksBasket();
                    basketSession.resetBorrowDaysEnum();

                    return "redirect:"+ successHref;
                }
            } catch (Exception e) {
                System.out.println(e);

                return "redirect:" + cancelPaypalUrl + "?borrowId="+borrowDto.getId();
            }
        } else {

            String message = "Sorry;" +
                    "You have to many order in process or order is incomplete. You can not process any more. For more info concat our library;" +
                    "/checkout/;" +
                    "Go to basket";
            model.addAllAttributes(createMessageMap(message));

        }
        return "message";
    }


    @GetMapping("/cancel_sure")
    public String cancelBorrowChoice(Model model,@RequestParam(value = "borrowId",required = false) String borrowId) {

        String message = ";" +
                "Are you sure that you want cancel your order ? ;" +
                "/cart;" +
                "No, Link me back to cart;"+
                "/cancel?borrowId="+ borrowId +";"+
                "Yes, Cancel my borrow;"
                ;

        model.addAllAttributes(createMessageMap2Links(message));

        model.addAttribute("basketSession",basketSession);
        return "message";
    }

    @GetMapping("/cancel")
    public String cancelBorrow(Model model,@RequestParam(value = "borrowId",required = false) String borrowId,@RequestParam(value = "token", required = false) String token) {

        Optional<Borrow> orderById = borrowService.findBorrowById(borrowId);

        orderById.ifPresent(borrow -> {
            if (borrow.getBorrowStatusEnum() == BorrowStatusEnum.NEW) {
                String message = "Thank you;" +
                        "We've canceled your borrow ;" +
                        "/cart;" +
                        "No, Link me back to cart;";
                model.addAllAttributes(createMessageMap(message));
                borrowService.changeBorrowStatus(borrowId,BorrowStatusEnum.CANCEL);
            } else if (borrow.getBorrowStatusEnum() == BorrowStatusEnum.PAID) {
                PaymentExecution paymentExecution = new PaymentExecution();
                paymentExecution.setPayerId("ss");

                String message = "Thank you;" +
                        "We've canceled your borrow. Your payment will be send back to your account ;" +
                        "/cart;" +
                        "No, Link me back to cart;";
                model.addAllAttributes(createMessageMap(message));


            }
            model.addAttribute("basketSession", basketSession);

        });

        return "message";
    }

    @GetMapping(successPaypalUrl)
    public String paymentSecces(Model model,@RequestParam(value = "borrowDays", required = false) String borrowDays,
                                @RequestParam(value = "bookId", required = false) String removeFromBaske,@RequestParam("token") String paymentId, @RequestParam("PayerID") String payerId) {

        try {
            System.out.println("token " + paymentId + "user " + payerId);
            Payment payment = paypalService.executePayment(paymentId, payerId);

            Optional<Borrow> optionalBorrow = borrowRepository.findBorrowsByPayPalPaymentId(payment.getId());

            if(payment.getState().equals("approved") && optionalBorrow.isPresent()){
                Borrow borrow = optionalBorrow.get();
                borrow.setBorrowStatusEnum(BorrowStatusEnum.PAID);
                borrowRepository.save(borrow);
                String message = "Thank you;" +
                        "We've received your payment. We will send your borrow as soon as it's possible;" +
                        "/cart;" +
                        "Link me back to cart;";
                model.addAllAttributes(createMessageMap(message));
            }
            else {
                paymentProblemMessage(model);
            }

        } catch (PayPalRESTException e) {
            paymentProblemMessage(model);
        }

        return "message";

    }

    private void paymentProblemMessage(Model model) {
        String message = "Sorry" +
                "We have problem with our payment. Contact with our team. ;" +
                "/cart;" +
                "Link me back to cart;";
        model.addAllAttributes(createMessageMap(message));
    }

}
