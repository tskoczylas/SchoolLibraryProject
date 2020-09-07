package com.tomsapp.Toms.V2.dto;

import com.tomsapp.Toms.V2.entity.Books;
import com.tomsapp.Toms.V2.entity.Borrowing;
import com.tomsapp.Toms.V2.entity.Students;
import com.tomsapp.Toms.V2.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;



@SessionScope
@Component
public class BorrowCart {

    @Autowired
    private StudentServiceInt studentServiceInt;


    @Autowired
  private   BooksServiceInt booksServiceInt;

    @Autowired
    private BorrowingServiceInt borrowingServiceInt;

    private Students student;
    private Books books;
    @NotNull
    @Min(1)
    @Max(31)
    private int borrowDays;

    public BorrowCart() {

    }



  public void AddBookToBorrowList(int bookId) {
        this.books=  booksServiceInt.getbooById(bookId);
    }

  public void AddStudentToBoorowList(int studentId) {
        this.student=  studentServiceInt.findbyId(studentId);
    }

    public void setBorrowDays(int borrowDays) {
        this.borrowDays = borrowDays;
    }

    public int getBorrowDays() {
        return borrowDays;
    }

    public void saveToBorrowing(){

        Borrowing borrowing = new Borrowing();
        borrowing.setStudents(student);
        borrowing.setBooks(books);
        books.setTotalNumber(books.getTotalNumber() - 1);
        borrowing.setEndBorrowDate(LocalDateTime.now().plusDays(borrowDays));

        booksServiceInt.saveBooks(books);


        borrowingServiceInt.save(borrowing);

    }


    public void cleanBusket(){
        this.student=null;
        this.books=null;
        this.borrowDays=0;


    }

    public Students getStudent() {
        return student;
    }

    public Books getBooks() {
        return books;
    }

    public void setStudent(Students student) {
        this.student = student;
    }

    public void setBooks(Books books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "BorrowCart{" +
                "student=" + student +
                ", books=" + books +
                ", borrowDays=" + borrowDays +
                '}';
    }
}
