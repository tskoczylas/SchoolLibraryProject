package com.tomsapp.Toms.V2.session;

import com.tomsapp.Toms.V2.entity.Books;
import com.tomsapp.Toms.V2.enums.BorrowDaysEnum;
import com.tomsapp.Toms.V2.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@SessionScope
@Component
public class BasketSession {



    protected   BooksServiceInt booksServiceInt;
    protected BorrowDaysEnum borrowDaysEnum;
    protected List<Books> selectBooks;


    @Autowired
    public BasketSession(BooksServiceInt booksServiceInt) {
        this.booksServiceInt = booksServiceInt;
    }


    @PostConstruct
    void initiate(){
        borrowDaysEnum=BorrowDaysEnum.MIN;
        selectBooks =new ArrayList<>();

}

    public BorrowDaysEnum getBorrowDaysEnum() {
        return borrowDaysEnum;
    }

    public List<Books> getSelectBooks() {
        return selectBooks;
    }

    public void changeDayAndCalculateBorrowCost(String borrowDaysEnumString) {

        if(Arrays.stream(BorrowDaysEnum.values()).anyMatch(a->a.name().equals(borrowDaysEnumString))){
        BorrowDaysEnum borrowDaysEnum = BorrowDaysEnum.
                valueOf(borrowDaysEnumString).
                buttonChangeDays();
        borrowDaysEnum.calculateBorrowCost(selectBooks.size());
        this.borrowDaysEnum = borrowDaysEnum;}
    }

    public void addBookToBorrowList(String bookId) {
        booksServiceInt.getBookByIdString(bookId).ifPresent(book ->
        {
            this.selectBooks.add(book); });
    }


    public void cleanBooksBasket(){
        this.selectBooks.clear();
    }

    public void resetBorrowDaysEnum(){
        BorrowDaysEnum[] values = BorrowDaysEnum.values();
        borrowDaysEnum = values[0];
    }


    public void removeBookFromCard(String removeCartBookId) {
        booksServiceInt.
                     getBookByIdString(removeCartBookId).
                        ifPresent(books1 -> this.selectBooks.remove(books1));
    }








}
