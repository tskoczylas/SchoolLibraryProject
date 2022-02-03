package com.tomsapp.Toms.V2.schedule;

import com.tomsapp.Toms.V2.entity.Borrow;
import com.tomsapp.Toms.V2.enums.BorrowStatusEnum;
import com.tomsapp.Toms.V2.repository.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BorrowSchedule {
    @Value("${value.borrowSchedule.daleyDaysAfterShipment}")
    int daleyDaysAfterShipment;

    BorrowRepository borrowRepository;

    @Autowired
    public BorrowSchedule(BorrowRepository borrowRepository) {
        this.borrowRepository = borrowRepository;
    }


    @Scheduled(fixedDelay = 2000000)

    public void setBorrow() {
        borrowRepository.findBorrowForSetBorrowed(BorrowStatusEnum.SENT,LocalDateTime.now().minusDays(daleyDaysAfterShipment)).
                ifPresent(
                borrows -> {
                    borrows.
                            forEach(borrow -> borrow.setBorrowStatusEnum(BorrowStatusEnum.BORROWED));
                    borrows.
                            forEach(borrow -> borrow.setStartBorrowDate(LocalDateTime.now()));
                    borrows.
                            forEach(borrow ->borrow.setEndBorrowDate(LocalDateTime.now().plusDays(borrow.getDaysBorrow())) );

                    borrowRepository.saveAll(borrows);
                     });}


    @Scheduled(fixedDelay = 2000000)
    public void findOverDue() {
        borrowRepository.findBorrowForOverDue(BorrowStatusEnum.BORROWED,LocalDateTime.now()).ifPresent(
                borrows -> {
                    borrows.forEach(borrow -> borrow.setBorrowStatusEnum(BorrowStatusEnum.OVERDUE));
                    //send notice mail - soon

                    borrowRepository.saveAll(borrows);


                });

    }


   }
