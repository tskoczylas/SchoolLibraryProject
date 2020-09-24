package com.tomsapp.Toms.V2.mapper;

import com.tomsapp.Toms.V2.dto.StudentAddressDto;
import com.tomsapp.Toms.V2.entity.Adress;
import com.tomsapp.Toms.V2.entity.Student;
import com.tomsapp.Toms.V2.entity.Token;
import org.modelmapper.ModelMapper;

public class StudentAddressMaper {

 static   ModelMapper modelMapper;


    public static StudentAddressDto mapToStudentAddressDtoFromToken(Token token) {
        Student student = token.getStudent();
        modelMapper = new ModelMapper();
        StudentAddressDto studentAddressDto = new StudentAddressDto();
        modelMapper.map(student, studentAddressDto);
        return studentAddressDto;
    }

    public static Student mapToStudentFromStudentAddressDto(StudentAddressDto studentAddressDto){
        Student student = new Student();
        modelMapper = new ModelMapper();
        modelMapper.map(studentAddressDto,student);
        return student;
    }
    public static Adress mapToAddressFromStudentAddressDto(StudentAddressDto studentAddressDto){
        Adress adress = new Adress();
        modelMapper = new ModelMapper();
        modelMapper.map(studentAddressDto,adress);
        return adress;
    }
}
