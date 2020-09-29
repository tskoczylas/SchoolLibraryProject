package com.tomsapp.Toms.V2.enums;

public enum  BorrowDaysEnum {
    MIN(30,0.010), MEDIUM(60,0.007),MAX(90,0.006);

    private int days;
    private double costPerDays;
    private double totalCost;

    BorrowDaysEnum(int days, double costPerDays) {
        this.days = days;
        this.costPerDays=costPerDays;
    }

    public int getDays() {
        return days;
    }

    public int expectDays() {
        for (int i = 0; i <BorrowDaysEnum.values().length-1 ; i++) {
            BorrowDaysEnum[] values = BorrowDaysEnum.values();
            BorrowDaysEnum firstEnum = BorrowDaysEnum.valueOf(values[i].name());
            BorrowDaysEnum nextEnum = BorrowDaysEnum.valueOf(values[i+1].name());
            if(this==firstEnum) return nextEnum.days;
        }

        return BorrowDaysEnum.MIN.days;
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

    public BorrowDaysEnum buttonChangeDays(){
        for (int i = 0; i <BorrowDaysEnum.values().length-1 ; i++) {
            BorrowDaysEnum[] values = BorrowDaysEnum.values();
            BorrowDaysEnum firstEnum = BorrowDaysEnum.valueOf(values[i].name());
            BorrowDaysEnum nextEnum = BorrowDaysEnum.valueOf(values[i+1].name());
            if(this==firstEnum) return nextEnum;
        }
        return BorrowDaysEnum.MIN;
    }
}
