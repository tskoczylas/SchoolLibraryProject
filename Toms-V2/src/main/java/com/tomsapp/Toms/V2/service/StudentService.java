package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.entity.Role;
import com.tomsapp.Toms.V2.entity.RoleEnum;
import com.tomsapp.Toms.V2.entity.Student;
import com.tomsapp.Toms.V2.exeption.NoSuchUserExeptions;
import com.tomsapp.Toms.V2.repository.StudentsRepository;
import com.tomsapp.Toms.V2.security.StudentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService implements StudentServiceInt {


    StudentsRepository studentsRepository;

    @Autowired
    public StudentService(StudentsRepository studentsRepository) {
        this.studentsRepository = studentsRepository;

    }




    List<Student> findStudentsByAuthentication(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> listOfAuthorities = authentication.
                getAuthorities().
                stream().map(GrantedAuthority::getAuthority).
                collect(Collectors.toList());

        if(authentication.isAuthenticated()&&listOfAuthorities.contains(RoleEnum.ROLE_USER.name())){
            List<Student> students = new java.util.ArrayList<>();
            StudentUser principal = (StudentUser) authentication.getPrincipal();
            Student student1 = principal.getStudent();
            students.add(student1);
            return students;}
        else if(authentication.isAuthenticated()&&listOfAuthorities.contains(RoleEnum.ROLE_ADMIN.name())){
            return studentsRepository.findAll(); }
        else return Collections.emptyList();
    }





    @Override
    public List<Student> getStudents() {


        return findStudentsByAuthentication();
    }

    @Override
    public void saveSrudent(Student tempStudent) {
        studentsRepository.save(tempStudent);
    }

    @Override
    public Student findbyId(int studentId) {
        if(studentsRepository.findById(studentId).isPresent())
            return studentsRepository.findById(studentId).get();
        else throw new NoSuchUserExeptions();


    }

    @Override
    public void deleteStudentbyId(int studentId) {
        studentsRepository.deleteById(studentId);
    }

    @Override
    public List<Student> findStudentsByNameorSurname(String shearchField) {
        return studentsRepository.findStudentsByNameorSurname(shearchField);
    }

    @Override
    public Student findStudentByEmailorUsername(String emailOrUsername) {
        return studentsRepository.findStudentsByEmail(emailOrUsername);
    }


}
