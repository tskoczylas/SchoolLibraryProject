package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.entity.Books;
import com.tomsapp.Toms.V2.repository.BooksRepository;
import com.tomsapp.Toms.V2.session.PageSession;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class BooksServiceImp implements BooksService {


   protected BooksRepository booksRepository;
    protected PageSession pageSession;

    public BooksServiceImp(BooksRepository booksRepository, PageSession pageSession) {
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
    public void decreaseAvailableBookQuantity(Books books){
        booksRepository.findById(books.getId()).ifPresent(
                repoBooks -> {
                    repoBooks.setAvailableQuantity(repoBooks.getAvailableQuantity()-1);
                    booksRepository.save(repoBooks);
                }
        );
    }

    @Override
    public void inceaseAvailableBookQuantity(Books books){
        booksRepository.findById(books.getId()).ifPresent(
                repoBooks -> {
                    repoBooks.setAvailableQuantity(repoBooks.getAvailableQuantity()+1);
                    booksRepository.save(repoBooks);
                }
        );

    }


    @Override
    public void saveBooksList(
            List<Books> booksList) {

        if(!booksRepository.existsById(6)){
        booksRepository.saveAll(booksList);}
    }

    @Override
    public Page<Books> findAllOrFindByKeyword() {
        Pageable dividePage =
                PageRequest.of(pageSession.getCurrentPage(),
                        pageSession.getPageSize(),pageSession.getSort());
       if(pageSession.getKeyword()==null) return booksRepository.
               findAll(dividePage);
     else return booksRepository.
               findBookByKeywordIgnoreCase(pageSession.getKeyword(),dividePage);

    }




}
