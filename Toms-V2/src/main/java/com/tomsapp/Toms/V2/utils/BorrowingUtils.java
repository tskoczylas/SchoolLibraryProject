package com.tomsapp.Toms.V2.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;

public class BorrowingUtils {


    static public String timeBetweenStartAndEndInDays(LocalDateTime end){

        if(end==null) return null;
        long days = LocalDateTime.now().until(end, ChronoUnit.DAYS);
        if(days<=0) return LocalDateTime.now().until(end, ChronoUnit.HOURS) + " hours";
        else return LocalDateTime.now().until(end,ChronoUnit.DAYS) + " days";

    }


    }
