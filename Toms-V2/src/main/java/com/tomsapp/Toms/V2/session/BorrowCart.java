package com.tomsapp.Toms.V2.session;

import com.tomsapp.Toms.V2.entity.Books;
import com.tomsapp.Toms.V2.entity.Borrowing;
import com.tomsapp.Toms.V2.entity.Student;
import com.tomsapp.Toms.V2.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.PostConstruct;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;


@SessionScope
@Component
public class BorrowCart {

    @Autowired
    private StudentServiceInt studentServiceInt;


    @Autowired
  private   BooksServiceInt booksServiceInt;

    @Autowired
    private BorrowingServiceInt borrowingServiceInt;

    private Student student;
    private List<Books> books;
    @NotNull
    @Min(1)
    @Max(31)
    private int borrowDays;


    public BorrowCart() {

    }
@PostConstruct
void createBooksList(){
        books=new ArrayList<>();
}


  public void AddBookToBorrowList(int bookId) {
      Books tempBook = booksServiceInt.getbooById(bookId);
      this.books.add(tempBook);

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
/*
        Borrowing borrowing = new Borrowing();
        borrowing.setStudent(student);
        borrowing.setBooks(books);
        books.setTotalNumber(books.getTotalNumber() - 1);
        borrowing.setEndBorrowDate(LocalDateTime.now().plusDays(borrowDays));

        booksServiceInt.saveBooks(books);


        borrowingServiceInt.save(borrowing);
*/
    }


    public void cleanBusket(){
        this.student=null;
        this.books=null;
        this.borrowDays=0;


    }

    public Student getStudent() {
        return student;
    }

    public void removeBookFromCard(String removeCartBookId) {
        Books bookToRemoveById = booksServiceInt.
                getbooById(Integer.parseInt(removeCartBookId));
        this.books.remove(bookToRemoveById);
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Books> getBooks() {
        return books;
    }

    public void setBooks(List<Books> books) {
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
