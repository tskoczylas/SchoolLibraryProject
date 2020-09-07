package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.entity.Adress;
import com.tomsapp.Toms.V2.entity.Students;
import com.tomsapp.Toms.V2.repository.AdressRepository;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AdressServiceTest {

    @Test
    void listofAdreeses() {
    }

    @Test
    void save() {
    }

    @Test
    void delete() {
    }

    @Test
    void getById() {
    }

    @Test
    void FindAdressByStudentIdShuldReturnStudent() {
        //given
        AdressRepository adressRepository = mock(AdressRepository.class);
        Optional<List<Adress>> tempAdress = Optional.of(Collections.singletonList(new Adress()));
        AdressService adressService = new AdressService(adressRepository);
        //when
        when(adressRepository.findAdressByAdressStudents_Id(1)).
                thenReturn(tempAdress);
        //then

        assertThat(adressService.findAdressByStudentId(1),hasSize(1));
        assertThat(adressService.findAdressByStudentId(3),hasSize(0));


    }

    @Test
    void FindAdressByStudentIdShuldReturnEmptyList() {
        //given
        AdressRepository adressRepository = mock(AdressRepository.class);
        Optional<List<Adress>> tempAdress = Optional.of(Collections.singletonList(new Adress()));
        AdressService adressService = new AdressService(adressRepository);
        //when
        when(adressRepository.findAdressByAdressStudents_Id(1)).
                thenReturn(tempAdress);
        //then

        assertThat(adressService.findAdressByStudentId(3),hasSize(0));


    }
}