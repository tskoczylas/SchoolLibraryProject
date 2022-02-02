package com.tomsapp.Toms.V2.session;

import com.tomsapp.Toms.V2.entity.Books;
import com.tomsapp.Toms.V2.enums.BorrowPeriodEnum;
import com.tomsapp.Toms.V2.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BasketSessionTest {
    private BooksService booksService;
    private BasketSession basketSession;


    @BeforeEach
    void createMocks(){
        booksService = mock(BooksService.class);

        basketSession =
                new BasketSession(booksService);
    }


    @Test
    void initiateShouldCreateBooksListAndInitiateBorrowDayEnumMIN() {
        //given
        //when
        basketSession.initiate();
        //then
        assertEquals(basketSession.borrowPeriodEnum, BorrowPeriodEnum.MIN);
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
        assertEquals(basketSession.borrowPeriodEnum, BorrowPeriodEnum.MEDIUM);
        assertEquals(basketSession.borrowPeriodEnum.getTotalCost(),
                basketSession.selectBooks.size()*basketSession.borrowPeriodEnum.getCostPerDays()*basketSession.borrowPeriodEnum.getDays());

    }
    @Test
    void changeDayAndCalculateBorrowCostShouldNotExecuteWhenPapalEnumNameNotProvide() {
        //given
        basketSession.selectBooks = new ArrayList<>();
        basketSession.selectBooks.add(new Books());
        basketSession.selectBooks.add(new Books());
        basketSession.borrowPeriodEnum = BorrowPeriodEnum.MIN;

        String enumString="sample";
        //when
        basketSession.changeDayAndCalculateBorrowCost(enumString);
        //then
        assertEquals(basketSession.borrowPeriodEnum, BorrowPeriodEnum.MIN);
        assertEquals(basketSession.borrowPeriodEnum.getTotalCost(),6,6);

    }


    @Test
    void addBookToBorrowListShouldAddBookAndDecreaseAvailWhenProvideBookIdExistQuantityIsAbove0AndMaxBooksInBasketIsLowerThatSumOfAddedBooks() {
        //given
        String bookId="2";
        basketSession.maxBasketSize =6;
        Books books = new Books();
        books.setAvailableQuantity(1);
        basketSession.selectBooks = new ArrayList<>();


        //when
        when(booksService.getBookByIdString(bookId)).thenReturn(Optional.of(books));
        basketSession.addBookToBasket(bookId);
        basketSession.addBookToBasket(bookId);
        basketSession.addBookToBasket(bookId);

        //then
        assertThat(basketSession.getSelectBooks(),hasSize(3));
        verify(booksService,times(3)).decreaseAvailableBookQuantity(books);
    }

    @Test
    void addBookToBorrowListShouldNotAddWhenProvideBookIdNotExist() {
        //given
        basketSession.maxBasketSize =6;
        String bookId="2";
        Books books = new Books();
        basketSession.selectBooks = new ArrayList<>();
        //when
        when(booksService.getBookByIdString(bookId)).thenReturn(Optional.of(books));
        basketSession.addBookToBasket("3");
        basketSession.addBookToBasket("3");
        basketSession.addBookToBasket("3");

        //then
        assertThat(basketSession.getSelectBooks(),hasSize(0));
    }

    @Test
    void addBookToBorrowListShouldNotAddWhenAvailableQuantityIs0OrBelow() {
        //given
        basketSession.maxBasketSize =6;
        String bookId="2";
        Books books = new Books();
        books.setAvailableQuantity(0);
        basketSession.selectBooks = new ArrayList<>();
        //when
        when(booksService.getBookByIdString(bookId)).thenReturn(Optional.of(books));
        basketSession.addBookToBasket("2");
        basketSession.addBookToBasket("2");
        basketSession.addBookToBasket("2");

        //then
        assertThat(basketSession.getSelectBooks(),hasSize(0));
    }

    @Test
    void addBookToBorrowListShouldNotAddBookAndDecreaseAvailWhenProvideBookIdExistQuantityIsAbove0AndMaxBooksInBasketIsGreaterThatSumOfAddedBooks() {
        //given
        String bookId="2";
        basketSession.maxBasketSize =2;
        Books books = new Books();
        books.setAvailableQuantity(1);
        basketSession.selectBooks = new ArrayList<>();


        //when
        when(booksService.getBookByIdString(bookId)).thenReturn(Optional.of(books));
        basketSession.addBookToBasket(bookId);
        basketSession.addBookToBasket(bookId);
        basketSession.addBookToBasket(bookId);
        basketSession.addBookToBasket(bookId);


        //then
        assertThat(basketSession.getSelectBooks(),hasSize(2));
        verify(booksService,times(2)).decreaseAvailableBookQuantity(books);
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
        when(booksService.getBookByIdString(removeCartBookId)).thenReturn(Optional.of(books));
        basketSession.removeBookFromBasket(removeCartBookId);
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
        when(booksService.getBookByIdString(removeCartBookId)).thenReturn(Optional.of(books));
        basketSession.removeBookFromBasket("3");
        //then
        assertThat(basketSession.getSelectBooks(), hasSize(2));

    }

    @Test
    void decreaseAvailableBooksOnTheEndOfTheSessionIfPresentShouldExecuteWhenSelectBooksListIsNotEmpty() {
        //given
        Books fistBook = new Books();
        Books secondBook = new Books();

        basketSession.selectBooks = new ArrayList<>();
        basketSession.selectBooks.add(fistBook);
        basketSession.selectBooks.add(secondBook);
        //when
        basketSession.decreaseAvailableBooksOnTheEndOfTheSessionIfPresent();
        ArgumentCaptor<Books> booksArgumentCaptor =ArgumentCaptor.forClass(Books.class);
        verify(booksService,times(2)).inceaseAvailableBookQuantity(booksArgumentCaptor.capture());
        //then
        assertEquals(booksArgumentCaptor.getValue(),fistBook);
        assertEquals(booksArgumentCaptor.getValue(),secondBook);

    }

    @Test
    void decreaseAvailableBooksOnTheEndOfTheSessionIfPresentShouldNotExecuteWhenSelectBooksListIsEmpty() {
        //given
        //when
        basketSession.decreaseAvailableBooksOnTheEndOfTheSessionIfPresent();
        //then
        verifyNoInteractions(booksService);


    }

    @Test
    void resetBorrowDaysEnumShouldSetBorPerEnToFirstEnumInOrder() {
        //given
        basketSession.borrowPeriodEnum= BorrowPeriodEnum.MEDIUM;

        //when
        basketSession.resetBorrowDaysEnum();

        //then
        assertEquals(basketSession.borrowPeriodEnum,BorrowPeriodEnum.MIN);
    }

    @Test
    void isBasketFullShouldReturnTrueWhenSelectBookListSizeEqualOrGatherMaxBasketSize() {
        //given
        String bookId="2";
        basketSession.maxBasketSize =2;
        Books books = new Books();
        basketSession.selectBooks = new ArrayList<>();
        //when
        basketSession.selectBooks.add(books);
        basketSession.selectBooks.add(books);
        //then
        assertTrue(basketSession.isBasketFull());
    }

    @Test
    void isBasketFullShouldReturnFalseWhenSelectBookListSizeSmallerMaxBasketSize() {
        //given
        String bookId="2";
        basketSession.maxBasketSize =2;
        Books books = new Books();
        basketSession.selectBooks = new ArrayList<>();
        //when
        basketSession.selectBooks.add(books);
        //then
        assertFalse(basketSession.isBasketFull());
    }

    @Test
    void getSelectBooksShouldReturnSelectBookAndCalculateTotalCost() {
        //given
        basketSession.selectBooks = new ArrayList<>();
        basketSession.selectBooks.add(new Books());
        basketSession.selectBooks.add(new Books());
        basketSession.borrowPeriodEnum = BorrowPeriodEnum.MIN;

        //when
        List<Books> selectBooks = basketSession.getSelectBooks();
        double totalCost = basketSession.borrowPeriodEnum.getTotalCost();
        //then

        assertNotEquals(0, totalCost, 0.0);
        assertThat(selectBooks,hasSize(2));

    }

    @Test
    void isEmptyReturnTrueWhenSelectBooksEmpty() {
        //given
        basketSession.selectBooks = new ArrayList<>();
        //when
        //then
        assertTrue(basketSession.isEmpty());

    }
    @Test
    void isEmptyReturnFlaseWhenSelectBooksNotEmpty() {
        //given
        basketSession.selectBooks = new ArrayList<>();
        basketSession.selectBooks.add(new Books());
        //when
        //then
        assertFalse(basketSession.isEmpty());
    }




}