package com.tomsapp.Toms.V2.entity;

import com.tomsapp.Toms.V2.enums.BorrowDaysEnum;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Borrowing {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @CreationTimestamp
    private LocalDateTime createdDate;
    @Enumerated(value = EnumType.STRING)
    private BorrowDaysEnum borrowDaysEnum;

    @OneToOne(cascade = CascadeType.MERGE, targetEntity = Student.class)
    Student student;

    @OneToOne(cascade = CascadeType.MERGE, targetEntity = Books.class)
    Books books;

    public Borrowing() {
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

    public BorrowDaysEnum getBorrowDaysEnum() {
        return borrowDaysEnum;
    }

    public void setBorrowDaysEnum(BorrowDaysEnum borrowDaysEnum) {
        this.borrowDaysEnum = borrowDaysEnum;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Borrowing borrowing = (Borrowing) o;
        return id == borrowing.id &&
                Objects.equals(createdDate, borrowing.createdDate) &&
                borrowDaysEnum == borrowing.borrowDaysEnum &&
                Objects.equals(student, borrowing.student) &&
                Objects.equals(books, borrowing.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdDate, borrowDaysEnum, student, books);
    }

    @Override
    public String toString() {
        return "Borrowing{" +
                "id=" + id +
                ", createdDate=" + createdDate +
                ", borrowDaysEnum=" + borrowDaysEnum +
                ", student=" + student +
                ", books=" + books +
                '}';
    }
}
