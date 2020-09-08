package com.tomsapp.Toms.V2.repository;

import com.tomsapp.Toms.V2.entity.Adress;
import com.tomsapp.Toms.V2.entity.Students;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdressRepository extends JpaRepository<Adress, Integer> {


   Optional<List<Adress>> findAdressByAdressStudents_Id(int studentId);

}
