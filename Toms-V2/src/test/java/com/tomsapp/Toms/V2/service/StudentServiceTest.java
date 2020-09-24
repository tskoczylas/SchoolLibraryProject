package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.entity.Adress;
import com.tomsapp.Toms.V2.entity.Student;
import com.tomsapp.Toms.V2.enums.Role;
import com.tomsapp.Toms.V2.repository.StudentsRepository;
import com.tomsapp.Toms.V2.repository.TokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.modelmapper.internal.bytebuddy.matcher.ElementMatchers.isEquals;

class StudentServiceTest {


    private StudentsRepository studentsRepository;
    private StudentServiceInt studentServiceInt;

    @BeforeEach
    void createMocks(){
        studentsRepository = mock(StudentsRepository.class);

        studentServiceInt=new StudentService(studentsRepository);
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
        studentServiceInt.saveStudentUserActivateAndAssignAddress(student,adress);
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
}