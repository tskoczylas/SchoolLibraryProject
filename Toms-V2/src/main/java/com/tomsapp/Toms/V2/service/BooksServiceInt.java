package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.entity.Books;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BooksServiceInt {

    public List<Books> getBooks();
    public void saveBooks(Books books);

    void deleeteBookById(int bookId);

    Books getbooById(int bookId);

    List<Books> searchByTitleorAutorOrIbns(String searchField);

  List<Books> getAvaibleBooks();

    void saveBooksList(List<Books> booksList);

    Page<Books> findOrProvideList(int size);
}
