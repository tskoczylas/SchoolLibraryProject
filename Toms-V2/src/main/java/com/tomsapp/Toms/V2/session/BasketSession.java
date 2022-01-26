package com.tomsapp.Toms.V2.session;

import com.tomsapp.Toms.V2.entity.Books;
import com.tomsapp.Toms.V2.enums.BorrowPeriodEnum;
import com.tomsapp.Toms.V2.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@SessionScope
@Component
public class BasketSession {



    protected BooksService booksService;
    protected BorrowPeriodEnum borrowPeriodEnum;
    protected List<Books> selectBooks;
    @Value("${value.books.maxBasketSize}")
    protected int maxBasketSize;

    @Autowired
    public BasketSession(BooksService booksService) {
        this.booksService = booksService;
    }


    @PostConstruct
    void initiate(){
        borrowPeriodEnum = BorrowPeriodEnum.MIN;

        selectBooks =new ArrayList<>();}


   public boolean isBasketFull (){
        return maxBasketSize <= selectBooks.size(); }

    public BorrowPeriodEnum getBorrowPeriodEnum() {
        return borrowPeriodEnum;
    }

    public List<Books> getSelectBooks() {
        if(borrowPeriodEnum !=null){
        borrowPeriodEnum.calculateBorrowCost(selectBooks.size());}
        return selectBooks;
    }

    public void changeDayAndCalculateBorrowCost(String borrowDaysEnumString) {

        if(Arrays.stream(BorrowPeriodEnum.values()).anyMatch(a->a.name().equals(borrowDaysEnumString))){
        BorrowPeriodEnum borrowPeriodEnum = BorrowPeriodEnum.
                valueOf(borrowDaysEnumString).
                buttonChangeDays();
        borrowPeriodEnum.calculateBorrowCost(selectBooks.size());
        this.borrowPeriodEnum = borrowPeriodEnum;}


    }

    public void addBookToBasket(String bookId) {

        booksService.getBookByIdString(bookId).ifPresent(book ->
        {if(book.isAvailable() && !isBasketFull())
        {
            booksService.decreaseAvailableBookQuantity(book);
            this.selectBooks.add(book); }

        });
    }


    public void cleanBooksBasket(){
        this.selectBooks.clear();
    }

    public void resetBorrowDaysEnum(){
        BorrowPeriodEnum[] values = BorrowPeriodEnum.values();
        borrowPeriodEnum = values[0];
    }

    public boolean isEmpty(){
        return selectBooks.size()==0;
    }


    public void removeBookFromBasket(String removeCartBookId) {
        booksService.
                     getBookByIdString(removeCartBookId).
                        ifPresent(books1 ->
                        {
                            booksService.inceaseAvailableBookQuantity(books1);
                            this.selectBooks.remove(books1);});
    }


@PreDestroy
    protected void decreaseAvailableBooksOnTheEndOfTheSessionIfPresent(){
        if(selectBooks!=null&&!selectBooks.isEmpty()){
            selectBooks.forEach(books -> booksService.inceaseAvailableBookQuantity(books));
        }
}


}
