package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.entity.RoleEnum;
import com.tomsapp.Toms.V2.entity.Students;
import com.tomsapp.Toms.V2.repository.StudentsRepository;
import com.tomsapp.Toms.V2.security.StudentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService implements StudentServiceInt {

    @Autowired
    StudentsRepository studentsRepository;

    List<Students> findStudentsByAuthentication(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> listOfAuthorities = authentication.
                getAuthorities().
                stream().map(GrantedAuthority::getAuthority).
                collect(Collectors.toList());

        if(authentication.isAuthenticated()&&listOfAuthorities.contains(RoleEnum.ROLE_USER.name())){
            List<Students> students = new java.util.ArrayList<>();
            StudentUser principal = (StudentUser) authentication.getPrincipal();
            Students students1 = principal.getStudents();
            students.add(students1);
            return students;}
        else if(authentication.isAuthenticated()&&listOfAuthorities.contains(RoleEnum.ROLE_ADMIN.name())){
            return studentsRepository.findAll(); }
        else return Collections.emptyList();
    }


    @Override
    public List<Students> getStudents() {


        return findStudentsByAuthentication();
    }

    @Override
    public void saveSrudent(Students tempStudents) {
        studentsRepository.save(tempStudents);
    }

    @Override
    public Students findbyId(int studentId) {
        if(studentsRepository.findById(studentId).isPresent())
            return studentsRepository.findById(studentId).get();
        else throw new NullPointerException("Nie ma takiego uztkownika");

    }

    @Override
    public void deleteStudentbyId(int studentId) {
        studentsRepository.deleteById(studentId);
    }

    @Override
    public List<Students> findStudentsByNameorSurname(String shearchField) {
        return studentsRepository.findStudentsByNameorSurname(shearchField);
    }

    @Override
    public Students findStudentByEmailorUsername(String emailOrUsername) {
        return studentsRepository.findStudentsByEmail(emailOrUsername);
    }


}
