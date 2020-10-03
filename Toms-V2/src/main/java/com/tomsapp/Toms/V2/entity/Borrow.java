package com.tomsapp.Toms.V2.entity;

import com.tomsapp.Toms.V2.enums.BorrowPeriodEnum;
import com.tomsapp.Toms.V2.enums.BorrowStatusEnum;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
public class Borrow {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;


    @CreationTimestamp
    private LocalDateTime createdDate;
    @Enumerated(value = EnumType.STRING)
    private BorrowPeriodEnum borrowPeriodEnum;
    @Enumerated(value = EnumType.STRING)
    private BorrowStatusEnum borrowStatusEnum;

    @OneToOne(cascade = CascadeType.MERGE, targetEntity = Student.class)
    private   Student student;

    @ManyToMany(cascade = CascadeType.MERGE, targetEntity = Books.class)
    private List<Books> books;

    public Borrow() {
    }


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

    public BorrowPeriodEnum getBorrowPeriodEnum() {
        return borrowPeriodEnum;
    }

    public void setBorrowPeriodEnum(BorrowPeriodEnum borrowPeriodEnum) {
        this.borrowPeriodEnum = borrowPeriodEnum;
    }

    public Student getStudent() {
        return student;
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

    public BorrowStatusEnum getBorrowStatusEnum() {
        return borrowStatusEnum;
    }



    public void setBorrowStatusEnum(BorrowStatusEnum borrowStatusEnum) {
        this.borrowStatusEnum = borrowStatusEnum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Borrow borrow = (Borrow) o;
        return id == borrow.id &&
                Objects.equals(createdDate, borrow.createdDate) &&
                borrowPeriodEnum == borrow.borrowPeriodEnum &&
                Objects.equals(student, borrow.student) &&
                Objects.equals(books, borrow.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdDate, borrowPeriodEnum, student, books);
    }

    @Override
    public String toString() {
        return "Borrow{" +
                "id=" + id +
                ", createdDate=" + createdDate +
                ", borrowPeriodEnum=" + borrowPeriodEnum +
                ", student=" + student +
                ", books=" + books +
                '}';
    }
}
