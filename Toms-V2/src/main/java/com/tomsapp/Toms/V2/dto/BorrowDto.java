package com.tomsapp.Toms.V2.dto;

import com.tomsapp.Toms.V2.entity.Books;
import com.tomsapp.Toms.V2.entity.Student;
import com.tomsapp.Toms.V2.enums.BorrowPeriodEnum;
import com.tomsapp.Toms.V2.enums.BorrowStatusEnum;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BorrowDto {


    int id;
    private LocalDateTime createdDate;
    private BorrowPeriodEnum borrowPeriodEnum;
    private BorrowStatusEnum borrowStatusEnum;
    private Student student;
    private List<Books> books;

    private String createDateFormat;

    public void setId(int id) {
        this.id = id;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void setBorrowPeriodEnum(BorrowPeriodEnum borrowPeriodEnum) {
        this.borrowPeriodEnum = borrowPeriodEnum;
    }

    public void setBorrowStatusEnum(BorrowStatusEnum borrowStatusEnum) {
        this.borrowStatusEnum = borrowStatusEnum;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setBooks(List<Books> books) {
        this.books = books;
    }

    public void setCreateDateFormat(String createDateFormat) {
        this.createDateFormat = createDateFormat;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public BorrowPeriodEnum getBorrowPeriodEnum() {
        return borrowPeriodEnum;
    }

    public BorrowStatusEnum getBorrowStatusEnum() {
        return borrowStatusEnum;
    }

    public Student getStudent() {
        return student;
    }

    public List<Books> getBooks() {
        return books;
    }

    public String getCreateDateFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return createdDate.format(formatter);
    }

    @Override
    public String toString() {
        return "BorrowDto{" +
                "id=" + id +
                ", createdDate=" + createdDate +
                ", borrowPeriodEnum=" + borrowPeriodEnum +
                ", borrowStatusEnum=" + borrowStatusEnum +
                ", student=" + student +
                ", books=" + books +
                ", createDateFormat='" + createDateFormat + '\'' +
                '}';
    }
}