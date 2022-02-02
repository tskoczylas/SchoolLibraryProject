package com.tomsapp.Toms.V2.controller;

import org.junit.jupiter.api.Disabled;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

//@ExtendWith(SpringExtension.class)
//@WebMvcTest({AdressController.class, AdressServiceInt.class})
@Disabled
class AdressControllerTest {
/*
@Mock
private AdressService adressService;

@Mock
private StudentService studentService;


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
        Adress adresse = new Adress(1, "Simple", "Simple", "Simple", tempStudent);

        when(studentService.findbyId(studentId)).thenReturn(tempStudent);
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

        when(studentService.findbyId(22)).thenThrow(NoSuchUserExeptions.class);
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


 */
}