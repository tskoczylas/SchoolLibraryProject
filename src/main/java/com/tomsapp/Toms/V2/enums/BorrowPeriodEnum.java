package com.tomsapp.Toms.V2.enums;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public enum BorrowPeriodEnum {
    MIN(30,0.15), MEDIUM(60,0.13),MAX(90,0.11);

    protected int days;
    protected double costPerDays;
    protected double totalCost;
    protected double overDueFee;

    BorrowPeriodEnum(int days, double costPerDays) {
        this.overDueFee=costPerDays*2;
        this.days = days;
        this.costPerDays=costPerDays;
    }

    public int countBorrowDaysLeftFrom(LocalDateTime shipmentDate){
        if(shipmentDate.isBefore(LocalDateTime.now())){
            long until = shipmentDate.until(LocalDateTime.now(), ChronoUnit.DAYS);
            return (int) (days-until);
        }
        else return 0;
    }

    public int isOverDueAfter(LocalDateTime localDateTime){
        if(localDateTime.isAfter(LocalDateTime.now())){
            long until = LocalDateTime.now().until(localDateTime, ChronoUnit.DAYS);
            return (int) (days-until);
        }
        else return 0;
    }

    public int getDays() {
        return days;
    }

    public int expectDays() {
        for (int i = 0; i < BorrowPeriodEnum.values().length-1 ; i++) {
            BorrowPeriodEnum[] values = BorrowPeriodEnum.values();
            BorrowPeriodEnum firstEnum = BorrowPeriodEnum.valueOf(values[i].name());
            BorrowPeriodEnum nextEnum = BorrowPeriodEnum.valueOf(values[i+1].name());
            if(this==firstEnum) return nextEnum.days;
        }

        return BorrowPeriodEnum.MIN.days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public double getOverDueFee() {
        return overDueFee;
    }

    public double getCostPerDays() {
        return costPerDays;
    }

    public void calculateBorrowCost(int totalInBasket){

        totalCost=costPerDays*totalInBasket*days;
    }

    public BorrowPeriodEnum buttonChangeDays(){
        for (int i = 0; i < BorrowPeriodEnum.values().length-1 ; i++) {
            BorrowPeriodEnum[] values = BorrowPeriodEnum.values();
            BorrowPeriodEnum firstEnum = BorrowPeriodEnum.valueOf(values[i].name());
            BorrowPeriodEnum nextEnum = BorrowPeriodEnum.valueOf(values[i+1].name());
            if(this==firstEnum) return nextEnum;
        }
        return BorrowPeriodEnum.MIN;
    }
}
