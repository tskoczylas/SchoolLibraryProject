package com.tomsapp.Toms.V2.repository;

import com.tomsapp.Toms.V2.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentsRepository extends JpaRepository<Student, Integer> {


    @Query(value = "select u from Student u where u.firstName like %:search% or u.lastName like %:search%")
    List<Student> findStudentsByNameorSurname(@Param("search") String searchFied);
@Query(value = "select u from Student u where u.email like :key")
    Optional<Student> findStudentForSecurity(@Param("key") String emailOrUsername);




}
