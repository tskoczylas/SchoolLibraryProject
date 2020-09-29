package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.dto.StudentAddressDto;
import com.tomsapp.Toms.V2.entity.Adress;
import com.tomsapp.Toms.V2.entity.Student;
import com.tomsapp.Toms.V2.enums.Role;
import com.tomsapp.Toms.V2.exeption.NoSuchUserExeptions;
import com.tomsapp.Toms.V2.repository.StudentsRepository;
import com.tomsapp.Toms.V2.security.StudentUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
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

class StudentServiceIntTest {


    private StudentsRepository studentsRepository;
    private StudentService studentService;

    @BeforeEach
    void createMocks(){
        studentsRepository = mock(StudentsRepository.class);

        studentService =new StudentServiceInt(studentsRepository);
    }

    @Test
    void testSaveStudentUserActivateAndAssignAddressAndEncodeAndAssignPassword() {
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
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        //when
        Student student =new Student();
        student.setId(2);
        when(authentication.getPrincipal()
        ).thenReturn(new StudentUser(student));

        when(studentsRepository.findById(2)).thenReturn(java.util.Optional.of(student));
        //then
        Student logInStudent = studentService.findLogInStudent();
        assertThat(logInStudent.getId(),is(2));


    }

    @Test
    void findLogStudentShouldThrowNoSuchUserExeptionsWhenLogUserIsNotInstanceofStudentUserAndStudentIdExistInDatabase() {
        //given
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        //when
        Student student =new Student();
        student.setId(2);
        when(authentication.getPrincipal()
        ).thenReturn(new Object());

        when(studentsRepository.findById(2)).thenReturn(java.util.Optional.of(student));
        //then
        assertThrows(NoSuchUserExeptions.class,()->studentService.findLogInStudent());
    }

    @Test
    void findLogStudentShouldThrowNoSuchUserExeptionsWhenLogUserIsInstanceofStudentUserAndStudentIdNotExistInDatabase() {
        //given
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        //when
        Student student =new Student();
        student.setId(2);
        when(authentication.getPrincipal()
        ).thenReturn(new StudentUser(student));

        when(studentsRepository.findById(2)).thenReturn(Optional.empty());
        //then
        assertThrows(NoSuchUserExeptions.class,()->studentService.findLogInStudent());
    }

}