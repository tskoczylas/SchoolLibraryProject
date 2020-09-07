package com.tomsapp.Toms.V2.dto;

import com.tomsapp.Toms.V2.entity.Books;
import com.tomsapp.Toms.V2.entity.Students;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;

public class BorrowingDto {

    int id;

    private LocalDateTime createdDate;

    private LocalDateTime endBorrowDate;


    private String borrowPeriod;

    Students students;

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

    public Students getStudents() {
        return students;
    }

    public void setStudents(Students students) {
        this.students = students;
    }

    public Books getBooks() {
        return books;
    }

    public void setBooks(Books books) {
        this.books = books;
    }
}
