package com.tomsapp.Toms.V2.controller;

import com.tomsapp.Toms.V2.dto.StudentAddressDto;
import com.tomsapp.Toms.V2.dto.StudentAddressEditDto;
import com.tomsapp.Toms.V2.entity.Borrow;
import com.tomsapp.Toms.V2.entity.Student;
import com.tomsapp.Toms.V2.service.BorrowService;
import com.tomsapp.Toms.V2.service.EmailService;
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

import static com.tomsapp.Toms.V2.mapper.StudentAddressEditMapper.mapToStudentAddressEditDtoFromStudent;
import static com.tomsapp.Toms.V2.mapper.StudentAddressMaper.mapToStudentAddressDtoFromStudent;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

class CartControllerTest {

    @Mock
    BasketSession basketSession;
    @Mock
    StudentService studentService;
    @Mock
    BorrowService borrowService;
    @Mock
    EmailService emailService;






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
    void edit_dataGetShouldProvideSAEDtofromStudentLogIn() throws Exception {
        Student student = new Student();
        //when
        when(studentService.findLogInStudent()).thenReturn(student);
        StudentAddressEditDto studentAddressEditDto = mapToStudentAddressEditDtoFromStudent(student);

        //then

        MockHttpServletRequestBuilder updateDetails = get("/edit_data/");

        mockMvc.perform(updateDetails)
                .andExpect(status().isOk())
                .andExpect(view().name("edit_address"))
                .andExpect(model().attribute("studentAddressDto",studentAddressEditDto));
    }

    @Test
    void edit_dataPostShouldRedirectToCheckoutAndExecuteEditMessageWhenBiddingWithoutErrors() throws Exception {
        StudentAddressEditDto studentAddressEditDto=new StudentAddressEditDto();
        studentAddressEditDto.setCountry("Poland");
        studentAddressEditDto.setFirstName("Tomasz");
        studentAddressEditDto.setLastName("Simple");
        studentAddressEditDto.setAddressSecondLine("Squere 3");
        studentAddressEditDto.setAddressFirstLine("Brighton");
        studentAddressEditDto.setPostCode("KJ82JS");
        studentAddressEditDto.setTelephone("33333333");

        //when

        //then

        MockHttpServletRequestBuilder updateDetails = post("/edit_data/")
                .flashAttr("studentAddressDto",studentAddressEditDto);

        mockMvc.perform(updateDetails)
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/checkout"));
        verify(studentService).editStudentAndAddress(studentAddressEditDto);

    }

    @Test
    void edit_dataPostShouldRedirectToEditWhenBiddingHaveErrors() throws Exception {
        StudentAddressEditDto studentAddressEditDto=new StudentAddressEditDto();
        studentAddressEditDto.setCountry("Poland");
        studentAddressEditDto.setLastName("Simple");
        studentAddressEditDto.setAddressSecondLine("Squere 3");
        studentAddressEditDto.setAddressFirstLine("Brighton");
        studentAddressEditDto.setPostCode("KJ82JS");
        studentAddressEditDto.setTelephone("33333333");
        //when
        MockHttpServletRequestBuilder updateDetails = post("/edit_data/")
                .flashAttr("studentAddressDto",studentAddressEditDto);
        //then
        mockMvc.perform(updateDetails)
                .andExpect(status().isOk())
                .andExpect(view().name("edit_address"))
                .andExpect(model().attribute("studentAddressDto",studentAddressEditDto));
    }

    @Test
    void processShouldReturnMessageWhenBasketIsFull() throws Exception {
        //given

        //when
        when(basketSession.isEmpty()).thenReturn(true);
        MockHttpServletRequestBuilder updateDetails = get("/process/");
        //then
        mockMvc.perform(updateDetails)
                .andExpect(status().isOk())
                .andExpect(view().name("message"))
                .andExpect(model().attribute("messageTitle",is(not(emptyString()))))
                .andExpect(model().attribute("messageText",is(not(emptyString()))))
                .andExpect(model().attribute("link",is(not(emptyString()))))
                .andExpect(model().attribute("linkMes",is(not(emptyString()))));

    }

    @Test
    void processShouldReturnMessageWhenBasketIsNotFullAndSaveBorrowSendMessageAndCleanBasket() throws Exception {
        //given
        when(basketSession.isEmpty()).thenReturn(false);
        when(borrowService.logInStudentHasRightToOrder()).thenReturn(true);
        //when

        MockHttpServletRequestBuilder updateDetails = get("/process/");
        //then
        Borrow borrow = new Borrow();
    //    when(borrowService.saveNewBorrow()).thenReturn(borrow);

        mockMvc.perform(updateDetails)
                .andExpect(status().isOk())
                .andExpect(view().name("message"))
                .andExpect(model().attribute("messageTitle",is(not(emptyString()))))
                .andExpect(model().attribute("messageText",is(not(emptyString()))))
                .andExpect(model().attribute("link",is(not(emptyString()))))
                .andExpect(model().attribute("linkMes",is(not(emptyString()))));

       // verify(emailService).sendConformationMessageNewOrder(borrow);
        verify(basketSession).cleanBooksBasket();
        verify(basketSession).resetBorrowDaysEnum();

    }

    @Test
    void processShouldReturnMessageWhenBasketIsFullAndUserDoesntHaveRightToOrder() throws Exception {
        //given
        when(basketSession.isEmpty()).thenReturn(false);
        when(borrowService.logInStudentHasRightToOrder()).thenReturn(false);
        //when

        MockHttpServletRequestBuilder updateDetails = get("/process/");
        //then
        mockMvc.perform(updateDetails)
                .andExpect(status().isOk())
                .andExpect(view().name("message"))
                .andExpect(model().attribute("messageTitle",is(not(emptyString()))))
                .andExpect(model().attribute("messageText",is(not(emptyString()))))
                .andExpect(model().attribute("link",is(not(emptyString()))))
                .andExpect(model().attribute("linkMes",is(not(emptyString()))));


    }

}