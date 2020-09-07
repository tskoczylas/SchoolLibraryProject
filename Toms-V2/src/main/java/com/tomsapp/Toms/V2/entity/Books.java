package com.tomsapp.Toms.V2.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "books2")
public class Books {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;



    @Column(name = "title")
   private String title;
    @Column(name = "author")
   private String author;

    @Column(name = "ibns")
   private String ibns;

   @Column(name = "total_ammount")
   private int totalNumber;

   @Column(name ="create_date")
   @CreationTimestamp
   private Timestamp date;


    public Books() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIbns() {
        return ibns;
    }

    public void setIbns(String ibns) {
        this.ibns = ibns;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }


    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return  title + " " +
               author;
    }
}
