package com.tomsapp.Toms.V2.repository;

import com.tomsapp.Toms.V2.entity.Adress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdressRepository extends JpaRepository<Adress, Integer> {


   Optional<List<Adress>> findAdressByAdressStudent_Id(int studentId);

}
