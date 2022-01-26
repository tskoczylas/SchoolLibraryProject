package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.dto.BorrowDto;
import com.tomsapp.Toms.V2.entity.Borrow;
import com.tomsapp.Toms.V2.entity.Student;
import com.tomsapp.Toms.V2.enums.BorrowStatusEnum;
import com.tomsapp.Toms.V2.mapper.BorrowMapper;
import com.tomsapp.Toms.V2.repository.BorrowRepository;
import com.tomsapp.Toms.V2.session.BasketSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BorrowServiceInt implements BorrowService {
    @Value("${value.borrow.maxOrdersPerStudent}")
    protected int maxOrdersPerStudent;


    protected BasketSession basketSession;
    protected BorrowRepository borrowRepository;
    protected StudentService studentService;
    protected EmailService emailService;

    public BorrowServiceInt(BasketSession basketSession, BorrowRepository borrowRepository, StudentService studentService, EmailService emailService) {
        this.basketSession = basketSession;
        this.borrowRepository = borrowRepository;
        this.studentService = studentService;
        this.emailService = emailService;


    }

    @Override
    public boolean logInStudentHasRightToOrder() {

        Student logInStudent = studentService.findLogInStudent();
        Optional<String> activeOrders =
                borrowRepository.
                        countActiveOrders(logInStudent.getId(), Arrays.asList(BorrowStatusEnum.COMPLETE,BorrowStatusEnum.CANCEL));

        return activeOrders.map(activeOrd -> Integer.parseInt(activeOrd) < maxOrdersPerStudent).orElse(true);

    }

    @Override
    public List<BorrowDto> findStudentLogInBorrowsDto() {

        return studentService.
                findLogInStudent().
                getStudentsBorrows().
                stream().
                map(BorrowMapper::mapToBorrowDtoFromBorrow).collect(Collectors.toList());
    }

    @Override
    public List<BorrowDto> findByBorrStatusListLogIn(List<BorrowStatusEnum> borrowStatusEnum) {

        return borrowRepository.findBorrowByStatusAndStudent(borrowStatusEnum,
                studentService.
                        findLogInStudent().
                        getId()).
                map(s -> s.stream().
                        map(BorrowMapper::mapToBorrowDtoFromBorrow).
                        collect(Collectors.toList())).
                orElse(Collections.emptyList());

    }




    @Override
    public Optional<BorrowDto> createBorrow() {
        BorrowDto borrow = new BorrowDto();

        Student logInStudent = studentService.findLogInStudent();
        borrow.setStudent(logInStudent);
        borrow.setOverDueFee(basketSession.getBorrowPeriodEnum().getOverDueFee());
        borrow.setPricePerItem(basketSession.getBorrowPeriodEnum().getCostPerDays());
        borrow.setDaysBorrow(basketSession.getBorrowPeriodEnum().getDays());
        borrow.setTotalCost(basketSession.getBorrowPeriodEnum().getTotalCost());
        borrow.setBooks(basketSession.getSelectBooks());
        borrow.setBorrowStatusEnum(BorrowStatusEnum.NEW);

        if(borrow.isComplete()) return Optional.of(borrow);
        else return Optional.empty();
    }

    @Override
    public BorrowDto saveBorrowDto(BorrowDto borrowDto){
        Borrow borrow = BorrowMapper.mapToBorrowFromBorrowDto(borrowDto);
        Borrow saveBorrow = borrowRepository.save(borrow);
        return BorrowMapper.mapToBorrowDtoFromBorrow(saveBorrow);
    }



    @Override
    public void changeBorrowStatus(String borrowId, BorrowStatusEnum borrowStatusEnum) {
        if (borrowId.matches("[0-9]+")) {
            Optional<Borrow> byId = borrowRepository.findById(Integer.parseInt(borrowId));
            byId.ifPresent(borrow ->
            {
                borrow.setBorrowStatusEnum(borrowStatusEnum);

                borrowRepository.save(borrow);
            });
        }
    }

    @Override
    public Optional<Borrow> findBorrowById(String borrowId) {
        if (borrowId.matches("[0-9]+")) {
            return borrowRepository.findById(Integer.parseInt(borrowId));
        } else return Optional.empty();
    }



}