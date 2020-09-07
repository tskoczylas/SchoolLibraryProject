package com.tomsapp.Toms.V2.controller;

import com.tomsapp.Toms.V2.entity.Books;
import com.tomsapp.Toms.V2.entity.Students;
import com.tomsapp.Toms.V2.service.BooksServiceInt;
import com.tomsapp.Toms.V2.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    BooksServiceInt booksService;
    @Autowired
    StudentService studentService;


    @GetMapping("/showbooks")
    public String showBooks(Model model) {
        model.addAttribute("shbooks", booksService.getBooks());


        return "showBookForm";
    }

    @GetMapping("/add")
    public String addBooks(Model model) {

        Books books = new Books();
        List<Students> students = studentService.getStudents();

        model.addAttribute("booksadd", books);

        model.addAttribute("shstudents", students);

        return "addBookForm";
    }


    @GetMapping("/deletebooks")
    public String deleteBooks(@RequestParam("booksId") int bookId) {

        booksService.deleeteBookById(bookId);

        System.out.println(bookId);


        return "redirect:/books/showbooks";
    }

    @PostMapping("/save")
    public String saveBooks(@ModelAttribute() Books books) {
        booksService.saveBooks(books);


        return "redirect:/books/showbooks";
    }

    @GetMapping("/update")
    public String updateBooks(@RequestParam("booksId") int bookId, Model model) {
        List<Students> students = studentService.getStudents();


        model.addAttribute("shstudents", students);

        Books books = booksService.getbooById(bookId);

        model.addAttribute("booksadd", books);

        return "addBookForm";
    }

    @GetMapping("/shearch")
    public String searchBooks(@RequestParam("searchField") String searchField, Model model) {


        List<Books> searchingLis = booksService.searchByTitleorAutorOrIbns(searchField);
        System.out.println(searchingLis);

        {
            model.addAttribute("shbooks", searchingLis);

            return "showBookForm";
        }


    }
}
