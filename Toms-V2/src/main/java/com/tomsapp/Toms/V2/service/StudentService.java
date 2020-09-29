package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.dto.StudentAddressDto;
import com.tomsapp.Toms.V2.entity.Adress;
import com.tomsapp.Toms.V2.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {



    public void saveSrudent(Student tempStudent);


    public Student findStudentByEmailorUsername(String emailOrUsername);



    Optional<Student> findStudentByEmail(String email);

    void saveStudentUserActivateAndAssignAddress(Student student, Adress adress);

    Student findLogInStudent();
}
