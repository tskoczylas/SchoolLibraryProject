package com.tomsapp.Toms.V2.controller;

import com.tomsapp.Toms.V2.dto.StudentAddressDto;
import com.tomsapp.Toms.V2.entity.Student;
import com.tomsapp.Toms.V2.service.BorrowService;
import com.tomsapp.Toms.V2.service.StudentAddressService;
import com.tomsapp.Toms.V2.service.StudentService;
import com.tomsapp.Toms.V2.session.BasketSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.tomsapp.Toms.V2.mapper.StudentAddressMaper.mapToStudentAddressDtoFromStudent;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

class CartControllerTest {

    @Mock
    BasketSession basketSession;
    @Mock
    StudentService studentService;
    @Mock
    StudentAddressService studentAddressService;
    @Mock
    BorrowService borrowService;


    @InjectMocks
    private CartController cartController;

    private MockMvc mockMvc;


    @BeforeEach
    public void setup(){

        MockitoAnnotations.initMocks(this);
        mockMvc= MockMvcBuilders.standaloneSetup(cartController).build();}

    @Test
    void checkoutShouldAddToModelLogInStAddDoAndBasketReaParamShouldSetBasketWhenAvailable() throws Exception {
        //given
        Student student = new Student();
        //when
        when(studentService.findLogInStudent()).thenReturn(student);
        StudentAddressDto studentAddressDto = mapToStudentAddressDtoFromStudent(student);

        //then

        MockHttpServletRequestBuilder updateDetails = get("/checkout/")
                .param("borrowDays", "MAX")
                .param("bookId","6");
        mockMvc.perform(updateDetails)
                .andExpect(status().isOk())
                .andExpect(view().name("checkout"))
                .andExpect(model().attribute("logInStudent",studentAddressDto))
                .andExpect(model().attribute("basketSession",basketSession));
        verify(basketSession).removeBookFromBasket("6");
        verify(basketSession).changeDayAndCalculateBorrowCost("MAX");

    }

    @Test
    void checkoutShouldAddToModelLogInStAddDoAndBasketReaParamShouldSetBasketWhenNotAvailable() throws Exception {
        //given
        Student student = new Student();
        //when
        when(studentService.findLogInStudent()).thenReturn(student);
        StudentAddressDto studentAddressDto = mapToStudentAddressDtoFromStudent(student);

        //then

        MockHttpServletRequestBuilder updateDetails = get("/checkout/");

        mockMvc.perform(updateDetails)
                .andExpect(status().isOk())
                .andExpect(view().name("checkout"))
                .andExpect(model().attribute("logInStudent",studentAddressDto))
                .andExpect(model().attribute("basketSession",basketSession));
        verify(basketSession).removeBookFromBasket(null);
        verify(basketSession).changeDayAndCalculateBorrowCost(null);

    }

    @Test
    void editAddress() {
    }

    @Test
    void testEditAddress() {
    }

    @Test
    void processOrder() {
    }
}