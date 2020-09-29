package com.tomsapp.Toms.V2.controller;

import com.tomsapp.Toms.V2.enums.SelectEnum;
import com.tomsapp.Toms.V2.session.BasketSession;
import com.tomsapp.Toms.V2.entity.Books;
import com.tomsapp.Toms.V2.service.BooksServiceInt;
import com.tomsapp.Toms.V2.service.StudentServiceInt;
import com.tomsapp.Toms.V2.session.PageSession;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;



@Controller
@RequestMapping("/books")
public class BookController {


    BooksServiceInt booksService;
    StudentServiceInt studentServiceInt;
    BasketSession basketSession;
    PageSession pageSession;

    public BookController(BooksServiceInt booksService, StudentServiceInt studentServiceInt, BasketSession basketSession, PageSession pageSession) {
        this.booksService = booksService;
        this.studentServiceInt = studentServiceInt;
        this.basketSession = basketSession;
        this.pageSession = pageSession;
    }


    @GetMapping("/showbooks")
    public String showBooks(Model model,
                            @ModelAttribute("dropCartEnum") String selectEnum  ,
                            @RequestParam(value = "pageNumber",required = false) String currentPage,
                            @RequestParam(value = "keywords",required = false) String keyword,
                            @RequestParam(value = "maxPerPage",required = false) String pageSize)
    {

        pageSession.setCurrentPageKeywordAndPageSize(currentPage,keyword,pageSize);
        if (keyword!=null) pageSession.refreshPage();
        pageSession.sortBy(selectEnum);

        Page<Books> booksPage = booksService.findAllOrFindByKeyword();


        model.addAttribute("booksList",booksPage.get().collect(Collectors.toList()));
        model.addAttribute("currentPage",pageSession.getCurrentPage());
        model.addAttribute("lastPage", booksPage.getTotalPages()-1);
        model.addAttribute("cartBooks", basketSession.getSelectBooks());
        model.addAttribute("dropCartEnum", new ArrayList<>(Arrays.asList(SelectEnum.values())));
        model.addAttribute("currentSelectDropList",pageSession.getSelectEnum());


            return "book";
        }



        @GetMapping(value = {"/addToCard","//addToCard","//checkout"})
        public String addToCard(Model model,
                @RequestParam(value = "bookId",required = false) String bookId,
                                @RequestParam(value = "removeCartBookId",required = false) String removeCartBookId){

                basketSession.addBookToBorrowList(bookId);
                basketSession.removeBookFromCard(removeCartBookId);


        return "redirect:/books/showbooks";
        }


    }

