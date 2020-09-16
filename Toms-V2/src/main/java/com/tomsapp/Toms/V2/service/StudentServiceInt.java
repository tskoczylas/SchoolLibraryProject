package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.entity.Student;

import java.util.List;

public interface StudentServiceInt {


    public List<Student> getStudents();
    public void saveSrudent(Student tempStudent);
    public Student findbyId(int studentId);


    void deleteStudentbyId(int studentId);

    List<Student> findStudentsByNameorSurname(String shearchField);

    public Student findStudentByEmailorUsername(String emailOrUsername);

    Student getUserStudent();
}
