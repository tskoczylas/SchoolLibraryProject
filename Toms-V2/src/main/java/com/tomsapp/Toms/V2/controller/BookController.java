package com.tomsapp.Toms.V2.controller;

import com.tomsapp.Toms.V2.enums.SelectEnum;
import com.tomsapp.Toms.V2.session.BorrowCart;
import com.tomsapp.Toms.V2.entity.Books;
import com.tomsapp.Toms.V2.entity.Student;
import com.tomsapp.Toms.V2.service.BooksServiceInt;
import com.tomsapp.Toms.V2.service.StudentService;
import com.tomsapp.Toms.V2.session.PageSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;



@Controller
@RequestMapping("/books")
public class BookController {


    BooksServiceInt booksService;
    StudentService studentService;
    BorrowCart borrowCart;
    PageSession pageSession;

    public BookController(BooksServiceInt booksService, StudentService studentService, BorrowCart borrowCart, PageSession pageSession) {
        this.booksService = booksService;
        this.studentService = studentService;
        this.borrowCart = borrowCart;
        this.pageSession = pageSession;
    }



    @GetMapping("/showbooks")
    public String showBooks(Model model,
                            @ModelAttribute("dropCartEnum") String dropCartEnum  ,
                            @RequestParam(value = "pageNumber",required = false) String currentPage,
                            @RequestParam(value = "keywords",required = false) String keyword,
                            @RequestParam(value = "maxPerPage",defaultValue = "20") String maxBooksPerPage,
                            @RequestParam(value = "testOrder",required = false) String ss)
    {

        pageSession.setCurrentPageAndKeyword(currentPage,keyword);


        if (keyword!=null) pageSession.refreshPage();

        Page<Books> orProvideList = booksService.
                findOrProvideList(Integer.parseInt(maxBooksPerPage),dropCartEnum);


        model.addAttribute("booksList",orProvideList.get().collect(Collectors.toList()));
        model.addAttribute("currentPage",Integer.parseInt(pageSession.getCurrentPage()));
        model.addAttribute("lastPage", orProvideList.getTotalPages()-1);
        model.addAttribute("cartBooks", borrowCart.getBooks());
        model.addAttribute("dropCartEnum", new ArrayList<>(Arrays.asList(SelectEnum.values())));
        model.addAttribute("currentSelectDropList",pageSession.getSelectEnum());


            return "books-media-gird-view-v1";
        }



        @GetMapping("/addToCard")
        public String addToCard(Model model,
                @RequestParam(value = "bookId",required = false) String bookId,
                                @RequestParam(value = "removeCartBookId",required = false) String removeCartBookId){
            if (bookId != null) {
                borrowCart.AddBookToBorrowList(Integer.parseInt(bookId));}

            if (removeCartBookId != null) {
                borrowCart.removeBookFromCard(removeCartBookId);}


        return "redirect:/books/showbooks";
        }




    @GetMapping("/add")
    public String addBooks(Model model) {

        Books books = new Books();
        List<Student> students = studentService.getStudents();

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
        List<Student> students = studentService.getStudents();


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
