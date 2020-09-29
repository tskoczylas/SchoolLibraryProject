package com.tomsapp.Toms.V2.enums;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class BorrowDaysEnumTest {

    @Test
    void buttonChangeDaysShouldChangeBorrowDaysEnumToNextEnumInOrder() {
        //given
        //given
        BorrowDaysEnum borrowDaysEnumMIN=BorrowDaysEnum.MIN;
        BorrowDaysEnum borrowDaysEnumMAX=BorrowDaysEnum.MAX;
        //when
        //then
        assertThat(BorrowDaysEnum.MEDIUM,is(borrowDaysEnumMIN.buttonChangeDays()));
        assertThat(BorrowDaysEnum.MIN,is(borrowDaysEnumMAX.buttonChangeDays()));
    }

    @Test
    void expectDaysShouldReturnNextDayField() {
        //given
        BorrowDaysEnum borrowDaysEnumMIN=BorrowDaysEnum.MIN;
        BorrowDaysEnum borrowDaysEnumMAX=BorrowDaysEnum.MAX;
        //when
        //then
        assertThat(BorrowDaysEnum.MEDIUM.getDays(),is(borrowDaysEnumMIN.expectDays()));
        assertThat(BorrowDaysEnum.MIN.getDays(),is(borrowDaysEnumMAX.expectDays()));

    }

    @Test
    void calculateBorrowCostShouldMultiplyBooksInBasketBorrowDaysCostPerDay(){
        //given
        BorrowDaysEnum borrowDaysEnumMIN=BorrowDaysEnum.MIN;
        //when
        int totalBooksInBasket=22;
        borrowDaysEnumMIN.calculateBorrowCost(totalBooksInBasket);
        //then
        assertThat(borrowDaysEnumMIN.getTotalCost(),is(BorrowDaysEnum.MIN.getDays()*BorrowDaysEnum.MIN.getCostPerDays()*totalBooksInBasket));


    }
}