package com.tomsapp.Toms.V2.utils;

import com.tomsapp.Toms.V2.dto.BorrowDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.tomsapp.Toms.V2.utils.BorrowingUtils.dotTwoLineFormater;
import static com.tomsapp.Toms.V2.utils.BorrowingUtils.formatDataTime;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.junit.jupiter.api.Assertions.*;

class BorrowingUtilsTest {

    @Test
    void formatDataTimeShuldFormatLocalDataTimeInSpecificFormat() {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(2020, 5, 5, 4, 30, 3);
        //when
        String createDateFormat = formatDataTime(localDateTime);

        //then
        assertThat(createDateFormat, stringContainsInOrder("2020","05","5","4","30"));

    }


    @Test
    void dotTwoLineFormaterShuldFormatToTwoLineWithDot() {
        //given
        double sample= 2.394989489379827;
        //when
        String dotFormat = dotTwoLineFormater().format(sample);

        //then
        assertThat(dotFormat, stringContainsInOrder("2","3","9"));

    }






    }
