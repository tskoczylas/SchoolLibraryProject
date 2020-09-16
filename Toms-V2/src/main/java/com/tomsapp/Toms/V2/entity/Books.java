package com.tomsapp.Toms.V2.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Objects;

@Entity

public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;



    String title;
    String isbn;
    String pageCount;
    LocalDate publishedDate;
    String thumbnailUrl;
    @Column(length = 2000)
    String shortDescription;
    @Column(length = 5000)
    String longDescription;
    String status;
    String authors;
    String categories;
    int totalNumber;

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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Books books = (Books) o;
        return id == books.id &&
                totalNumber == books.totalNumber &&
                Objects.equals(title, books.title) &&
                Objects.equals(isbn, books.isbn) &&
                Objects.equals(pageCount, books.pageCount) &&
                Objects.equals(publishedDate, books.publishedDate) &&
                Objects.equals(thumbnailUrl, books.thumbnailUrl) &&
                Objects.equals(shortDescription, books.shortDescription) &&
                Objects.equals(longDescription, books.longDescription) &&
                Objects.equals(status, books.status) &&
                Objects.equals(authors, books.authors) &&
                Objects.equals(categories, books.categories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, isbn, pageCount, publishedDate, thumbnailUrl, shortDescription, longDescription, status, authors, categories, totalNumber);
    }

    @Override
    public String toString() {
        return "Books{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", pageCount='" + pageCount + '\'' +
                ", publishedDate=" + publishedDate +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", longDescription='" + longDescription + '\'' +
                ", status='" + status + '\'' +
                ", authors='" + authors + '\'' +
                ", categories='" + categories + '\'' +
                ", totalNumber=" + totalNumber +
                '}';
    }
}
