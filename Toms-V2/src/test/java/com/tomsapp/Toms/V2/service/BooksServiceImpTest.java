package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.entity.Books;
import com.tomsapp.Toms.V2.repository.BooksRepository;
import com.tomsapp.Toms.V2.session.PageSession;
import org.assertj.core.internal.bytebuddy.TypeCache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BooksServiceImpTest {

    @Mock
    private BooksRepository booksRepository;
    @Mock
    private PageSession pageSession;
    @InjectMocks
    private BooksServiceImp booksServiceImp;
    @Captor
    ArgumentCaptor<Books> bookArgumentCaptor;
    @Captor
    ArgumentCaptor<List<Books>> bookListArgumentCaptor;
    @Captor
    ArgumentCaptor<Page<Books>> pageBookArgumentCaptor;
    @Captor
    ArgumentCaptor<String> keywordArgumentCaptor;
    @Captor
    ArgumentCaptor<Pageable> pageableArgumentCaptor;


    @Test
    void getBookByIdStringShouldReturnOptionalWithBookWhenExistAndInputIsIntegerAndNotNull() {
        //given
        Optional<Books> optionalBooks = Optional.of(new Books());
        when(booksRepository.findById(2)).thenReturn(optionalBooks);
        //when
        Optional<Books> bookByIdString = booksServiceImp.getBookByIdString("2");
        //then
        assertThat(bookByIdString,is(optionalBooks));
    }

    @Test
    void getBookByIdStringShouldReturnEmptyOptionalWhenInputIsNull() {
        //given
        when(booksRepository.findById(2)).thenReturn(Optional.of(new Books()));
        //when
        Optional<Books> bookByIdString = booksServiceImp.getBookByIdString(null);
        //then
        assertThat(bookByIdString,is(Optional.empty()));
    }

    @Test
    void getBookByIdStringShouldReturnEmptyOptionalWhenInputIsNotInteger() {
        //given
        when(booksRepository.findById(2)).thenReturn(Optional.of(new Books()));
        //when
        Optional<Books> bookByIdString = booksServiceImp.getBookByIdString("notInteger");
        //then
        assertThat(bookByIdString,is(Optional.empty()));
    }

    @Test
    void decreaseAvailableBookQuantityShouldDecreaseABQAndSaveWhenBookIdIsPresent() {
        //given
        Books books=new Books();
        books.setAvailableQuantity(2);
        books.setId(2);
        when(booksRepository.findById(2)).thenReturn(Optional.of(books));
        //when
        booksServiceImp.decreaseAvailableBookQuantity(books);
        verify(booksRepository).save(bookArgumentCaptor.capture());
        //then
        assertThat(bookArgumentCaptor.getValue().getAvailableQuantity(),is(1));

    }

    @Test
    void decreaseAvailableBookQuantityShouldDoNotSaveWhenBookIdIsNotPresent() {
        //given
        Books books=new Books();
        books.setAvailableQuantity(2);
        books.setId(2);
        when(booksRepository.findById(2)).thenReturn(Optional.empty());
        //when
        booksServiceImp.decreaseAvailableBookQuantity(books);

        //then
        verify(booksRepository).findById(books.getId());
        verifyNoMoreInteractions(booksRepository);


    }
    @Test
    void increaseAvailableBookQuantityShouldincreaseABAAndSaveWhenBookIdIsPresent() {
        //given
        Books books=new Books();
        books.setAvailableQuantity(2);
        books.setId(2);
        when(booksRepository.findById(2)).thenReturn(Optional.of(books));
        //when
        booksServiceImp.inceaseAvailableBookQuantity(books);
        verify(booksRepository).save(bookArgumentCaptor.capture());
        //then
        assertThat(bookArgumentCaptor.getValue().getAvailableQuantity(),is(3));

    }

    @Test
    void increaseAvailableBookQuantityShouldDoNotSaveWhenBookIdIsNotPresent() {
        //given
        Books books=new Books();
        books.setAvailableQuantity(2);
        books.setId(2);
        when(booksRepository.findById(2)).thenReturn(Optional.empty());
        //when
        booksServiceImp.inceaseAvailableBookQuantity(books);

        //then
        verify(booksRepository).findById(books.getId());
        verifyNoMoreInteractions(booksRepository);
    }

    @Test
    void  saveBooksList() {
        //given
        List<Books> booksList = new ArrayList<>();
        booksList.add(new Books());
        booksList.add(new Books());
        //when
        booksServiceImp.saveBooksList(booksList);
        verify(booksRepository).saveAll(bookListArgumentCaptor.capture());

        //then
       assertThat(bookListArgumentCaptor.getValue(),hasSize(2));
    }

    @Test
    void  findAllOrFindByKeywordShouldExecuteFindBookByKeywordIgnoreCaseWhenKeywordPresent() {
        //given
        Sort.TypedSort<Books> person = Sort.sort(Books.class);
        when(pageSession.getCurrentPage()).thenReturn(2);
        when(pageSession.getKeyword()).thenReturn("sample");
        when(pageSession.getPageSize()).thenReturn(21);
        when(pageSession.getSort()).thenReturn(person);


        //when
        booksServiceImp.findAllOrFindByKeyword();


        verify(booksRepository).findBookByKeywordIgnoreCase(keywordArgumentCaptor.capture(),pageableArgumentCaptor.capture());

        //then

        assertThat(keywordArgumentCaptor.getValue(),is("sample"));
        assertThat(pageableArgumentCaptor.getValue().getPageNumber(),is(2));
        assertThat(pageableArgumentCaptor.getValue().getPageSize(),is(21));
        assertThat(pageableArgumentCaptor.getValue().getSort(),is(person));


    }

    @Test
    void  findAllOrFindByKeywordShouldExecuteFindAllWhenKeywordIsNotPresent() {
        //given
        Sort.TypedSort<Books> person = Sort.sort(Books.class);
        when(pageSession.getCurrentPage()).thenReturn(2);
        when(pageSession.getKeyword()).thenReturn(null);
        when(pageSession.getPageSize()).thenReturn(21);
        when(pageSession.getSort()).thenReturn(person);


        //when
        booksServiceImp.findAllOrFindByKeyword();


        verify(booksRepository).findAll(pageableArgumentCaptor.capture());

        //then

        assertThat(pageableArgumentCaptor.getValue().getPageNumber(),is(2));
        assertThat(pageableArgumentCaptor.getValue().getPageSize(),is(21));
        assertThat(pageableArgumentCaptor.getValue().getSort(),is(person));


    }





}