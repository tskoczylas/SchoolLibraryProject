package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.entity.Adress;
import com.tomsapp.Toms.V2.entity.Students;
import com.tomsapp.Toms.V2.exeption.NoSuchUserExeptions;
import com.tomsapp.Toms.V2.repository.AdressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
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

        if(adress.getAdressStudents()==null) throw new  NoSuchUserExeptions();
        else adressRepository.save(adress);
    }



    @Override
    public List<Adress> findAdressByStudentId(int studentId) {
        Optional<List<Adress>> findAdressByStudentId =
                adressRepository.
                        findAdressByAdressStudents_Id(studentId);
        return findAdressByStudentId.orElse(Collections.emptyList());


    }

    @Override
    public Students deleteAndFindStudent(int adressId) {
        Optional<Adress> adressOptional = adressRepository.findById(adressId);
        if(adressOptional.isPresent()
                &&adressOptional.
                map(Adress::getAdressStudents).
                isPresent()){
             adressRepository.deleteById(adressId);
            return adressOptional.map(Adress::getAdressStudents).get();
        }
        else throw new NoSuchUserExeptions();

    }

}
