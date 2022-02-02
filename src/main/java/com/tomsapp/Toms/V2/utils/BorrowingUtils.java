package com.tomsapp.Toms.V2.utils;

import com.tomsapp.Toms.V2.enums.BorrowPeriodEnum;

import java.text.NumberFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.Locale;

public class BorrowingUtils {

/*
    static public String timeBetweenStartAndEndInDays(BorrowPeriodEnum borrowPeriodEnum){

        if(borrowPeriodEnum.getDays()==0) return "0";
        if(days<=0) return LocalDateTime.now().until(end, ChronoUnit.HOURS) + " hours";
        else return LocalDateTime.now().until(end,ChronoUnit.DAYS) + " days";

    }

*/



    public static String formatDataTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return localDateTime.format(formatter);
    }


    public static NumberFormat dotTwoLineFormater(){
        NumberFormat currencyFormatter=   NumberFormat.getNumberInstance(Locale.ENGLISH);
        currencyFormatter.setMaximumFractionDigits(2);
        currencyFormatter.setMinimumFractionDigits(2);
        return currencyFormatter;}


    }

