package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.entity.Books;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface BooksService {




    Optional<Books> getBookByIdString(String bookId);

    void decreaseAvailableBookQuantity(Books books);

    void inceaseAvailableBookQuantity(Books books);

    void saveBooksList(List<Books> booksList);


    Page<Books> findAllOrFindByKeyword();
}
