package com.tomsapp.Toms.V2.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public enum BorrowStatusEnum {
    NEW("Order has been registered, We're processing  your payment ")
    ,PAID("Order has been paid"),
    SENT("Order has been send"),
    BORROWED("Order is in held"),
    RETURN("Order has been send back"),
    COMPLETE("Borrow is finish"),
    OVERDUE("Order has been overdue"),
    CANCEL("Borrow has benn canceled");



   public boolean isInBorrowPeriod(){
        return Stream.of(this).allMatch(b->b.equals(SENT)||b.equals(BORROWED));
    }

    public boolean isBorrowBeforeSend(){
        return Stream.of(this).allMatch(b->b.equals(NEW)||b.equals(PAID));
    }

   static public List<BorrowStatusEnum> activeList(){
       return Arrays.asList(NEW,PAID,SENT,BORROWED);
    }

    String description;



    BorrowStatusEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
