package com.tomsapp.Toms.V2.mapper;

import com.tomsapp.Toms.V2.dto.StudentAddressDto;
import com.tomsapp.Toms.V2.dto.StudentAddressEditDto;
import com.tomsapp.Toms.V2.entity.Adress;
import com.tomsapp.Toms.V2.entity.Student;
import com.tomsapp.Toms.V2.mapper.StudentAddressEditMapper;
import org.junit.jupiter.api.Test;

import static com.tomsapp.Toms.V2.mapper.StudentAddressEditMapper.mapToStudentAddressEditDtoFromStudent;
import static com.tomsapp.Toms.V2.mapper.StudentAddressMaper.mapToStudentAddressDtoFromStudent;
import static com.tomsapp.Toms.V2.mapper.StudentAddressMaper.mapToStudentFromStudentAddressDto;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentAddressEditMapperTest {

    @Test
    void mapToAddressFromStudentAddressEditDto() {
        //given
        StudentAddressEditDto studentAddressEditDto = new StudentAddressEditDto();
        String sample= "sample";
        studentAddressEditDto.setAddressFirstLine(sample);
        studentAddressEditDto.setAddressId(2);
        studentAddressEditDto.setAddressSecondLine(sample);
        studentAddressEditDto.setCountry(sample);
        //then
        Adress adress = StudentAddressEditMapper.mapToAddressFromStudentAddressEditDto(studentAddressEditDto);
        //when
        assertEquals(sample,adress.getAddressFirstLine());
        assertEquals(2,adress.getId());
        assertEquals(sample,adress.getAddressSecondLine());
        assertEquals(sample,adress.getCountry());


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

        student.setFirstName(sample);

        //then
        StudentAddressEditDto studentAddressEditDto = mapToStudentAddressEditDtoFromStudent(student);
        //when
        assertEquals(studentAddressEditDto.getFirstName(),sample);
        assertEquals(studentAddressEditDto.getAddressFirstLine(),sample);
        assertEquals(studentAddressEditDto.getPostCode(),sample);
        assertEquals(studentAddressEditDto.getAddressId(),22);




    }
}