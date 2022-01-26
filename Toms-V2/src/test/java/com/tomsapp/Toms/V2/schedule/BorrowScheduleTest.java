package com.tomsapp.Toms.V2.schedule;

import com.tomsapp.Toms.V2.entity.Borrow;
import com.tomsapp.Toms.V2.enums.BorrowPeriodEnum;
import com.tomsapp.Toms.V2.enums.BorrowStatusEnum;
import com.tomsapp.Toms.V2.repository.BorrowRepository;
import org.assertj.core.internal.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BorrowScheduleTest {

    @Mock
    BorrowRepository borrowRepository;

    @InjectMocks
    BorrowSchedule borrowSchedule;

    @Captor
    ArgumentCaptor<List<Borrow>> borrowCaptor;



    @Test
    void setBorrowShouldChangeBSEtoBOROWEDAndSetShipmentDateAndEndBorrowDate(){
        borrowSchedule.daleyDaysAfterShipment=3;
        //given
        Borrow borrow1 = new Borrow();
        borrow1.setId(3);
        borrow1.setBorrowStatusEnum(BorrowStatusEnum.SENT);
        borrow1.setDaysBorrow(20);
        borrow1.setShipmentDate(LocalDateTime.now().minusDays(5));

        List<Borrow> borrowList= Collections.singletonList(borrow1);
        //when
        when(borrowRepository.findBorrowForSetBorrowed(ArgumentMatchers.any(BorrowStatusEnum.class), ArgumentMatchers.any(LocalDateTime.class))).thenReturn(java.util.Optional.of(borrowList));

       borrowSchedule.setBorrow();
        //then

        verify(borrowRepository).saveAll(borrowCaptor.capture());

        assertThat(borrowCaptor.getValue(),hasSize(1));
        assertEquals(borrowCaptor.getValue().get(0).getShipmentDate().getMinute(),LocalDateTime.now().getMinute());
        assertEquals(borrowCaptor.getValue().get(0).getBorrowStatusEnum(),BorrowStatusEnum.BORROWED);
        assertEquals(borrowCaptor.getValue().get(0).getEndBorrowDate().getMinute(),LocalDateTime.now().plusDays(20).getMinute());

    }
    @Test
    void findOverDueShouldChangeStatusEnumToOVERDUEWhenEndBorrowDataIsBeforeNow(){
        //given
        Borrow borrow1 = new Borrow();
        borrow1.setId(3);
        borrow1.setBorrowStatusEnum(BorrowStatusEnum.BORROWED);
        borrow1.setEndBorrowDate(LocalDateTime.now().minusDays(2));

        List<Borrow> borrowList= Collections.singletonList(borrow1);
        //when
        when(borrowRepository.findBorrowForOverDue(ArgumentMatchers.any(BorrowStatusEnum.class), ArgumentMatchers.any(LocalDateTime.class))).
                thenReturn(java.util.Optional.of(borrowList));
        borrowSchedule.findOverDue();
        //then
        verify(borrowRepository).saveAll(borrowCaptor.capture());

        assertThat(borrowCaptor.getValue(),hasSize(1));
        assertEquals(borrowCaptor.getValue().get(0).getBorrowStatusEnum(),BorrowStatusEnum.OVERDUE);

    }

}