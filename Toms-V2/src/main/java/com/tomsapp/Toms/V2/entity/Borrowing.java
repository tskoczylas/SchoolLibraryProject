package com.tomsapp.Toms.V2.entity;

import com.sun.javafx.beans.IDProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.xml.crypto.Data;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "borrowing")
public class Borrowing {


    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "create_date")
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name = "end_date")
    private LocalDateTime endBorrowDate;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "student_id")
     Students students;



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
                ", students=" + students +
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Borrowing borrowing = (Borrowing) o;
        return id == borrowing.id &&
                Objects.equals(createdDate, borrowing.createdDate) &&
                Objects.equals(endBorrowDate, borrowing.endBorrowDate) &&
                Objects.equals(students, borrowing.students) &&
                Objects.equals(books, borrowing.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdDate, endBorrowDate, students, books);
    }
}
