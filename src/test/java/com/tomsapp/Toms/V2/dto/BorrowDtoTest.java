package com.tomsapp.Toms.V2.dto;

import com.tomsapp.Toms.V2.entity.Adress;
import com.tomsapp.Toms.V2.entity.Books;
import com.tomsapp.Toms.V2.entity.Student;
import com.tomsapp.Toms.V2.enums.BorrowStatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.junit.jupiter.api.Assertions.*;

class BorrowDtoTest {
    BorrowDto completeBorrow;

    @BeforeEach
    void createCompleteBorrow(){
        completeBorrow = new BorrowDto();
        Student student = new Student();
        student.setAdresses(new Adress());
        completeBorrow.setStudent(student);
        completeBorrow.setId(33);
        completeBorrow.setTotalCost(22);
        completeBorrow.setDaysBorrow(30);
        completeBorrow.setPricePerItem(0.12);
        completeBorrow.setOverDueFee(2.12);
        completeBorrow.setBorrowStatusEnum(BorrowStatusEnum.NEW);
        completeBorrow.setBooks(Collections.singletonList(new Books()));

    }



        @Test
    void countOverDuesShouldCalculateOVWhenBSEIsOverdueAndEBDNotNull(){
        //given
        BorrowDto borrowDto=new BorrowDto();
        borrowDto.borrowStatusEnum=BorrowStatusEnum.OVERDUE;
        borrowDto.overDueFee=0.2;
        borrowDto.endBorrowDate=LocalDateTime.now().minusDays(694);
        //when
        double countOverDue = borrowDto.countOverDue();
        //then
        assertEquals(countOverDue,138,8);

    }

    @Test
    void countOverDuesShouldNotCalculateOVWhenBSEIsNotOverdueAndEBDNotNull(){
        //given
        BorrowDto borrowDto=new BorrowDto();
        borrowDto.borrowStatusEnum=BorrowStatusEnum.NEW;
        borrowDto.overDueFee=0.2;
        borrowDto.endBorrowDate=LocalDateTime.now().minusDays(694);
        //when
        double countOverDue = borrowDto.countOverDue();
        //then
        assertEquals(countOverDue,0);

    }
    @Test
    void countOverDuesShouldNotCalculateOVWhenBSEIsOverdueAndEBDNull(){
        //given
        BorrowDto borrowDto=new BorrowDto();
        borrowDto.borrowStatusEnum=BorrowStatusEnum.NEW;
        borrowDto.overDueFee=0.2;
        //when
        double countOverDue = borrowDto.countOverDue();
        //then
        assertEquals(countOverDue,0);

    }
    @Test
    void getTotalCostFormatShouldFormatTC(){
        //given
        BorrowDto borrowDto=new BorrowDto();
        borrowDto.totalCost=0.2283884;
        //when
        String totalCostFormat = borrowDto.getTotalCostFormat();
        //then
       assertEquals(totalCostFormat,"0.23");

    }

    @Test
    void getCountSumPricePerItemFormatShouldCountAndFormat(){
        //given
        BorrowDto borrowDto=new BorrowDto();
        borrowDto.pricePerItem=0.16;
        borrowDto.daysBorrow=22;
        //when
        String totalCostFormat = borrowDto.getCountSumPricePerItemFormat();
        //then
        assertEquals(totalCostFormat,"3.52");

    }

    @Test
    void isCompleteReturnTrueWhenAllFillUp(){
        //given
        //when
        //then
        assertTrue(completeBorrow.isComplete());

    }

    @Test
    void isCompleteReturnFalseWhenUnCompleteBS(){
        //given
        //when
        completeBorrow.setBorrowStatusEnum(null);
        assertFalse(completeBorrow.isComplete());

    }



    @Test
    void isCompleteReturnFalseWhenUnCompleteST(){
        //given
        //when
        completeBorrow.setStudent(null);
        assertFalse(completeBorrow.isComplete());

    }
    @Test
    void isCompleteReturnFalseWhenUnCompleteStAdd(){
        //given
        Student student = new Student();
        student.setAdresses(null);
        //when
        completeBorrow.setStudent(student);
        assertFalse(completeBorrow.isComplete());

    }
    @Test
    void isCompleteReturnFalseWhenUnCompleteTC(){
        //given
        //when
        completeBorrow.setTotalCost(0);
        assertFalse(completeBorrow.isComplete());

    }
    @Test
    void isCompleteReturnFalseWhenUnCompleteDB(){
        //given
        //when
        completeBorrow.setDaysBorrow(0);
        assertFalse(completeBorrow.isComplete());

    }

    @Test
    void isCompleteReturnFalseWhenUnCompletePPI(){
        //given
        //when
        completeBorrow.setPricePerItem(0);
        assertFalse(completeBorrow.isComplete());

    }
    @Test
    void isCompleteReturnFalseWhenUnCompleteODF(){
        //given
        //when
        completeBorrow.setOverDueFee(0);
        assertFalse(completeBorrow.isComplete());

    }
    @Test
    void isCompleteReturnFalseWhenUnCompleteBooks(){
        //given
        //when
        completeBorrow.setBooks(null);
        assertFalse(completeBorrow.isComplete());

    }

}