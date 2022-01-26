package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.dto.StudentAddressDto;
import com.tomsapp.Toms.V2.dto.StudentAddressEditDto;
import com.tomsapp.Toms.V2.entity.Adress;
import com.tomsapp.Toms.V2.enums.Role;
import com.tomsapp.Toms.V2.entity.Student;
import com.tomsapp.Toms.V2.exeption.NoSuchUserExeptions;
import com.tomsapp.Toms.V2.mapper.StudentAddressMaper;
import com.tomsapp.Toms.V2.repository.StudentsRepository;
import com.tomsapp.Toms.V2.security.StudentUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tomsapp.Toms.V2.mapper.StudentAddressEditMapper.mapToAddressFromStudentAddressEditDto;
import static com.tomsapp.Toms.V2.mapper.StudentAddressMaper.*;

@Service
public class StudentServiceInt implements StudentService {


    StudentsRepository studentsRepository;

    public StudentServiceInt(StudentsRepository studentsRepository) {
        this.studentsRepository = studentsRepository;
    }



    @Override
    public void saveStudent(Student tempStudent) {
        studentsRepository.save(tempStudent);
    }


    @Override
    public Student findStudentByEmailorUsername(String emailOrUsername) {


        return studentsRepository.
                findStudentForSecurity(emailOrUsername).orElseGet(Student::new);
    }


    @Override
    public Optional<Student> findStudentByEmail(String email) {
        return studentsRepository.findStudentForSecurity(email);
    }

    @Override
    public void saveStudentUserActivateAndAssignAddress(Student student, Adress adress){
       BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        adress.setAdressStudent(student);
        student.setRole(Role.ROLE_USER);
        student.setAdresses(adress);
        student.setEnabled(true);
       student.setPassword(passwordEncoder.encode(student.getPassword()));
        studentsRepository.save(student);

    }

    @Override
    public Student findLogInStudent() {

        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication().getPrincipal();

        if (principal instanceof StudentUser ) {
            StudentUser studentUser = (StudentUser) principal;
            return studentsRepository.
                    findById(studentUser.
                            getStudent().
                            getId()).
                    orElseThrow(NoSuchUserExeptions::new);}
        else throw new  NoSuchUserExeptions();
    }


    @Override
    public void  editStudentAndAddress(StudentAddressEditDto studentAddressEditDto){

        if(studentAddressEditDto.getAddressId()!=0 ){
        Adress adress = mapToAddressFromStudentAddressEditDto(studentAddressEditDto);
        Student logInStudent = findLogInStudent();
        logInStudent.setAdresses(adress);
        logInStudent.setFirstName(studentAddressEditDto.getFirstName());
        logInStudent.setLastName(studentAddressEditDto.getLastName());
        adress.setAdressStudent(logInStudent);
        studentsRepository.save(logInStudent);}
        else
            throw new IllegalArgumentException();
}


}
