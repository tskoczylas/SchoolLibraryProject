package com.tomsapp.Toms.V2.controller;

import com.tomsapp.Toms.V2.dto.StudentAddressDto;
import com.tomsapp.Toms.V2.entity.Borrow;
import com.tomsapp.Toms.V2.service.BorrowService;
import com.tomsapp.Toms.V2.service.EmailService;
import com.tomsapp.Toms.V2.service.StudentAddressService;
import com.tomsapp.Toms.V2.service.StudentService;
import com.tomsapp.Toms.V2.session.BasketSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.tomsapp.Toms.V2.mapper.StudentAddressMaper.mapToStudentAddressDtoFromStudent;

@Controller
public class CartController {
    BasketSession basketSession;
    StudentService studentService;
    StudentAddressService studentAddressService;
    BorrowService borrowService;
    EmailService emailService;

    public CartController(BasketSession basketSession, StudentService studentService, StudentAddressService studentAddressService, BorrowService borrowService, EmailService emailService) {
        this.basketSession = basketSession;
        this.studentService = studentService;
        this.studentAddressService = studentAddressService;
        this.borrowService = borrowService;
        this.emailService = emailService;
    }

    @GetMapping("/checkout")
    public String getCheckout(Model model,
                              @RequestParam(value = "borrowDays", required = false ) String borrowDays,
                              @RequestParam(value = "bookId",required = false) String removeFromBasket){

        basketSession.removeBookFromBasket(removeFromBasket);
        basketSession.changeDayAndCalculateBorrowCost(borrowDays);

        model.addAttribute("logInStudent",mapToStudentAddressDtoFromStudent(studentService.findLogInStudent()));
        model.addAttribute("basketSession",basketSession);

        return "checkout";
    }
    @GetMapping("/edit_data")
    public String editAddress(Model model){
        model.addAttribute("studentAddressDto",mapToStudentAddressDtoFromStudent(studentService.findLogInStudent()));


        return "edit_address";
    }
    @PostMapping("/edit_data")
    public String editAddress(@Valid @ModelAttribute("studentAddressDto") StudentAddressDto studentAddressDto, BindingResult bindingResult,Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("studentAddressDto",studentAddressDto);
            return "edit_address"; }
        else {
        studentAddressService.editStudentAndAddress(studentAddressDto);
        return "redirect:/checkout";}
    }
    @GetMapping("/process")
    public  String processOrder(Model model,@RequestParam(value = "value",required = false,defaultValue = "false") boolean process){

        if(borrowService.logInStudentHasRightToOrder()){
            Borrow borrow = borrowService.saveBorrow();
            emailService.sendConformationMessageNewOrder(borrow);

            basketSession.cleanBooksBasket();
            basketSession.resetBorrowDaysEnum();

            model.addAttribute("messageTitle","You will be redirect to payment site ");
            model.addAttribute("messageText","Thank you! " );

            return "message";
        }
        else


        model.addAttribute("messageText","You have to many order in process, " +
                "You can not process any more. For more info concat our library,");
        model.addAttribute("messageTitle","Sorry" );
        model.addAttribute("link","/checkout" );
        model.addAttribute("linkMes","Go to basket" );

        return "message";
    }

    @GetMapping("/abb")
    public  String processOrder(Model model){

        model.addAttribute("borrow",new Borrow());


        return "order_conformation";
    }





}
