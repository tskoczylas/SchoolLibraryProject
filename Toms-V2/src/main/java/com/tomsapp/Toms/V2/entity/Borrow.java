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

    LocalDateTime shipmentDate;
    String shipmentNumber;

    LocalDateTime shipmentReturnDate;
    String shipmentReturnNumber;

    double totalCost;
    int daysBorrow;
    double pricePerItem;
    double overDueFee;

    LocalDateTime startBorrowDate;
    LocalDateTime endBorrowDate;

    String payPalPaymentId;



    @CreationTimestamp
    private LocalDateTime createdDate;
    @Enumerated(value = EnumType.STRING)
    private BorrowStatusEnum borrowStatusEnum;

    @ManyToOne(cascade = CascadeType.DETACH, targetEntity = Student.class)
    private   Student student;

    @ManyToMany(cascade = CascadeType.DETACH, targetEntity = Books.class)
    private List<Books> books;

    public Borrow() {
    }

    public LocalDateTime getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(LocalDateTime shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public String getShipmentNumber() {
        return shipmentNumber;
    }

    public void setShipmentNumber(String shipmentNumber) {
        this.shipmentNumber = shipmentNumber;
    }

    public LocalDateTime getShipmentReturnDate() {
        return shipmentReturnDate;
    }

    public void setShipmentReturnDate(LocalDateTime shipmentReturnDate) {
        this.shipmentReturnDate = shipmentReturnDate;
    }

    public String getShipmentReturnNumber() {
        return shipmentReturnNumber;
    }

    public void setShipmentReturnNumber(String shipmentReturnNumber) {
        this.shipmentReturnNumber = shipmentReturnNumber;
    }

    public LocalDateTime getEndBorrowDate() {
        return endBorrowDate;
    }

    public void setEndBorrowDate(LocalDateTime endBorrowDate) {
        this.endBorrowDate = endBorrowDate;
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

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public int getDaysBorrow() {
        return daysBorrow;
    }

    public String getPayPalPaymentId() {
        return payPalPaymentId;
    }

    public void setPayPalPaymentId(String payPalPaymentId) {
        this.payPalPaymentId = payPalPaymentId;
    }

    public double getOverDueFee() {
        return overDueFee;
    }

    public void setOverDueFee(double overDueFee) {
        this.overDueFee = overDueFee;
    }

    public void setDaysBorrow(int daysBorrow) {
        this.daysBorrow = daysBorrow;
    }

    public double getPricePerItem() {
        return pricePerItem;
    }

    public void setPricePerItem(double pricePerItem) {
        this.pricePerItem = pricePerItem;
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
                Objects.equals(student, borrow.student) &&
                Objects.equals(books, borrow.books);
    }

    public LocalDateTime getStartBorrowDate() {
        return startBorrowDate;
    }

    public void setStartBorrowDate(LocalDateTime startBorrowDate) {
        this.startBorrowDate = startBorrowDate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdDate, student, books);
    }

    @Override
    public String toString() {
        return "Borrow{" +
                "id=" + id +
                ", shipmentDate=" + shipmentDate +
                ", shipmentNumber='" + shipmentNumber + '\'' +
                ", shipmentReturnDate=" + shipmentReturnDate +
                ", shipmentReturnNumber='" + shipmentReturnNumber + '\'' +
                ", totalCost=" + totalCost +
                ", daysBorrow=" + daysBorrow +
                ", pricePerItem=" + pricePerItem +
                ", startBorrowDate=" + startBorrowDate +
                ", createdDate=" + createdDate +
                ", borrowStatusEnum=" + borrowStatusEnum +
                ", student=" + student +
                ", books=" + books +
                '}';
    }
}
