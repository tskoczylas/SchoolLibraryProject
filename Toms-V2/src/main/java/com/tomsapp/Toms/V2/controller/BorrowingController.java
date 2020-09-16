package com.tomsapp.Toms.V2.controller;

import com.tomsapp.Toms.V2.session.BorrowCart;
import com.tomsapp.Toms.V2.entity.Borrowing;
import com.tomsapp.Toms.V2.service.BooksServiceInt;
import com.tomsapp.Toms.V2.service.BorrowingServiceInt;
import com.tomsapp.Toms.V2.service.StudentServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/borrowing")
public class BorrowingController {

    @Autowired
    BorrowingServiceInt borrowingServiceInt;

    @Autowired
    BooksServiceInt booksServiceInt;

    @Autowired
    StudentServiceInt studentServiceInt;

    @Autowired
    BorrowCart borrowCart;



    @GetMapping("/list")
    public String showList(Model model) {
        model.addAttribute("listOfboorow",borrowingServiceInt.borrowingList());
        return "showBorrowingForm";
    }



    @GetMapping("/addStudent")

    public String provideSrudentsList(Model model ) {
        model.addAttribute("list", studentServiceInt.getStudents());
        return "addBorrowFormStudent";
    }


    @GetMapping("/addBook")
    public String addBorrowBook(Model model, @RequestParam("studentId") int studentID) {

        borrowCart.AddStudentToBoorowList(studentID);

        model.addAttribute("shbooks", booksServiceInt.getAvaibleBooks());
        model.addAttribute("student", studentServiceInt.findbyId(studentID));

        return "addBorrowFormBook";

    }

    @GetMapping("/addDateForm")
    public String addDataForm(Model model, @RequestParam(value = "booksId",required = false) int bookId) {

        if(bookId!=0){
            borrowCart.AddBookToBorrowList(bookId);
        }

        model.addAttribute("borowCard",borrowCart);


        return "addBoorowFormDate";
    }

    @PostMapping("/save")

    public String saveData(@Valid @ModelAttribute("borowCard") BorrowCart borrowCart2,
                           BindingResult bindingResult ){


        if (bindingResult.hasErrors()) return "addBoorowFormDate";
    else {
        borrowCart.setBorrowDays(borrowCart2.getBorrowDays());
        borrowCart.saveToBorrowing();


        return "redirect:/borrowing/list";}


    }

    @GetMapping("/delete")
    public String delete(@RequestParam("borowId") int borowId)

    {
        Borrowing borrowing=borrowingServiceInt.getByID(borowId);
        borrowing.getBooks().setTotalNumber(borrowing.getBooks().getTotalNumber()+1);
      borrowingServiceInt.delete(borowId);



        return "redirect:/borrowing/list";

    }



}