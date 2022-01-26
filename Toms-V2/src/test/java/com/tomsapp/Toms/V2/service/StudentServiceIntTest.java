package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.dto.StudentAddressDto;
import com.tomsapp.Toms.V2.dto.StudentAddressEditDto;
import com.tomsapp.Toms.V2.entity.Adress;
import com.tomsapp.Toms.V2.entity.Student;
import com.tomsapp.Toms.V2.enums.Role;
import com.tomsapp.Toms.V2.exeption.NoSuchUserExeptions;
import com.tomsapp.Toms.V2.exeption.UnfilledStudentUserExceptions;
import com.tomsapp.Toms.V2.repository.StudentsRepository;
import com.tomsapp.Toms.V2.security.StudentUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class StudentServiceIntTest {

    @Mock
    private StudentsRepository studentsRepository;
    @InjectMocks
    private StudentServiceInt studentService;

    Authentication authentication;
    Student student;

    @BeforeEach
    void prepareAuthStud(){
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

         student =new Student();
        student.setId(2);
        when(authentication.getPrincipal()
        ).thenReturn(new StudentUser(student));

        when(studentsRepository.findById(2)).thenReturn(java.util.Optional.of(student));
    }
    @Test
    void SaveStudentUserActivateAndAssignAddressAndEncodeAndAssignPassword() {
        //given
        String passwordBefore="test";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Student student = new Student();
       student.setPassword(passwordBefore);
        Adress adress = new Adress();
        //when
        studentService.saveStudentUserActivateAndAssignAddress(student,adress);
        ArgumentCaptor<Student> studentArgumentCaptor=ArgumentCaptor.forClass(Student.class);
        verify(studentsRepository).save(studentArgumentCaptor.capture());

        //then
        assertTrue(passwordEncoder.matches(passwordBefore,studentArgumentCaptor.getValue().getPassword()));

        assertThat(studentArgumentCaptor.getValue().getAdresses(),is(equalTo(adress)));
        assertThat(studentArgumentCaptor.getValue().getRole(),is(equalTo(Role.ROLE_USER)));
        assertThat(studentArgumentCaptor.getValue().getRole(),is(equalTo(Role.ROLE_USER)));
        assertThat(studentArgumentCaptor.getValue().isEnabled(),is(true));
        assertThat(studentArgumentCaptor.getValue().getAdresses().getAdressStudent(),is(student));

    }

    @Test
    void findLogStudentShouldReturnStudentWhenLogUserIsInstanceofStudentUserAndStudentIdExistInDatabase() {
        //given
        //when
        //then
        Student logInStudent = studentService.findLogInStudent();
        assertThat(logInStudent.getId(),is(2));


    }

    @Test
    void findLogStudentShouldThrowNoSuchUserExeptionsWhenLogUserIsNotInstanceofStudentUserAndStudentIdExistInDatabase() {
        //given
        //when
        student.setId(3);
        //then
        assertThrows(NoSuchUserExeptions.class,()->studentService.findLogInStudent());
    }

    @Test
    void findLogStudentShouldThrowNoSuchUserExeptionsWhenLogUserIsInstanceofStudentUserAndStudentIdNotExistInDatabase() {
        //given
        //when
        when(studentsRepository.findById(2)).thenReturn(Optional.empty());
        //then
        assertThrows(NoSuchUserExeptions.class,()->studentService.findLogInStudent());
    }

    @Test
    void editStudentAndAddressShouldEditWhenAddressProvideAndStudentLogIn() {
        //given
        StudentAddressEditDto studentAddressDto = new StudentAddressEditDto();
        studentAddressDto.setAddressId(3);
        studentAddressDto.setCountry("different");
        studentAddressDto.setFirstName("firstName");
        student.setId(2);
        student.setFirstName("none");
        student.setPassword("pass");
        //when
        studentService.editStudentAndAddress(studentAddressDto);
        ArgumentCaptor<Student> studentArgumentCaptor=ArgumentCaptor.forClass(Student.class);
        verify(studentsRepository).save(studentArgumentCaptor.capture());
        //then
        assertThat(studentArgumentCaptor.getValue().getAdresses().getId(),is(3));
        assertThat(studentArgumentCaptor.getValue().getAdresses().getCountry(),is("different"));

        assertThat(studentArgumentCaptor.getValue().getId(),is(2));
        assertThat(studentArgumentCaptor.getValue().getFirstName(),is("firstName"));
        assertThat(studentArgumentCaptor.getValue().getPassword(),is("pass"));

    }

    @Test
    void editStudentAndAddressShouldThrowExceptionsWhenAddressId0AndStudentLogInProvide() {
        //given
        StudentAddressEditDto studentAddressDto = new StudentAddressEditDto();
        studentAddressDto.setCountry("different");
        studentAddressDto.setFirstName("firstName");
        student.setId(2);
        student.setFirstName("none");
        student.setPassword("pass");
        //when
        //then
        assertThrows(IllegalArgumentException.class,()->studentService.editStudentAndAddress(studentAddressDto));
    }




}