package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.entity.Students;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface StudentServiceInt {


    public List<Students> getStudents();
    public void saveSrudent(Students tempStudents);
    public Students findbyId(int studentId);


    void deleteStudentbyId(int studentId);

    List<Students> findStudentsByNameorSurname(String shearchField);

    public Students findStudentByEmailorUsername(String emailOrUsername);
}
