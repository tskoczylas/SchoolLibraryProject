package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.dto.BorrowDto;
import com.tomsapp.Toms.V2.entity.Adress;
import com.tomsapp.Toms.V2.entity.Books;
import com.tomsapp.Toms.V2.entity.Borrow;
import com.tomsapp.Toms.V2.entity.Student;
import com.tomsapp.Toms.V2.enums.BorrowPeriodEnum;
import com.tomsapp.Toms.V2.enums.BorrowStatusEnum;
import com.tomsapp.Toms.V2.repository.BorrowRepository;
import com.tomsapp.Toms.V2.session.BasketSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.*;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    @Captor
    ArgumentCaptor<List<Borrow>> borrowListCaptor;

    @Captor
    ArgumentCaptor<Borrow> borrowCaptor;
    @Captor
    ArgumentCaptor<Optional<Borrow>> borrowCaptorOptional;


    @BeforeEach()
    public void create() {
        Student student = new Student();
        student.setId(2);
        when(studentService.findLogInStudent()).thenReturn(student);

    }

    @Test
    void logInStudentHasRightToOrderShouldReturnTrueWhenActiveOrdersOptEmpty() {
        //given
        Student student = new Student();
        student.setId(2);
        borrowServiceInt.maxOrdersPerStudent = 3;

        //when
        when(studentService.findLogInStudent()).thenReturn(student);
        when(borrowRepository.countActiveOrders(2, Arrays.asList(BorrowStatusEnum.COMPLETE, BorrowStatusEnum.CANCEL))).thenReturn(Optional.empty());
        //then
        assertTrue(borrowServiceInt.logInStudentHasRightToOrder());


    }

    @Test
    void logInStudentHasRightToOrderShouldReturnTrueWhenActiveOrdersOptNotGreaterThatMaxOrderPerStudent() {
        //given
        Student student = new Student();
        student.setId(2);
        borrowServiceInt.maxOrdersPerStudent = 3;

        //when
        when(studentService.findLogInStudent()).thenReturn(student);
        when(borrowRepository.countActiveOrders(2, Arrays.asList(BorrowStatusEnum.COMPLETE, BorrowStatusEnum.CANCEL))).thenReturn(Optional.of("2"));
        //then
        assertTrue(borrowServiceInt.logInStudentHasRightToOrder());


    }

    @Test
    void logInStudentHasRightToOrderShouldReturnFalseWhenActiveOrdersOptEqualThatMaxOrderPerStudent() {
        //given
        Student student = new Student();
        student.setId(2);
        borrowServiceInt.maxOrdersPerStudent = 3;

        //when
        when(studentService.findLogInStudent()).thenReturn(student);
        when(borrowRepository.countActiveOrders(2, Arrays.asList(BorrowStatusEnum.COMPLETE, BorrowStatusEnum.CANCEL))).thenReturn(Optional.of("3"));
        //then

        assertFalse(borrowServiceInt.logInStudentHasRightToOrder());


    }


    @Test
    void logInStudentHasRightToOrderShouldReturnFalseWhenActiveOrdersOptGreaterThatMaxOrderPerStudent() {
        //given
        Student student = new Student();
        student.setId(2);
        borrowServiceInt.maxOrdersPerStudent = 3;

        //when
        when(studentService.findLogInStudent()).thenReturn(student);
        when(borrowRepository.countActiveOrders(2, Arrays.asList(BorrowStatusEnum.COMPLETE, BorrowStatusEnum.CANCEL))).thenReturn(Optional.of("5"));
        //then
        assertFalse(borrowServiceInt.logInStudentHasRightToOrder());


    }


    @Test
    void findByBorrStatusListLogInShouldReturnEmptyListWhenNullOrListOfBorrowsWhenNotNull() {

        //given
        when(borrowRepository.findBorrowByStatusAndStudent(BorrowStatusEnum.activeList(), 2)).thenReturn(Optional.empty()).
                thenReturn(Optional.of(Collections.singletonList(new Borrow())));
        //when
        List<BorrowDto> empty = borrowServiceInt.findByBorrStatusListLogIn(BorrowStatusEnum.activeList());
        List<BorrowDto> notEmpty = borrowServiceInt.findByBorrStatusListLogIn(BorrowStatusEnum.activeList());

        //then
        assertThat(empty, hasSize(0));
        assertThat(notEmpty, hasSize(1));
    }

    @Test
    void findStudentLogInBorrowsDtoShouldReturnListStudentBorrows() {
        //given
        List<Borrow> borrowList = Collections.singletonList(new Borrow());
        Student student = new Student();
        student.setStudentsBorrows(borrowList);
        //when
        when(studentService.findLogInStudent()).thenReturn(student);

        //then
        assertThat(borrowServiceInt.findStudentLogInBorrowsDto(), hasSize(1));
    }

    @Test
    void createBorrowShouldCreateBorrowWhenBorrowIsComplete() {
        //given
        BorrowPeriodEnum borrowPeriodEnum = BorrowPeriodEnum.MIN;
        borrowPeriodEnum.calculateBorrowCost(2);
        Student student = new Student();
        student.setFirstName("Am");
        student.setAdresses(new Adress());
        List<Books> booksList = Arrays.asList(new Books(), new Books());

        //when
        when(basketSession.getBorrowPeriodEnum()).thenReturn(borrowPeriodEnum);
        when(basketSession.getSelectBooks()).thenReturn(booksList);
        when(studentService.findLogInStudent()).thenReturn(student);
        //then
        BorrowDto borrowOptional = borrowServiceInt.createBorrow().get();

        assertEquals(borrowOptional.getStudent(),student);
        assertEquals(borrowOptional.getOverDueFee(),borrowPeriodEnum.getOverDueFee());
        assertEquals(borrowOptional.getPricePerItem(),borrowPeriodEnum.getCostPerDays());
        assertEquals(borrowOptional.getDaysBorrow(),borrowPeriodEnum.getDays());
        assertEquals(borrowOptional.getTotalCost(),borrowPeriodEnum.getTotalCost());
        assertEquals(borrowOptional.getBooks(),booksList);
        assertEquals(borrowOptional.getBorrowStatusEnum(),BorrowStatusEnum.NEW);
    }

    @Test
    void createBorrowShouldReturnEmptyOptionalWhenBorrowIsNotComplete() {
        //given
        BorrowPeriodEnum borrowPeriodEnum = BorrowPeriodEnum.MIN;
        borrowPeriodEnum.calculateBorrowCost(2);
        Student student = new Student();
        student.setFirstName("Am");
        List<Books> booksList = Arrays.asList(new Books(), new Books());

        //when
        when(basketSession.getBorrowPeriodEnum()).thenReturn(borrowPeriodEnum);
        when(basketSession.getSelectBooks()).thenReturn(booksList);
        when(studentService.findLogInStudent()).thenReturn(student);
        //then
        assertEquals(borrowServiceInt.createBorrow(),Optional.empty());

    }

    @Test
    void saveBorrowDtoShouldMapToDtoSaveAndReturnDto() {
        //given
        BorrowDto borrowDto = new BorrowDto();
        borrowDto.setId(4);
        Borrow borrow = new Borrow();
        borrow.setId(2);
        //when
        when(borrowRepository.save(any(Borrow.class))).thenReturn(borrow);
        BorrowDto returnBorrow = borrowServiceInt.saveBorrowDto(borrowDto);
        when(borrowRepository.save(any(Borrow.class))).thenReturn(borrow);
        verify(borrowRepository).save(borrowCaptor.capture());
        //given
        assertEquals(borrowCaptor.getValue().getId(), 4);
        assertEquals(returnBorrow.getId(), 2);
    }

    @Test
    void changeBorrowStatusShouldChangeStatusWhenIdIsIntegerAndExist() {
        //given
        Borrow borrow = new Borrow();
        borrow.setId(2);
        borrow.setBorrowStatusEnum(BorrowStatusEnum.NEW);
        //when
        when(borrowRepository.findById(2)).thenReturn(Optional.of(borrow));
        borrowServiceInt.changeBorrowStatus("2", BorrowStatusEnum.SENT);

        verify(borrowRepository).save(borrowCaptor.capture());
        //given
        assertEquals(borrowCaptor.getValue().getBorrowStatusEnum(), BorrowStatusEnum.SENT);
    }

    @Test
    void changeBorrowStatusShouldNotChangeStatusWhenIdIsNotInteger() {
        //given
        Borrow borrow = new Borrow();
        borrow.setId(2);
        borrow.setBorrowStatusEnum(BorrowStatusEnum.NEW);
        //when
        when(borrowRepository.findById(2)).thenReturn(Optional.of(borrow));
        borrowServiceInt.changeBorrowStatus("abc", BorrowStatusEnum.SENT);
        //then
        verifyNoInteractions(borrowRepository);
    }

    @Test
    void findBorrowByIdShouldReturnBorrowWhenInputIsIntegerAndBorrowPresent() {
        //given
        Borrow borrow = new Borrow();
        borrow.setId(2);
        //when
        when(borrowRepository.findById(2)).thenReturn(Optional.of(borrow));
        Optional<Borrow> orderById = borrowServiceInt.findBorrowById("2");

        //then
        assertEquals(orderById.get().getId(), 2);
    }

    @Test
    void findBorrowByIdShouldReturnEmptyOptionalWhenInputIsNotIntegerAndBorrowPresent() {
        //given
        Borrow borrow = new Borrow();
        borrow.setId(2);
        //when
        when(borrowRepository.findById(2)).thenReturn(Optional.of(borrow));
        Optional<Borrow> orderById = borrowServiceInt.findBorrowById("ABC");

        //then
        assertEquals(orderById, Optional.empty());
    }
}