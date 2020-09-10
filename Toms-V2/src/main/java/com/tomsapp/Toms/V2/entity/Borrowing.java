package com.tomsapp.Toms.V2.entity;

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

    private LocalDateTime endBorrowDate;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "student_id")
    Student student;



    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "book_id")
    Books books;






    public Borrowing() {
    }


    @Override
    public String toString() {
        return "Borrowing{" +
                "id=" + id +
                ", createdDate=" + createdDate +
                ", endBorrowDate='" + endBorrowDate + '\'' +
                ", student=" + student +
                ", books=" + books +
                '}';
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

    public LocalDateTime getEndBorrowDate() {
        return endBorrowDate;
    }

    public void setEndBorrowDate(LocalDateTime endBorrowDate) {
        this.endBorrowDate = endBorrowDate;
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
                Objects.equals(endBorrowDate, borrowing.endBorrowDate) &&
                Objects.equals(student, borrowing.student) &&
                Objects.equals(books, borrowing.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdDate, endBorrowDate, student, books);
    }
}
