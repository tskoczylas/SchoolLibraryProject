package com.tomsapp.Toms.V2.enums;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class BorrowPeriodEnumTest {

    @Test
    void buttonChangeDaysShouldChangeBorrowDaysEnumToNextEnumInOrder() {
        //given
        //given
        BorrowPeriodEnum borrowPeriodEnumMIN = BorrowPeriodEnum.MIN;
        BorrowPeriodEnum borrowPeriodEnumMAX = BorrowPeriodEnum.MAX;
        //when
        //then
        assertThat(BorrowPeriodEnum.MEDIUM,is(borrowPeriodEnumMIN.buttonChangeDays()));
        assertThat(BorrowPeriodEnum.MIN,is(borrowPeriodEnumMAX.buttonChangeDays()));
    }

    @Test
    void expectDaysShouldReturnNextDayField() {
        //given

        BorrowPeriodEnum borrowPeriodEnumMIN = BorrowPeriodEnum.MIN;
        BorrowPeriodEnum borrowPeriodEnumMAX = BorrowPeriodEnum.MAX;
        //when
        //then
        assertThat(BorrowPeriodEnum.MEDIUM.getDays(),is(borrowPeriodEnumMIN.expectDays()));
        assertThat(BorrowPeriodEnum.MIN.getDays(),is(borrowPeriodEnumMAX.expectDays()));

    }

    @Test
    void calculateBorrowCostShouldMultiplyBooksInBasketBorrowDaysCostPerDay(){
        //given
        BorrowPeriodEnum borrowPeriodEnumMIN = BorrowPeriodEnum.MIN;
        //when
        int totalBooksInBasket=22;
        borrowPeriodEnumMIN.calculateBorrowCost(totalBooksInBasket);
        //then
        assertThat(borrowPeriodEnumMIN.getTotalCost(),is(BorrowPeriodEnum.MIN.getDays()* BorrowPeriodEnum.MIN.getCostPerDays()*totalBooksInBasket));


    }

    @Test
    void countBorrowDaysLeftFromShouldReturnDaysLeftWhenProvideDateIsAfterNow(){
        //given
        BorrowPeriodEnum borrowPeriodEnumMIN = BorrowPeriodEnum.MIN;
        //when
        LocalDateTime localDateTime = LocalDateTime.now().minusDays(20);
        int i = borrowPeriodEnumMIN.countBorrowDaysLeftFrom(localDateTime);

        //then
       assertThat(i,is(borrowPeriodEnumMIN.days-20));

    }
    @Test
    void countBorrowDaysLeftFromShouldReturnOWhenShipingDateIsAfterNow(){
        //given
        BorrowPeriodEnum borrowPeriodEnumMIN = BorrowPeriodEnum.MIN;
        //when
        LocalDateTime localDateTime = LocalDateTime.now().plusDays(20).plusMinutes(1);
        int i = borrowPeriodEnumMIN.countBorrowDaysLeftFrom(localDateTime);

        //then
        assertThat(i,is(0));

    }


}