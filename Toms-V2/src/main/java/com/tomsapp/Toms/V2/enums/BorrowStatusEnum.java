package com.tomsapp.Toms.V2.enums;

public enum BorrowStatusEnum {
    NEW("Order has been registered, We're processing  your payment ")
    ,PAID("Order has been paid")
    ,PROCESS("Order is processing"),
    SEND("Order has been send"),
    COMPLETE("Order is complete");


    String description;



    BorrowStatusEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
