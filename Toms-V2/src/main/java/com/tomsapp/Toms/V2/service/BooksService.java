package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.entity.Books;
import com.tomsapp.Toms.V2.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class BooksService implements BooksServiceInt {

    @Autowired
    BooksRepository booksRepository;


    @Override
    public List<Books> getBooks() {
        return booksRepository.findAll();
    }

    @Override
    public void saveBooks(Books books) {
        booksRepository.save(books);
    }

    @Override
    public void deleeteBookById(int bookId) {
        booksRepository.deleteById(bookId);
    }

    @Override
    public Books getbooById(int bookId) {
       if(booksRepository.findById(bookId).isPresent())
           return booksRepository.findById(bookId).get();
    else throw new NullPointerException("Nie ma takiego uztkownika");

    }
    @Override
    public List<Books> getAvaibleBooks() {
    return     booksRepository.
                findAll().
                stream().
                filter(s -> s.getTotalNumber() > 0).
                collect(Collectors.toList());
    }


    @Override
    public List<Books> searchByTitleorAutorOrIbns(String searchField) {
        return booksRepository.findByAuthorContains(searchField);
    }
}
