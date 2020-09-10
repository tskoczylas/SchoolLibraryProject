package com.tomsapp.Toms.V2.controller;

import com.tomsapp.Toms.V2.entity.Adress;
import com.tomsapp.Toms.V2.entity.Student;
import com.tomsapp.Toms.V2.exeption.NoSuchUserExeptions;
import com.tomsapp.Toms.V2.service.AdressService;
import com.tomsapp.Toms.V2.service.StudentServiceInt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@ExtendWith(SpringExtension.class)
//@WebMvcTest({AdressController.class, AdressServiceInt.class})

class AdressControllerTest {

@Mock
private AdressService adressService;

@Mock
private StudentServiceInt studentServiceInt;


@InjectMocks
private AdressController adressController;

private MockMvc mockMvc;


@BeforeEach
public void setup(){
    MockitoAnnotations.initMocks(this);
    mockMvc= MockMvcBuilders.standaloneSetup(adressController).build();

}


    @Test
    void showAdressForm() throws Exception {

    int studentId=1;

        Student tempStudent = new Student();
        List <Adress> adresses =
                Collections.singletonList
                        ( new Adress(1, "Simple", "Simple", "Simple", tempStudent));

        when(studentServiceInt.findbyId(studentId)).thenReturn(tempStudent);
        when(adressService.findAdressByStudentId(studentId)).thenReturn(adresses);
        mockMvc.perform(get("/adress/showAdress?studentId=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("showAdressForm"))
                .andExpect(model().attribute("adress",adresses))
                .andExpect(model().attribute("viewStudent",tempStudent));

    }

    @Test
    void addAdress() throws Exception {
        Student tempStudent = new Student();
        List <Adress> adresses =
                Collections.singletonList
                        ( new Adress(1, "Simple", "Simple", "Simple", tempStudent));

        when(studentServiceInt.findbyId(22)).thenThrow(NoSuchUserExeptions.class);
        when(adressService.findAdressByStudentId(22)).thenReturn(adresses);


        mockMvc.perform(get("/adress/showAdress?studentId=22"))
                .andExpect(status().isInternalServerError());


    }

    @Test
    void saveAdress() {

    }

    @Test
    void deleteAdress() {
    }
}