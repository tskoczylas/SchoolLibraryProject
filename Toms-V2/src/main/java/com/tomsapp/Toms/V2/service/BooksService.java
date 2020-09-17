package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.entity.Books;
import com.tomsapp.Toms.V2.enums.SelectEnum;
import com.tomsapp.Toms.V2.repository.BooksRepository;
import com.tomsapp.Toms.V2.session.PageSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class BooksService implements BooksServiceInt {


    BooksRepository booksRepository;
    PageSession pageSession;

    public BooksService(BooksRepository booksRepository, PageSession pageSession) {
        this.booksRepository = booksRepository;
        this.pageSession = pageSession;
    }

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
    public void saveBooksList(List<Books> booksList) {
        booksRepository.saveAll(booksList);
    }

    @Override
    public Page<Books> findOrProvideList(int size, String dropCartEnum) {

        if(Arrays.stream(SelectEnum.values()).anyMatch((t) -> t.name().equals(dropCartEnum)))
        { pageSession.sortBy(dropCartEnum); }


        Pageable dividePage =
                PageRequest.of(Integer.parseInt(pageSession.getCurrentPage()), size,pageSession.getSort());

       if(pageSession.getKeyword()==null) return booksRepository.findAll(dividePage);
     else return booksRepository.findIt(pageSession.getKeyword(),dividePage);

    }







    @Override
    public List<Books> searchByTitleorAutorOrIbns(String searchField) {
        return null;
    }
}
