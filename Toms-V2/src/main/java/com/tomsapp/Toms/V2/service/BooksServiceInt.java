package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.entity.Books;
import com.tomsapp.Toms.V2.entity.Students;

import java.util.List;

public interface BooksServiceInt {

    public List<Books> getBooks();
    public void saveBooks(Books books);

    void deleeteBookById(int bookId);

    Books getbooById(int bookId);

    List<Books> searchByTitleorAutorOrIbns(String searchField);

  List<Books> getAvaibleBooks();
}
