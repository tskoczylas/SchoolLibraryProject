package com.tomsapp.Toms.V2.mapper;

import com.tomsapp.Toms.V2.dto.StudentAddressDto;
import com.tomsapp.Toms.V2.entity.Adress;
import com.tomsapp.Toms.V2.entity.Student;
import com.tomsapp.Toms.V2.entity.Token;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NamingConventions;
import org.modelmapper.spi.MatchingStrategy;

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
        modelMapper.addMappings(new PropertyMap<StudentAddressDto, Student>() {
            @Override
            protected void configure() {
                map().setId(source.getStudentId());

            }});
        modelMapper.map(studentAddressDto,student);
        return student;
    }
    public static Adress mapToAddressFromStudentAddressDto(StudentAddressDto studentAddressDto){
        Adress adress = new Adress();
        modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<StudentAddressDto, Adress>() {
            @Override
            protected void configure() {
                map().setId(source.getAddressId());

            }});
        modelMapper.map(studentAddressDto,adress);
        return adress;
    }
    public static StudentAddressDto mapToStudentAddressDtoFromStudent(Student student){
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        modelMapper.addMappings(new PropertyMap<Student, StudentAddressDto>() {
            @Override
            protected void configure() {
                map().setAddressId(source.getAdresses().getId());
                map().setStudentId(source.getId());

        }});
        StudentAddressDto map = modelMapper.map(student, StudentAddressDto.class);
        return map;
    }

}
