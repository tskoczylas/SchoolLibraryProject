package com.tomsapp.Toms.V2.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BorrowStatusEnumTest {

    @Test
    void canStartCountingBorrowDaysShouldReturnTrueWhenSENDorBORROWED() {
        //given
        boolean send = BorrowStatusEnum.SENT.isInBorrowPeriod();
        boolean overdue = BorrowStatusEnum.BORROWED.isInBorrowPeriod();
        //when
        //then
        assertTrue(send);
        assertTrue(overdue); }


    @Test
    void canStartCountingBorrowDaysShouldReturnFalseWhenAreNotSENDorBORROWED() {
        //given
        boolean paid = BorrowStatusEnum.PAID.isInBorrowPeriod();
        //     boolean process = BorrowStatusEnum.PROCESS.isInBorrowPeriod();
        //when
        //then
        assertFalse(paid);
        //    assertFalse(process); }
    }

}