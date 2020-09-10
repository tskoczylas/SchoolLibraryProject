package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.entity.Adress;
import com.tomsapp.Toms.V2.entity.Student;
import com.tomsapp.Toms.V2.exeption.NoSuchUserExeptions;
import com.tomsapp.Toms.V2.repository.AdressRepository;
import com.tomsapp.Toms.V2.repository.StudentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class AdressService implements AdressServiceInt {

    AdressRepository adressRepository;
    StudentsRepository studentsRepository;


    @Autowired
    public AdressService(AdressRepository adressRepository, StudentsRepository studentsRepository) {
        this.adressRepository = adressRepository;
        this.studentsRepository = studentsRepository;
    }



    @Override
    public List<Adress> listofAdreeses() {
        return adressRepository.findAll();
    }

    @Override
    public void save(Adress adress, int studentID) {

        Optional<Student> optionalStudents = studentsRepository.findById(studentID);

        optionalStudents.ifPresent(student ->{
            adress.setAdressStudent(student);
            adressRepository.save(adress);
        });

        if(!optionalStudents.isPresent()){
            throw new  NoSuchUserExeptions();
        }
    }



    @Override
    public List<Adress> findAdressByStudentId(int studentId) {
        Optional<List<Adress>> findAdressByStudentId =
                adressRepository.
                        findAdressByAdressStudent_Id(studentId);
        return findAdressByStudentId.orElse(Collections.emptyList());


    }

    @Override
    public Student deleteAndFindStudent(int adressId) {
        Optional<Adress> adressOptional = adressRepository.findById(adressId);
        if(adressOptional.isPresent()
                &&adressOptional.
                map(Adress::getAdressStudent).
                isPresent()){
             adressRepository.deleteById(adressId);
            return adressOptional.map(Adress::getAdressStudent).get();
        }
        else throw new NoSuchUserExeptions();

    }

}
