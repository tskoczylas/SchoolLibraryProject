package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.entity.Books;
import com.tomsapp.Toms.V2.repository.BooksRepository;
import com.tomsapp.Toms.V2.repository.TokenRepository;
import com.tomsapp.Toms.V2.session.PageSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BooksServiceTest {


    private BooksRepository booksRepository;
    private PageSession pageSession;
    private BooksService booksService;

    @BeforeEach
    void createMocks(){
        booksRepository = mock(BooksRepository.class);
        pageSession = mock(PageSession.class);
        booksService =
                new BooksService(booksRepository,pageSession);
    }

    @Test
    void getBookByIdStringShouldReturnOptionalWithBookWhenExistAndInputIsIntegerAndNotNull() {
        //given
        Optional<Books> optionalBooks = Optional.of(new Books());
        when(booksRepository.findById(2)).thenReturn(optionalBooks);
        //when
        Optional<Books> bookByIdString = booksService.getBookByIdString("2");
        //then
        assertThat(bookByIdString,is(optionalBooks));
    }

    @Test
    void getBookByIdStringShouldReturnEmptyOptionalWhenInputIsNull() {
        //given
        when(booksRepository.findById(2)).thenReturn(Optional.of(new Books()));
        //when
        Optional<Books> bookByIdString = booksService.getBookByIdString(null);
        //then
        assertThat(bookByIdString,is(Optional.empty()));
    }

    @Test
    void getBookByIdStringShouldReturnEmptyOptionalWhenInputIsNotInteger() {
        //given
        when(booksRepository.findById(2)).thenReturn(Optional.of(new Books()));
        //when
        Optional<Books> bookByIdString = booksService.getBookByIdString("notInteger");
        //then
        assertThat(bookByIdString,is(Optional.empty()));
    }


}