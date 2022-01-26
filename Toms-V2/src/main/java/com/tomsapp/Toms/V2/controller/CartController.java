package com.tomsapp.Toms.V2.controller;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.tomsapp.Toms.V2.dto.BorrowDto;
import com.tomsapp.Toms.V2.dto.StudentAddressDto;
import com.tomsapp.Toms.V2.dto.StudentAddressEditDto;
import com.tomsapp.Toms.V2.entity.Borrow;
import com.tomsapp.Toms.V2.entity.Student;
import com.tomsapp.Toms.V2.enums.BorrowStatusEnum;
import com.tomsapp.Toms.V2.mapper.BorrowMapper;
import com.tomsapp.Toms.V2.paypal.PaypalContex;
import com.tomsapp.Toms.V2.paypal.PaypalService;
import com.tomsapp.Toms.V2.repository.BorrowRepository;
import com.tomsapp.Toms.V2.service.BorrowService;
import com.tomsapp.Toms.V2.service.EmailService;
import com.tomsapp.Toms.V2.service.StudentService;
import com.tomsapp.Toms.V2.session.BasketSession;
import org.springframework.security.core.parameters.P;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Path;
import javax.validation.Valid;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.tomsapp.Toms.V2.mapper.BorrowMapper.mapToBorrowDtoFromBorrow;
import static com.tomsapp.Toms.V2.mapper.StudentAddressEditMapper.mapToStudentAddressEditDtoFromStudent;
import static com.tomsapp.Toms.V2.mapper.StudentAddressMaper.mapToStudentAddressDtoFromStudent;

import static com.tomsapp.Toms.V2.utils.Message.createMessageMap;
import static com.tomsapp.Toms.V2.utils.Message.createMessageMap2Links;

@Controller
public class CartController {
    BasketSession basketSession;
    StudentService studentService;
    BorrowService borrowService;
    EmailService emailService;
    BorrowRepository borrowRepository;

    final String cancelPaypalUrl="/cancel";
    final String successPaypalUrl="/payment_success";


    public CartController(BasketSession basketSession, StudentService studentService, BorrowService borrowService, EmailService emailService,  BorrowRepository borrowRepository) {
        this.basketSession = basketSession;
        this.studentService = studentService;
        this.borrowService = borrowService;
        this.emailService = emailService;
        this.borrowRepository = borrowRepository;
    }

    @GetMapping("/checkout")
    public String getCheckout(Model model,
                              @RequestParam(value = "borrowDays", required = false) String borrowDays,
                              @RequestParam(value = "bookId", required = false) String removeFromBasket) {

        basketSession.removeBookFromBasket(removeFromBasket);
        basketSession.changeDayAndCalculateBorrowCost(borrowDays);

        model.addAttribute("logInStudent", mapToStudentAddressDtoFromStudent(studentService.findLogInStudent()));
        model.addAttribute("basketSession", basketSession);

        return "checkout";
    }

    @GetMapping("/edit_data")
    public String editAddress(Model model) {
        model.addAttribute("studentAddressDto", mapToStudentAddressEditDtoFromStudent(studentService.findLogInStudent()));
        return "edit_address";
    }

    @PostMapping("/edit_data")
    public String editAddress(@Valid @ModelAttribute("studentAddressDto") StudentAddressEditDto studentAddressEditDto, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("studentAddressDto", studentAddressEditDto);
            return "edit_address";
        } else {
            studentService.editStudentAndAddress(studentAddressEditDto);
            return "redirect:/checkout";
        }
    }
/*
    @GetMapping("/process")

    public String processOrder(Model model, HttpServletRequest request) {
        if (basketSession.isEmpty()) {
            //tile //text  //link //link text
            String message = "Sorry;" +
                    "You can not process with empty basket;" +
                    "/checkout;" +
                    "Go back to basket";
            model.addAllAttributes(createMessageMap(message));

        } else if (borrowService.logInStudentHasRightToOrder()) {


            try {
                Borrow  borrow = borrowService.createBorrow();
                Borrow savedBorrow = borrowRepository.save(borrow);

                BorrowDto borrowDto = mapToBorrowDtoFromBorrow(savedBorrow);

                Payment payment = createPayment(borrowDto,successPaypalUrl,cancelPaypalUrl).create(createAPIContext);


                String redirectUrlFinal=null;
                if(payment!=null && borrowDto.isComplete()){
                    for (Links link : payment.getLinks()) {
                        if(link.getRel().equals("approval_url")){

                            redirectUrlFinal=link.getHref();}

                    }


                    savedBorrow.setPayPalPaymentId(payment.getId());
                     borrowRepository.save(savedBorrow);
                    emailService.sendConformationMessageNewOrder(borrowDto);
                    basketSession.cleanBooksBasket();
                    basketSession.resetBorrowDaysEnum();

                    return "redirect:"+ redirectUrlFinal + "?borrowId=" ;
                }
            } catch (PayPalRESTException e) {
                return "redirect:" + cancelPaypalUrl;
            }
        } else {

            String message = "Sorry;" +
                    "You have to many order in process. You can not process any more. For more info concat our library;" +
                    "/checkout/;" +
                    "Go to basket";
            model.addAllAttributes(createMessageMap(message));

        }
        return "message";
    }



    @GetMapping("/pay")
    public String payForAccount(Model model,@RequestParam(value = "borrowId",required = false) String borrowId) {
        //future pay service
        //if(payService.hasBennPaid()==true){

        borrowService.payForBorrow(borrowId);
        String message = "Thank you!;" +
                "We've received your payment! We will send your order up to 48 h.  ;" +
                "/cart;" +
                "Go back to your cart";
        model.addAllAttributes(createMessageMap(message));

        model.addAttribute("basketSession",basketSession);
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

        Optional<Borrow> orderById = borrowService.findOrderById(borrowId);

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
                borrowService.cancelOrder(borrowId);

            }
            model.addAttribute("basketSession", basketSession);

        });

        return "message";
    }

    @GetMapping(successPaypalUrl)
    public String paymentSecces(Model model,@RequestParam(value = "borrowDays", required = false) String borrowDays,
                                @RequestParam(value = "bookId", required = false) String removeFromBaske,@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {

        try {
            Payment payment = executePayment(paymentId, payerId, createAPIContext);

            Optional<Borrow> optionalBorrow = borrowRepository.findBorrowsByPayPalPaymentId(payment.getId());

            if(payment.getState().equals("approved") && optionalBorrow.isPresent()){
                Borrow borrow = optionalBorrow.get();
                borrow.setBorrowStatusEnum(BorrowStatusEnum.PAID);
                BorrowDto borrowDto = mapToBorrowDtoFromBorrow(borrow);
               // borrowService.saveOrEditBorrowDto(borrowDto);
                borrowRepository.save(borrow);
                String message = "Thank you;" +
                        "We've received your payment. We will send your borrow as soon as it's possible;" +
                        "/cart;" +
                        "No, Link me back to cart;";
                model.addAllAttributes(createMessageMap(message));
                return "message";

            }



        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        model.addAttribute("logInStudent", studentService.findLogInStudent());

            model.addAttribute("basketSession", basketSession);

        return "checkout";
    }




*/




    }
