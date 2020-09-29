package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.dto.StudentAddressDto;
import com.tomsapp.Toms.V2.entity.Adress;
import com.tomsapp.Toms.V2.entity.Student;
import com.tomsapp.Toms.V2.enums.Role;
import com.tomsapp.Toms.V2.exeption.NoSuchUserExeptions;
import com.tomsapp.Toms.V2.exeption.UnfilledStudentUserExceptions;
import com.tomsapp.Toms.V2.mapper.StudentAddressMaper;
import com.tomsapp.Toms.V2.repository.AdressRepository;
import com.tomsapp.Toms.V2.repository.StudentsRepository;
import com.tomsapp.Toms.V2.security.StudentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tomsapp.Toms.V2.mapper.StudentAddressMaper.*;

@Controller
public class StudentAddressServiceImp implements StudentAddressService {

  protected AdressRepository adressRepository;
    protected StudentsRepository studentsRepository;

@Autowired
    public StudentAddressServiceImp(AdressRepository adressRepository, StudentsRepository studentsRepository) {
        this.adressRepository = adressRepository;
        this.studentsRepository = studentsRepository;
    }

    @Override
   public void  editStudentAndAddress(StudentAddressDto studentAddressDto){
    if(studentAddressDto.getPassword()!=null
            &&studentAddressDto.getEmail()!=null
            &&studentAddressDto.getAddressId()!=0
         &&studentAddressDto.getStudentId()!=0)
    {
        Student student = mapToStudentFromStudentAddressDto(studentAddressDto);
        Adress adress = mapToAddressFromStudentAddressDto(studentAddressDto);
        student.setEnabled(true);
        student.setRole(Role.ROLE_USER);
        student.setAdresses(adress);
        adress.setAdressStudent(student);
        studentsRepository.save(student);
        adressRepository.save(adress);}
    else throw new UnfilledStudentUserExceptions();
    }




}
