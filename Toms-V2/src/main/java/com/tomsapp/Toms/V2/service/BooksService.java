package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.entity.Books;
import com.tomsapp.Toms.V2.enums.SelectEnum;
import com.tomsapp.Toms.V2.repository.BooksRepository;
import com.tomsapp.Toms.V2.session.PageSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
public class BooksService implements BooksServiceInt {


   protected BooksRepository booksRepository;
    protected PageSession pageSession;

    public BooksService(BooksRepository booksRepository, PageSession pageSession) {
        this.booksRepository = booksRepository;
        this.pageSession = pageSession;
    }


    @Override
    public Optional<Books> getBookByIdString(String bookId) {
        if (bookId!=null&&bookId.matches("[0-9]+"))
         { return booksRepository.findById(Integer.parseInt(bookId)); }
        else return Optional.empty();

    }


    @Override
    public void saveBooksList(List<Books> booksList) {
        booksRepository.saveAll(booksList);
    }

    @Override
    public Page<Books> findAllOrFindByKeyword() {
        Pageable dividePage =
                PageRequest.of(pageSession.getCurrentPage(), pageSession.getPageSize(),pageSession.getSort());
       if(pageSession.getKeyword()==null) return booksRepository.
               findAll(dividePage);
     else return booksRepository.
               findIt(pageSession.getKeyword(),dividePage);

    }




}
