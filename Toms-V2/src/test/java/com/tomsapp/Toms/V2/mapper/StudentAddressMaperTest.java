package com.tomsapp.Toms.V2.mapper;

import com.tomsapp.Toms.V2.dto.StudentAddressDto;
import com.tomsapp.Toms.V2.entity.Student;
import org.junit.jupiter.api.Test;

import static com.tomsapp.Toms.V2.mapper.StudentAddressMaper.mapToStudentFromStudentAddressDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.isEquals;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class StudentAddressMaperTest {

    @Test
    void AddressDtoShouldBeMapToStudent() {
        //given
        StudentAddressDto studentAddressDto = new StudentAddressDto();
        studentAddressDto.setEmail("sample@sample.pl");
        studentAddressDto.setConfirmEmail("sample@sample.pl");
        //then
        Student student = mapToStudentFromStudentAddressDto(studentAddressDto);
        //when
        assertEquals(studentAddressDto.getEmail(),student.getEmail());


    }

}