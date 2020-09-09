package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.entity.Adress;
import com.tomsapp.Toms.V2.entity.Students;

import java.util.List;

public interface AdressServiceInt {

    List<Adress> listofAdreeses();

    void save(Adress adress, int studentID);

    List<Adress> findAdressByStudentId(int studentId);

    Students deleteAndFindStudent(int adressId);
}
