package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.entity.Adress;
import com.tomsapp.Toms.V2.entity.Students;
import com.tomsapp.Toms.V2.exeption.NoSuchUserExeptions;
import com.tomsapp.Toms.V2.repository.AdressRepository;
import org.junit.jupiter.api.Test;
import org.springframework.expression.spel.ast.OpInc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdressServiceTest {

    @Test
    void listofAdreesesShuldProvideListOfAddresses() {
        //given
        AdressRepository adressRepository = mock(AdressRepository.class);
        AdressService adressService = new AdressService(adressRepository);
        //when
        when(adressRepository.findAll()).thenReturn(Arrays.asList(new Adress(),new Adress()));
        //then
        assertThat(adressService.listofAdreeses(),hasSize(2));

    }

    @Test
        void saveAddressShouldThrownNoSuchUserExemptionWhenStudentIsNotPresent() {
            //given
        Students tempStudent = new Students();
      Adress tempAddress =
              new Adress(1, "Simple", "Simple", "Simple", null);

            AdressRepository adressRepository = mock(AdressRepository.class);
            AdressService adressService = new AdressService(adressRepository);
            //when
            //then
            assertThrows(NoSuchUserExeptions.class,()->adressService.save(tempAddress));
        }

    @Test
    void saveAdressWhenStudentPresent() {
        //given
        Students tempStudent = new Students();
        Adress tempAddress =
                new Adress(1, "Simple", "Simple", "Simple", new Students());

        AdressRepository adressRepository = mock(AdressRepository.class);
        AdressService adressService = new AdressService(adressRepository);
        //when
        adressService.save(tempAddress);
        //then
        verify(adressRepository).save(tempAddress);


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

        assertThat(adressService.findAdressByStudentId(1), hasSize(1));
        assertThat(adressService.findAdressByStudentId(3), hasSize(0));


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

        assertThat(adressService.findAdressByStudentId(3), hasSize(0));


    }

    @Test
    void deleteAndFindStudentShouldReturnStudentAndDeleteAdressWhenAddressExistAndStudentProvided() {
        //given
        Students tempStudent = new Students();
        Optional<Adress> adress = Optional.of(
                new Adress(1, "Simple", "Simple", "Simple", tempStudent));
        AdressRepository adressRepository = mock(AdressRepository.class);
        AdressService adressService = new AdressService(adressRepository);
        //when
        when(adressRepository.findById(1)).thenReturn(adress);
        //then
        assertThat(adressService.deleteAndFindStudent(1), equalTo(tempStudent));
        verify(adressRepository).deleteById(1);
    }
    @Test
    void deleteAndFindStudentShouldThrownNoSouthUserExertionWhenStudentNull() {
        //given
        Optional<Adress> adress = Optional.of(
                new Adress(1, "Simple", "Simple", "Simple", null));
        AdressRepository adressRepository = mock(AdressRepository.class);
        AdressService adressService = new AdressService(adressRepository);
        //when
        when(adressRepository.findById(1)).thenReturn(adress);
        //then
        assertThrows(NoSuchUserExeptions.class,()->adressService.deleteAndFindStudent(1));
    }

    @Test
    void deleteAndFindStudentShouldThrownNoSouthUserExertionWhenAddressDoesNotExist() {
        //given
        AdressRepository adressRepository = mock(AdressRepository.class);
        AdressService adressService = new AdressService(adressRepository);
        //when
        when(adressRepository.findById(1)).thenReturn(Optional.empty());
        //then
        assertThrows(NoSuchUserExeptions.class,()->adressService.deleteAndFindStudent(1));
    }




}