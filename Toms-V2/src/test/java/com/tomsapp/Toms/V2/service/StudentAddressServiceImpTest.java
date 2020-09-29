package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.dto.StudentAddressDto;
import com.tomsapp.Toms.V2.entity.Adress;
import com.tomsapp.Toms.V2.entity.Student;
import com.tomsapp.Toms.V2.enums.Role;
import com.tomsapp.Toms.V2.exeption.NoSuchUserExeptions;
import com.tomsapp.Toms.V2.exeption.UnfilledStudentUserExceptions;
import com.tomsapp.Toms.V2.repository.AdressRepository;
import com.tomsapp.Toms.V2.repository.StudentsRepository;
import com.tomsapp.Toms.V2.security.StudentUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.mail.Address;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentAddressServiceImpTest {

    private StudentsRepository studentsRepository;
    private StudentAddressServiceImp studentAddressServiceImp;
    private AdressRepository adressRepository;

    @BeforeEach
    void createMocks(){
        studentsRepository = mock(StudentsRepository.class);
        adressRepository=mock(AdressRepository.class);

        studentAddressServiceImp =new StudentAddressServiceImp(adressRepository,studentsRepository);
    }


    @Test
    void editStudentAndAddressShouldEditWhenAddressProvideAndStudentHasPasswordAndEmail() {
        //given
        StudentAddressDto studentAddressDto = new StudentAddressDto();
        studentAddressDto.setStudentId(22);
        studentAddressDto.setAddressId(3);
        studentAddressDto.setEmail("email");
        studentAddressDto.setPassword("pass");
        //when
        studentAddressServiceImp.editStudentAndAddress(studentAddressDto);
        ArgumentCaptor<Student> studentArgumentCaptor=ArgumentCaptor.forClass(Student.class);
        ArgumentCaptor<Adress> addressArgumentCaptor=ArgumentCaptor.forClass(Adress.class);
        verify(studentsRepository).save(studentArgumentCaptor.capture());
       verify(adressRepository).save(addressArgumentCaptor.capture());
        //then
        assertThat(addressArgumentCaptor.getValue().getId(),is(3));
        assertThat(addressArgumentCaptor.getValue().getAdressStudent().getId(),is(22));
        assertThat(studentArgumentCaptor.getValue().getId(),is(22));
        assertThat(studentArgumentCaptor.getValue().getAdresses().getId(),is(3));
        assertThat(studentArgumentCaptor.getValue().isEnabled(),is(true));
        assertThat(studentArgumentCaptor.getValue().getRole(),is(Role.ROLE_USER));

    }

    @Test
    void editStudentAndAddressShouldThrowExeptionWhenAddressProvideAndStudentId0AndHasPasswordAndEmail() {
        //given
        StudentAddressDto studentAddressDto = new StudentAddressDto();
        studentAddressDto.setAddressId(3);
        studentAddressDto.setEmail("email");
        studentAddressDto.setPassword("pass");
        //when
        //then
        assertThrows(UnfilledStudentUserExceptions.class,()-> studentAddressServiceImp.editStudentAndAddress(studentAddressDto));

    }

    @Test
    void editStudentAndAddressShouldThrowExeptionWhenAddressProvideAndStudentDoesntHavePasswordAndHasEmail() {
        //given
        StudentAddressDto studentAddressDto = new StudentAddressDto();
        studentAddressDto.setStudentId(22);
        studentAddressDto.setAddressId(3);
        studentAddressDto.setEmail("email");
        //when
        //then
        assertThrows(UnfilledStudentUserExceptions.class,()-> studentAddressServiceImp.editStudentAndAddress(studentAddressDto));

    }
    @Test
    void editStudentAndAddressShouldThrowExeptionWhenAddressProvideAndStudentHasPasswordAndDoesntHaveEmail() {
        //given
        StudentAddressDto studentAddressDto = new StudentAddressDto();
        studentAddressDto.setStudentId(22);
        studentAddressDto.setAddressId(3);
        studentAddressDto.setPassword("pass");
        //when
        //then
        assertThrows(UnfilledStudentUserExceptions.class,()-> studentAddressServiceImp.editStudentAndAddress(studentAddressDto));

    }


}