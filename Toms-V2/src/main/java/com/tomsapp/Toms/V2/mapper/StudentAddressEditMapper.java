package com.tomsapp.Toms.V2.mapper;

import com.tomsapp.Toms.V2.dto.StudentAddressDto;
import com.tomsapp.Toms.V2.dto.StudentAddressEditDto;
import com.tomsapp.Toms.V2.entity.Adress;
import com.tomsapp.Toms.V2.entity.Student;
import com.tomsapp.Toms.V2.entity.Token;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;

public class StudentAddressEditMapper {

    static ModelMapper modelMapper;

    public static Adress mapToAddressFromStudentAddressEditDto(StudentAddressEditDto studentAddressEditDto) {
        Adress adress = new Adress();
        modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<StudentAddressEditDto, Adress>() {
            @Override
            protected void configure() {
                map().setId(source.getAddressId());

            }
        });
        modelMapper.map(studentAddressEditDto, adress);
        return adress;
    }

    public static StudentAddressEditDto mapToStudentAddressEditDtoFromStudent(Student student) {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        modelMapper.addMappings(new PropertyMap<Student, StudentAddressEditDto>() {
            @Override
            protected void configure() {
                map().setAddressId(source.getAdresses().getId());
            }
        });

        StudentAddressEditDto map = modelMapper.map(student, StudentAddressEditDto.class);
        return map;




    }
    }
