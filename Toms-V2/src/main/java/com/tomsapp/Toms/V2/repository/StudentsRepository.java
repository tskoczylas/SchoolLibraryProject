package com.tomsapp.Toms.V2.repository;

import com.tomsapp.Toms.V2.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentsRepository extends JpaRepository<Student, Integer> {


    @Query(value = "select * from students where first_name like %:search% or last_name like %:search%" ,nativeQuery = true)
    List<Student> findStudentsByNameorSurname(@Param("search") String searchFied);

    Student findStudentsByEmail(String emailOrUsername);


}
