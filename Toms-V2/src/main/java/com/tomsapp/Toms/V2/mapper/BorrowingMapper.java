package com.tomsapp.Toms.V2.mapper;

import com.tomsapp.Toms.V2.dto.BorrowingDto;
import com.tomsapp.Toms.V2.entity.Borrowing;

import java.time.temporal.ChronoUnit;

import static com.tomsapp.Toms.V2.utils.BorrowingUtils.timeBetweenStartAndEndInDays;

public class BorrowingMapper {



   public static BorrowingDto borrowingDto(Borrowing borrowing){
        BorrowingDto borrowingDto= new BorrowingDto();
        borrowingDto.setId(borrowing.getId());
        borrowingDto.setCreatedDate(borrowing.getCreatedDate());
        borrowingDto.setBooks(borrowing.getBooks());
        borrowingDto.setEndBorrowDate(borrowing.getEndBorrowDate());
       borrowingDto.setStudents(borrowing.getStudents());
       borrowingDto.setBorrowPeriod(timeBetweenStartAndEndInDays
               (borrowing.getEndBorrowDate()));

       return borrowingDto;



   }
}
