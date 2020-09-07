package com.tomsapp.Toms.V2.repository;

import com.tomsapp.Toms.V2.entity.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface StudentsRepository extends JpaRepository<Students, Integer> {


    @Query(value = "select * from students where first_name like %:search% or last_name like %:search%" ,nativeQuery = true)
    List<Students> findStudentsByNameorSurname(@Param("search") String searchFied);

    Students findStudentsByEmail(String emailOrUsername);


}
