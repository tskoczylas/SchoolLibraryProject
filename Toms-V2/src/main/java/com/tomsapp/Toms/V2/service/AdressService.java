package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.entity.Adress;
import com.tomsapp.Toms.V2.repository.AdressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class AdressService implements AdressServiceInt {

    AdressRepository adressRepository;

    @Autowired
    public AdressService(AdressRepository adressRepository) {
        this.adressRepository = adressRepository;
    }




    @Override
    public List<Adress> listofAdreeses() {
        return adressRepository.findAll();
    }

    @Override
    public void save(Adress adress) {
        adressRepository.save(adress);
    }

    @Override
    public void delete(int adressID) {
        adressRepository.deleteById(adressID);
    }

    @Override
    public Adress getById(int adressID) {
        return adressRepository.getOne(adressID);
    }

    @Override
    public List<Adress> findAdressByStudentId(int studentId) {
        Optional<List<Adress>> findAdressByStudentId =
                adressRepository.
                        findAdressByAdressStudents_Id(studentId);
        return findAdressByStudentId.orElse(Collections.emptyList());


    }

}
