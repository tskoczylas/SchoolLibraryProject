package com.tomsapp.Toms.V2.controller;

import com.tomsapp.Toms.V2.enums.SelectEnum;
import com.tomsapp.Toms.V2.session.BasketSession;
import com.tomsapp.Toms.V2.entity.Books;
import com.tomsapp.Toms.V2.service.BooksService;
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
@RequestMapping("/book")
public class BookController {


    BooksService booksService;
    StudentServiceInt studentServiceInt;
    BasketSession basketSession;
    PageSession pageSession;

    public BookController(BooksService booksService, StudentServiceInt studentServiceInt, BasketSession basketSession, PageSession pageSession) {
        this.booksService = booksService;
        this.studentServiceInt = studentServiceInt;
        this.basketSession = basketSession;
        this.pageSession = pageSession;
    }


    @GetMapping()
    public String showBooks(Model model,
                            @ModelAttribute("selectEnum") String selectEnum  ,
                            @RequestParam(value = "currentPage",required = false) String currentPage,
                            @RequestParam(value = "keyword",required = false) String keyword,
                            @RequestParam(value = "pageSize",required = false) String pageSize)
    {

        pageSession.setCurrentPageKeywordAndPageSize(currentPage,keyword,pageSize);
        if (keyword!=null) pageSession.refreshPage();
        pageSession.sortBy(selectEnum);

        Page<Books> booksPage = booksService.findAllOrFindByKeyword();


        model.addAttribute("booksList",booksPage.get().collect(Collectors.toList()));
        model.addAttribute("pageSession",pageSession);
        model.addAttribute("lastPage", booksPage.getTotalPages()-1);
        model.addAttribute("basketSession", basketSession);
        model.addAttribute("selectEnum", new ArrayList<>(Arrays.asList(SelectEnum.values())));


            return "book";
        }



        @GetMapping(value = {"/add","//add"})
        public String addToCard(@RequestParam(value = "bookId",required = false) String bookId,
                                @RequestParam(value = "removeCartBookId",required = false) String removeCartBookId){

                basketSession.addBookToBasket(bookId);
                basketSession.removeBookFromBasket(removeCartBookId);


        return "redirect:/book";
        }


    }

