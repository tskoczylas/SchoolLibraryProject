package com.tomsapp.Toms.V2.dto;

import com.tomsapp.Toms.V2.entity.Books;
import com.tomsapp.Toms.V2.entity.Student;
import com.tomsapp.Toms.V2.enums.BorrowStatusEnum;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;

import static com.tomsapp.Toms.V2.utils.BorrowingUtils.dotTwoLineFormater;
import static com.tomsapp.Toms.V2.utils.BorrowingUtils.formatDataTime;

public class BorrowDto {


    protected int id;
    protected LocalDateTime createdDate;
    protected BorrowStatusEnum borrowStatusEnum;
    protected Student student;
    protected List<Books> books;

     LocalDateTime shipmentDate;
    String shipmentNumber;
    LocalDateTime shipmentReturnDate;
    String shipmentReturnNumber;

    LocalDateTime endBorrowDate;
    LocalDateTime startBorrowDate;

    double totalCost;
    int daysBorrow;
    double pricePerItem;
    double overDueFee;

    String payPalPaymentId;





public String getCreateDateFormat() {
        return formatDataTime(createdDate);
    }
 public String getEndBorrowFormat() {
        return formatDataTime(endBorrowDate);
    }

    public double countOverDue(){
        if(borrowStatusEnum==BorrowStatusEnum.OVERDUE
                &&endBorrowDate!=null)
        { return -LocalDateTime.now().until(endBorrowDate,ChronoUnit.DAYS) * overDueFee; }
        else return 0;
    }

  public   String getTotalCostFormat(){
    return    dotTwoLineFormater().format(totalCost); }


  public String getCountSumPricePerItemFormat(){
        return    dotTwoLineFormater().format(pricePerItem*daysBorrow); }





    public boolean isComplete(){
        return borrowStatusEnum != null
                && student != null
                &&student.getAdresses() != null
                &&totalCost!=0
                &&daysBorrow!=0
                &&pricePerItem!=0
                &&overDueFee!=0
                && books != null;
    }

    public void setPayPalPaymentId(String payPalPaymentId) {
        this.payPalPaymentId = payPalPaymentId;
    }

    public String getPayPalPaymentId() {
        return payPalPaymentId;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public LocalDateTime getStartBorrowDate() {
        return startBorrowDate;
    }

    public void setStartBorrowDate(LocalDateTime startBorrowDate) {
        this.startBorrowDate = startBorrowDate;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public int getDaysBorrow() {
        return daysBorrow;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public double getOverDueFee() {
        return overDueFee;
    }

    public void setOverDueFee(double overDueFee) {
        this.overDueFee = overDueFee;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setBooks(List<Books> books) {
        this.books = books;
    }


    public int getId() {
        return id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }


    public Student getStudent() {
        return student;
    }

    public List<Books> getBooks() {
        return books;
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

    public BorrowStatusEnum getBorrowStatusEnum() {
        return borrowStatusEnum;
    }

    public void setBorrowStatusEnum(BorrowStatusEnum borrowStatusEnum) {
        this.borrowStatusEnum = borrowStatusEnum;
    }

    public LocalDateTime getEndBorrowDate() {
        return endBorrowDate;
    }

    public void setEndBorrowDate(LocalDateTime endBorrowDate) {
        this.endBorrowDate = endBorrowDate;
    }

    @Override
    public String toString() {
        return "BorrowDto{" +
                "id=" + id +
                ", createdDate=" + createdDate +
                ", borrowStatusEnum=" + borrowStatusEnum +
                ", student=" + student +
                ", books=" + books +
                ", shipmentDate=" + shipmentDate +
                ", shipmentNumber='" + shipmentNumber + '\'' +
                ", shipmentReturnDate=" + shipmentReturnDate +
                ", shipmentReturnNumber='" + shipmentReturnNumber + '\'' +
                ", endBorrowDate=" + endBorrowDate +
                ", startBorrowDate=" + startBorrowDate +
                ", totalCost=" + totalCost +
                ", daysBorrow=" + daysBorrow +
                ", pricePerItem=" + pricePerItem +
                ", overDueFee=" + overDueFee +
                '}';
    }
}