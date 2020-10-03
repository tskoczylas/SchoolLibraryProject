package com.tomsapp.Toms.V2.enums;

public enum BorrowPeriodEnum {
    MIN(30,0.10), MEDIUM(60,0.9),MAX(90,0.8);

    protected int days;
    protected double costPerDays;
    protected double totalCost;

    BorrowPeriodEnum(int days, double costPerDays) {
        this.days = days;
        this.costPerDays=costPerDays;
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

    public double getTotalCost() {
        return totalCost;
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
