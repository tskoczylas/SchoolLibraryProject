package com.tomsapp.Toms.V2.mapper;

import com.tomsapp.Toms.V2.dto.StudentAddressDto;
import com.tomsapp.Toms.V2.entity.Adress;
import com.tomsapp.Toms.V2.entity.Student;
import com.tomsapp.Toms.V2.entity.Token;
import org.junit.jupiter.api.Test;

import static com.tomsapp.Toms.V2.mapper.StudentAddressMaper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.isEquals;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class StudentAddressMaperTest {

    @Test
    void testMapToStudentFromStudentAddressDto() {
        //given
        StudentAddressDto studentAddressDto = new StudentAddressDto();
        String sampleEmail="sample@sample.pl";
        studentAddressDto.setEmail(sampleEmail);
        studentAddressDto.setConfirmEmail(sampleEmail);
        //then
        Student student = mapToStudentFromStudentAddressDto(studentAddressDto);
        //when
        assertEquals(sampleEmail,student.getEmail());


    }

    @Test
    void testMapToAddressFromStudentAddressDto() {

        //given
        StudentAddressDto studentAddressDto = new StudentAddressDto();
        String sample= "sample";
        studentAddressDto.setAddressFirstLine(sample);
        studentAddressDto.setAddressSecondLine(sample);
        studentAddressDto.setCountry(sample);
        //then
        Adress adress = mapToAddressFromStudentAddressDto(studentAddressDto);
        //when
        assertEquals(sample,adress.getAddressFirstLine());
        assertEquals(sample,adress.getAddressSecondLine());
        assertEquals(sample,adress.getCountry());

    }
    @Test
    void testMapToStudentAddressDtoFromToken(){
        //given
        String sample= "sample";
        Student student = new Student();
        student.setEmail(sample);
        student.setFirstName(sample);
        Token token = new Token(student);
        //then
        StudentAddressDto studentAddressDto = mapToStudentAddressDtoFromToken(token);
        //when
        assertEquals(studentAddressDto.getEmail(),sample);
        assertEquals(studentAddressDto.getFirstName(),sample);

    }

    @Test
    void testMapToStudentAddressDtoFromStudent(){
        //given
        String sample="sample";
        Student student= new Student();
        Adress adress = new Adress();

        adress.setAddressFirstLine(sample);
        adress.setPostCode(sample);
        adress.setId(22);
        student.setAdresses(adress);
        student.setId(44);


        student.setEmail(sample);
        student.setFirstName(sample);

        //then
        StudentAddressDto studentAddressDto = mapToStudentAddressDtoFromStudent(student);
        //when
        assertEquals(studentAddressDto.getEmail(),sample);
        assertEquals(studentAddressDto.getFirstName(),sample);
        assertEquals(studentAddressDto.getAddressFirstLine(),sample);
        assertEquals(studentAddressDto.getPostCode(),sample);
        assertEquals(studentAddressDto.getStudentId(),44);
        assertEquals(studentAddressDto.getAddressId(),22);




    }

}