package com.tomsapp.Toms.V2.session;

import com.tomsapp.Toms.V2.entity.Books;
import com.tomsapp.Toms.V2.enums.BorrowDaysEnum;
import com.tomsapp.Toms.V2.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BasketSessionTest {
    private BooksServiceInt booksServiceInt;
    private BasketSession basketSession;


    @BeforeEach
    void createMocks(){
        booksServiceInt = mock(BooksServiceInt.class);

        basketSession =
                new BasketSession(booksServiceInt);
    }


    @Test
    void initiateShouldCreateBooksListAndInitiateBorrowDayEnumMIN() {
        //given
        //when
        basketSession.initiate();
        //then
        assertEquals(basketSession.borrowDaysEnum,BorrowDaysEnum.MIN);
        assertThat(basketSession.selectBooks,hasSize(0));
    }



    @Test
    void changeDayAndCalculateBorrowCostShouldExecuteWhenPapalEnumNameProvide() {
        //given
       basketSession.selectBooks = new ArrayList<>();
        basketSession.selectBooks.add(new Books());
        basketSession.selectBooks.add(new Books());

        String enumString="MIN";
        //when
        basketSession.changeDayAndCalculateBorrowCost(enumString);
        //then
        assertEquals(basketSession.borrowDaysEnum,BorrowDaysEnum.MEDIUM);
        assertEquals(basketSession.borrowDaysEnum.getTotalCost(),
                basketSession.selectBooks.size()*basketSession.borrowDaysEnum.getCostPerDays()*basketSession.borrowDaysEnum.getDays());

    }
    @Test
    void changeDayAndCalculateBorrowCostShouldNotExecuteWhenPapalEnumNameNotProvide() {
        //given
        basketSession.selectBooks = new ArrayList<>();
        basketSession.selectBooks.add(new Books());
        basketSession.selectBooks.add(new Books());
        basketSession.borrowDaysEnum=BorrowDaysEnum.MIN;

        String enumString="sample";
        //when
        basketSession.changeDayAndCalculateBorrowCost(enumString);
        //then
        assertEquals(basketSession.borrowDaysEnum,BorrowDaysEnum.MIN);
        assertEquals(basketSession.borrowDaysEnum.getTotalCost(),6,6);

    }


    @Test
    void addBookToBorrowListShouldAddWhenProvideBookIdExist() {
        //given
        String bookId="2";
        Books books = new Books();
        basketSession.selectBooks = new ArrayList<>();
        //when
        when(booksServiceInt.getBookByIdString(bookId)).thenReturn(Optional.of(books));
        basketSession.addBookToBorrowList(bookId);
        basketSession.addBookToBorrowList(bookId);
        basketSession.addBookToBorrowList(bookId);

        //then
        assertThat(basketSession.getSelectBooks(),hasSize(3));
    }

    @Test
    void addBookToBorrowListShouldNotAddWhenProvideBookIdNotExist() {
        //given
        String bookId="2";
        Books books = new Books();
        basketSession.selectBooks = new ArrayList<>();
        //when
        when(booksServiceInt.getBookByIdString(bookId)).thenReturn(Optional.of(books));
        basketSession.addBookToBorrowList("3");
        basketSession.addBookToBorrowList("3");
        basketSession.addBookToBorrowList("3");

        //then
        assertThat(basketSession.getSelectBooks(),hasSize(0));
    }


    @Test
    void cleanBooksBasketShouldCleanBookList() {
        //given

        Books books = new Books();
        basketSession.selectBooks = new ArrayList<>();
        basketSession.selectBooks.add(books);
        basketSession.selectBooks.add(books);
        //when
        basketSession.cleanBooksBasket();
        //then
        assertThat(basketSession.getSelectBooks(),hasSize(0));
    }

    @Test
    void removeBookFromCardShouldRemoveBookWhenInputHasAExistBookId() {
        //given
        String removeCartBookId="2";
        Books books = new Books();
        basketSession.selectBooks = new ArrayList<>();
        basketSession.selectBooks.add(books);
        basketSession.selectBooks.add(books);
        //when
        when(booksServiceInt.getBookByIdString(removeCartBookId)).thenReturn(Optional.of(books));
        basketSession.removeBookFromCard(removeCartBookId);
        //then
        assertThat(basketSession.getSelectBooks(),hasSize(1));

    }
    @Test
    void removeBookFromCardShouldNotRemoveBookWhenInputNotExistBookId() {
        //given
        String removeCartBookId="2";
        Books books = new Books();
        basketSession.selectBooks = new ArrayList<>();
        basketSession.selectBooks.add(books);
        basketSession.selectBooks.add(books);
        //when
        when(booksServiceInt.getBookByIdString(removeCartBookId)).thenReturn(Optional.of(books));
        basketSession.removeBookFromCard("3");
        //then
        assertThat(basketSession.getSelectBooks(), hasSize(2));

    }


}