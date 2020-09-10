package com.tomsapp.Toms.V2.dto;

import com.tomsapp.Toms.V2.entity.Books;
import com.tomsapp.Toms.V2.entity.Student;

import java.time.LocalDateTime;

public class BorrowingDto {

    int id;

    private LocalDateTime createdDate;

    private LocalDateTime endBorrowDate;


    private String borrowPeriod;

    Student student;

    Books books;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getEndBorrowDate() {
        return endBorrowDate;
    }

    public void setEndBorrowDate(LocalDateTime endBorrowDate) {
        this.endBorrowDate = endBorrowDate;
    }

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Books getBooks() {
        return books;
    }

    public void setBooks(Books books) {
        this.books = books;
    }
}
