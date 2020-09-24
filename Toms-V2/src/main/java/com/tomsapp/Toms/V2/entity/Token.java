package com.tomsapp.Toms.V2.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;

    @CreationTimestamp
    private LocalDateTime createDate;

  private   String token;

 private     boolean isActive;


    @OneToOne(targetEntity = Student.class, fetch = FetchType.EAGER)
    Student student;

    public Token() {
    }

    public Token(Student student) {
        this.student =student;
        this.token = UUID.randomUUID().toString();
        this.isActive=true;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token1 = (Token) o;
        return id == token1.id &&
                isActive == token1.isActive &&
                Objects.equals(createDate, token1.createDate) &&
                Objects.equals(token, token1.token) &&
                Objects.equals(student, token1.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createDate, token, isActive, student);
    }
}
