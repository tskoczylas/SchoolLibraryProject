package com.tomsapp.Toms.V2.controller;

import com.tomsapp.Toms.V2.dto.StudentAddressDto;
import com.tomsapp.Toms.V2.service.BorrowingService;
import com.tomsapp.Toms.V2.service.StudentAddressService;
import com.tomsapp.Toms.V2.service.StudentService;
import com.tomsapp.Toms.V2.session.BasketSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.tomsapp.Toms.V2.mapper.StudentAddressMaper.mapToStudentAddressDtoFromStudent;

@Controller
public class CartController {
    BasketSession basketSession;
    StudentService studentService;
    StudentAddressService studentAddressService;
    BorrowingService borrowingService;

    public CartController(BasketSession basketSession, StudentService studentService, StudentAddressService studentAddressService, BorrowingService borrowingService) {
        this.basketSession = basketSession;
        this.studentService = studentService;
        this.studentAddressService = studentAddressService;
        this.borrowingService = borrowingService;
    }

    @GetMapping("/checkout")
    public String getCheckout(Model model, @RequestParam(value = "borrowDays", required = false ) String borrowDays,
    @RequestParam(value = "bookId",required = false) String removeFromBasket){

        basketSession.removeBookFromCard(removeFromBasket);
        basketSession.changeDayAndCalculateBorrowCost(borrowDays);

        model.addAttribute("cartBooks", basketSession.getSelectBooks());
        model.addAttribute("logInStudent",mapToStudentAddressDtoFromStudent(studentService.findLogInStudent()));
        model.addAttribute("daysEnum",basketSession.getBorrowDaysEnum());

return "checkout";
    }
    @GetMapping("/edit_data")
    public String editAddress(Model model){
        model.addAttribute("studentAddressDto",mapToStudentAddressDtoFromStudent(studentService.findLogInStudent()));


        return "edit_address";
    }
    @PostMapping("/edit_data")
    public String editAddress(@ModelAttribute("studentAddressDto") StudentAddressDto studentAddressDto){

        studentAddressService.editStudentAndAddress(studentAddressDto);


        return "redirect:/checkout";
    }
    @GetMapping("/process")
    public  String processOrder(Model model,@RequestParam(value = "value",required = false,defaultValue = "false") boolean process){
        borrowingService.saveBorrow();
        model.addAttribute("messageTitle","Your order has been send to library");
        model.addAttribute("messageText","Thank you" );

        return "message";
    }







}
