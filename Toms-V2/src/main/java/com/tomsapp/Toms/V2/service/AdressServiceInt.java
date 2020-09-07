package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.entity.Adress;

import java.util.List;

public interface AdressServiceInt {

    List<Adress> listofAdreeses();

    void save(Adress adress);

    void delete(int adressID);

    Adress getById(int adressID);

    List<Adress> findAdressByStudentId(int studentId);
}
