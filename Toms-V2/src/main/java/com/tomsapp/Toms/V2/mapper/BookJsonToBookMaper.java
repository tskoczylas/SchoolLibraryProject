package com.tomsapp.Toms.V2.mapper;

import com.tomsapp.Toms.V2.entity.Books;
import com.tomsapp.Toms.V2.setup.BookDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Random;

@Component
public class BookJsonToBookMaper {

    @Value("${value.books.available}")
      int availableBooks;

   private Random random;

    @PostConstruct
    @Scope(scopeName = "prototype")
    void createRandom(){
      random= new Random(30);
    }

    public  Books mapToBooks(BookDto bookDto){


        Books mapBook = new Books();
        mapBook.setTitle(bookDto.getTitle());
        if(bookDto.getAuthors().isEmpty()) mapBook.setAuthors("none");
        else mapBook.setAuthors(bookDto.getAuthors().get(0));

        if(bookDto.getCategories().isEmpty()) mapBook.setCategories("none");
        else mapBook.setCategories(bookDto.getCategories().get(0));
        mapBook.setIsbn(bookDto.getIsbn());
        mapBook.setLongDescription(bookDto.getLongDescription());
        mapBook.setPageCount(bookDto.getPageCount());
        mapBook.setShortDescription(bookDto.getShortDescription());
        mapBook.setStatus(bookDto.getStatus());
        mapBook.setThumbnailUrl(bookDto.getThumbnailUrl());
        mapBook.setAvailableQuantity(random.nextInt(20));

        return mapBook;
    }
}
