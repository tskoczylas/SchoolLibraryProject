package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.entity.Borrow;
import com.tomsapp.Toms.V2.entity.Student;
import com.tomsapp.Toms.V2.enums.BorrowStatusEnum;
import com.tomsapp.Toms.V2.repository.BorrowRepository;
import com.tomsapp.Toms.V2.session.BasketSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BorrowServiceInt implements BorrowService {
    @Value("${value.borrow.maxOrdersPerStudent}")
    protected int maxOrdersPerStudent;


   protected BasketSession basketSession;
   protected   BorrowRepository borrowRepository;
   protected   StudentService studentService;
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
                        countActiveOrders(logInStudent.getId(), BorrowStatusEnum.COMPLETE);

        return activeOrders.map(activeOrd -> Integer.parseInt(activeOrd) < maxOrdersPerStudent).orElse(true);

    }



    @Override
    public Borrow   saveBorrow(){
        Student logInStudent = studentService.findLogInStudent();

        Borrow borrow = new Borrow();

        borrow.setStudent(logInStudent);
        borrow.setBorrowPeriodEnum(basketSession.getBorrowPeriodEnum());
        borrow.setBooks(basketSession.getSelectBooks());
        borrow.setBorrowPeriodEnum(basketSession.getBorrowPeriodEnum());
        borrow.setBorrowStatusEnum(BorrowStatusEnum.NEW);


        return borrowRepository.save(borrow);
    }


}
