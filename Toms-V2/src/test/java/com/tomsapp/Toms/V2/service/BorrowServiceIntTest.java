package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.entity.Student;
import com.tomsapp.Toms.V2.enums.BorrowStatusEnum;
import com.tomsapp.Toms.V2.repository.BorrowRepository;
import com.tomsapp.Toms.V2.session.BasketSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BorrowServiceIntTest {

    @Mock
    BasketSession basketSession;
    @Mock
    BorrowRepository borrowRepository;
    @Mock
    StudentService studentService;
    @Mock
    EmailService emailService;

    @InjectMocks
    BorrowServiceInt borrowServiceInt;

@BeforeEach()
public void create(){
    Student student = new Student();
    student.setId(2);
    when(studentService.findLogInStudent()).thenReturn(student);

}

    @Test
    void logInStudentHasRightToOrderShouldReturnTrueWhenActiveOrdersOptEmpty() {
        //given
        Student student = new Student();
        student.setId(2);

        //when
        when(studentService.findLogInStudent()).thenReturn(student);
        when(borrowRepository.countActiveOrders(2,BorrowStatusEnum.COMPLETE)).thenReturn(Optional.empty());
        //then
        assertTrue(borrowServiceInt.logInStudentHasRightToOrder());


    }

    @Test
    void logInStudentHasRightToOrderShouldReturnTrueWhenActiveOrdersOptNotGreaterThatMaxOrderPerStudent() {
        //given
        Student student = new Student();
        student.setId(2);
        borrowServiceInt.maxOrdersPerStudent=3;

        //when
        when(studentService.findLogInStudent()).thenReturn(student);
        when(borrowRepository.countActiveOrders(2,BorrowStatusEnum.COMPLETE)).thenReturn(Optional.of("2"));
        //then
        assertTrue(borrowServiceInt.logInStudentHasRightToOrder());


    }

    @Test
    void logInStudentHasRightToOrderShouldReturnFalseWhenActiveOrdersOptEqualThatMaxOrderPerStudent() {
        //given
        Student student = new Student();
        student.setId(2);
        borrowServiceInt.maxOrdersPerStudent=3;

        //when
        when(studentService.findLogInStudent()).thenReturn(student);
        when(borrowRepository.countActiveOrders(2,BorrowStatusEnum.COMPLETE)).thenReturn(Optional.of("3"));
        //then
        assertFalse(borrowServiceInt.logInStudentHasRightToOrder());


    }


    @Test
    void logInStudentHasRightToOrderShouldReturnFalseWhenActiveOrdersOptGreaterThatMaxOrderPerStudent() {
        //given
        Student student = new Student();
        student.setId(2);
        borrowServiceInt.maxOrdersPerStudent=3;

        //when
        when(studentService.findLogInStudent()).thenReturn(student);
        when(borrowRepository.countActiveOrders(2,BorrowStatusEnum.COMPLETE)).thenReturn(Optional.of("5"));
        //then
        assertFalse(borrowServiceInt.logInStudentHasRightToOrder());


    }



    @Test
    void saveBorrow() {
    }
}