package com.tomsapp.Toms.V2.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.tomsapp.Toms.V2.dto.BorrowDto;
import com.tomsapp.Toms.V2.entity.Adress;
import com.tomsapp.Toms.V2.entity.Books;
import com.tomsapp.Toms.V2.entity.Student;
import com.tomsapp.Toms.V2.paypal.PaypalService;
import com.tomsapp.Toms.V2.repository.BorrowRepository;
import com.tomsapp.Toms.V2.service.BorrowService;
import com.tomsapp.Toms.V2.service.EmailService;
import com.tomsapp.Toms.V2.service.StudentService;
import com.tomsapp.Toms.V2.session.BasketSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static io.specto.hoverfly.junit.dsl.HoverflyDsl.service;
import static io.specto.hoverfly.junit.dsl.HttpBodyConverter.json;
import static io.specto.hoverfly.junit.dsl.ResponseCreators.created;
import static io.specto.hoverfly.junit.dsl.ResponseCreators.success;
import static io.specto.hoverfly.junit.dsl.matchers.HoverflyMatchers.equalsToJson;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.emptyString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.OpenAPIDefinition.openAPI;
import static org.mockserver.model.StringBody.exact;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
class PaymentControllerTest {
    @Mock
    BasketSession basketSession;
    @Mock
    StudentService studentService;
    @Mock
    BorrowService borrowService;
    @Mock
    EmailService emailService;
    @Mock
    PaypalService paypalService;
    @Mock
    BorrowRepository borrowRepository;

    @Captor
    ArgumentCaptor<BorrowDto> borrowDtoArgumentCaptor;



    @Captor
    ArgumentCaptor<EmailService> emailServiceArgumentCaptor;





    @InjectMocks
    private PaymentController paymentController;

    private MockMvc mockMvc;
    BorrowDto borrow;



    @BeforeEach
    public void setup(){

            borrow = new BorrowDto();
            borrow.setId(1);
            borrow.setPricePerItem(0.3);
            borrow.setDaysBorrow(2);
            borrow.setOverDueFee(2);
            borrow.setTotalCost(0.6);
            Student student = new Student();
            student.setFirstName("FirstName");
            student.setLastName("LastName");
            student.setEmail("email");

            Adress adress = new Adress();
            adress.setPostCode("postCode");
            adress.setAddressFirstLine("first");
            adress.setAddressSecondLine("second");
            adress.setTelephone("1");
            student.setAdresses(adress);
            Books books = new Books();
            books.setTitle("abc");
            Books books2 = new Books();
            books2.setTitle("bcc");
            List<Books> booksList=new ArrayList<>();
            booksList.add(books);
            booksList.add(books2);

            borrow.setBooks(booksList);
            borrow.setStudent(student);


        MockitoAnnotations.initMocks(this);
        mockMvc= MockMvcBuilders.standaloneSetup(paymentController).build();}

    @Test
    void processShouldProcessPaymentWhenBasketIsNotFullStudentHasRightToOrderAndCreateBorrowPresent() throws Exception {
        //given
        Payment payment = new Payment();
        List<Links> linksList = new ArrayList<>();
        Links links=new Links();
        links.setRel("approval_url");
        links.setHref("success");
        linksList.add(links);
        payment.setId("1");
        payment.setLinks(linksList);
        //when
        when(basketSession.isEmpty()).thenReturn(false);
        when(borrowService.createBorrow()).thenReturn(Optional.of(borrow));
        when(borrowService.logInStudentHasRightToOrder()).thenReturn(true);
        when(borrowService.saveBorrowDto(borrow)).thenReturn(borrow);

      //  when(paypalService.createPaymentHttpRes(borrow,paymentController.successPaypalUrl,paymentController.cancelPaypalUrl)).thenReturn(payment);

        //then

        MockHttpServletRequestBuilder updateDetails = get("/process/");
       mockMvc.perform(updateDetails)
               .andExpect(view().name("redirect:" + "success"))
                .andExpect(status().is3xxRedirection());

       verify(borrowService,times(2)).saveBorrowDto(borrowDtoArgumentCaptor.capture());

        System.out.println(borrowDtoArgumentCaptor.getValue().getPayPalPaymentId());


        assertEquals(borrowDtoArgumentCaptor.getValue().getPayPalPaymentId(),"1");


    }

    @Test

    void processShouldReturnMessageWhenBasketIsFull() throws Exception {
        //given
        //when
        when(basketSession.isEmpty()).thenReturn(true);

        //then
        MockHttpServletRequestBuilder updateDetails = get("/process/");
        mockMvc.perform(updateDetails)
                .andExpect(view().name("message"))
                .andExpect(model().attribute("messageTitle",is(not(emptyString()))))
                .andExpect(model().attribute("messageText",is(not(emptyString()))))
                .andExpect(model().attribute("link",is(not(emptyString()))))
                .andExpect(model().attribute("linkMes",is(not(emptyString()))));


    }
    @Test
    void processShouldReturnMessageWhenBasketIsFullAndUserDontHaveRightToOrder() throws Exception {
        //given
        //when
        when(basketSession.isEmpty()).thenReturn(true);
        when(borrowService.logInStudentHasRightToOrder()).thenReturn(false);

        //then
        MockHttpServletRequestBuilder updateDetails = get("/process/");
        mockMvc.perform(updateDetails)
                .andExpect(view().name("message"))
                .andExpect(model().attribute("messageTitle",is(not(emptyString()))))
                .andExpect(model().attribute("messageText",is(not(emptyString()))))
                .andExpect(model().attribute("link",is(not(emptyString()))))
                .andExpect(model().attribute("linkMes",is(not(emptyString()))));


    }




}