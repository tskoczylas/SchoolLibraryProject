package com.tomsapp.Toms.V2.controller;

import com.tomsapp.Toms.V2.dto.StudentAddressDto;
import com.tomsapp.Toms.V2.entity.Student;
import com.tomsapp.Toms.V2.entity.Token;
import com.tomsapp.Toms.V2.service.LoginService;
import com.tomsapp.Toms.V2.service.StudentServiceInt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sun.rmi.runtime.Log;

import java.util.Optional;

import static com.tomsapp.Toms.V2.mapper.StudentAddressMaper.mapToStudentAddressDtoFromToken;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.data.repository.util.ClassUtils.hasProperty;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class LoginControllerTest {

    @Mock
    StudentServiceInt studentServiceInt;

    @Mock
    LoginService loginService;


    @InjectMocks
    private LoginController loginController;

    private MockMvc mockMvc;
   final String sampleEmail="sample@sample.pl";
    final String secondSampleEmail ="notsample@sample.pl";

    @BeforeEach
    public void setup(){

        MockitoAnnotations.initMocks(this);
        mockMvc= MockMvcBuilders.standaloneSetup(loginController).build();}




    @Test
    void conformation_emailShuldReturnMessageWhenProvidedPasswordAreNotTheSame() throws Exception {

        //given
        StudentAddressDto studentAddressDto = new StudentAddressDto();

        //when
       studentAddressDto.setEmail(sampleEmail);
      studentAddressDto.setConfirmEmail(secondSampleEmail);

        //then

        MockHttpServletRequestBuilder updateDetails = post("/conformation_email")
                .flashAttr("emailUser", studentAddressDto);
        mockMvc.perform(updateDetails)
                .andExpect(status().isOk())
                .andExpect(view().name("login.html"))
                .andExpect(model().attribute("message","This email doesn't match"))
                .andExpect(model().attribute("emailUser",studentAddressDto));
    }

    @Test
    void conformation_emailShouldReturnMessageWhenProvidedNullEmail() throws Exception {
       //given
        StudentAddressDto studentAddressDto = new StudentAddressDto();
        //when
        studentAddressDto.setConfirmEmail(sampleEmail);
        //then
        MockHttpServletRequestBuilder updateDetails = post("/conformation_email")
                .flashAttr("emailUser", studentAddressDto);
        mockMvc.perform(updateDetails)
                .andExpect(status().isOk())
                .andExpect(view().name("login.html"))
                .andExpect(model().attribute("message","There is not email or confirm email"))
                .andExpect(model().attribute("emailUser",studentAddressDto));

    }

    @Test
    void conformation_emailShouldReturnMessageWhenProvidedNullConfirmEmail() throws Exception {
        //given
        StudentAddressDto studentAddressDto = new StudentAddressDto();
        //then
        studentAddressDto.setEmail(sampleEmail);
        //then
        MockHttpServletRequestBuilder updateDetails = post("/conformation_email")
                .flashAttr("emailUser", studentAddressDto);
        mockMvc.perform(updateDetails)
                .andExpect(status().isOk())
                .andExpect(view().name("login.html"))
                .andExpect(model().attribute("message","There is not email or confirm email"))
                .andExpect(model().attribute("emailUser",studentAddressDto));

    }
    @Test
    void conformation_emailShouldReturnMessageWhenStudentEmailExistInDataBase() throws Exception {
        //given
        StudentAddressDto studentAddressDto = new StudentAddressDto();
        Student student  = new Student();
        Optional<Student> optionalStudent = Optional.of(student);
        //when
        studentAddressDto.setEmail(sampleEmail);
        studentAddressDto.setConfirmEmail(sampleEmail);

       when(studentServiceInt.findStudentByEmail(studentAddressDto.getEmail())).thenReturn(optionalStudent);
        //then
        MockHttpServletRequestBuilder updateDetails = post("/conformation_email")
                .flashAttr("emailUser", studentAddressDto);
        mockMvc.perform(updateDetails)
                .andExpect(status().isOk())
                .andExpect(view().name("login.html"))
                .andExpect(model().attribute("message","Your email already exist"))
                .andExpect(model().attribute("emailUser",studentAddressDto));

    }

    @Test
    void conformation_emailShouldReturnMessageWhenStudentEmailNotExistInDataBaseAndExecuteCreateTokenSignStudentAndSendConfMail() throws Exception {
        //given
        StudentAddressDto studentAddressDto = new StudentAddressDto();
        //when
        studentAddressDto.setEmail(sampleEmail);
        studentAddressDto.setConfirmEmail(sampleEmail);

        when(studentServiceInt.findStudentByEmail(studentAddressDto.getEmail())).thenReturn(Optional.empty());
        //then

        MockHttpServletRequestBuilder updateDetails = post("/conformation_email")
                .flashAttr("emailUser", studentAddressDto);
        mockMvc.perform(updateDetails)
                .andExpect(status().isOk())
                .andExpect(view().name("login.html"))
                .andExpect(model().attribute("message","Email with registration link has been sent"))
                .andExpect(model().attribute("emailUser",studentAddressDto));
        verify(loginService).createTokenSignStudentAndSendConfMail(studentAddressDto);

    }
    @Test
    void create_accountProvideTokenStudentAndReturnRegWhenTokenAndStudentNotNullTokenIsActiveStudentIdIsNot0() throws Exception {
        //given
        String tokenText="abcnnsldlkdkfk";
        Student student = new Student();
            student.setId(22);
        Token token = new Token(student);

        //when
        when(loginService.findTokenByToken(tokenText)).thenReturn(token);
        StudentAddressDto studentAddressDto = mapToStudentAddressDtoFromToken(token);

        //then

        MockHttpServletRequestBuilder updateDetails = get("/create_account")
                .param("token",tokenText);
        mockMvc.perform(updateDetails)
                .andExpect(status().isOk())
                .andExpect(view().name("registration"))
                .andExpect(model().attribute("tokenStudent",studentAddressDto));

    }

    @Test
    void create_accountReturnErrorWhenTokenIsDoesntExistInDataBase() throws Exception {
        //given
        String tokenText="abcnnsldlkdkfk";
        Student student = new Student();
        student.setId(22);
        Token token = new Token(student);

        //when
        when(loginService.findTokenByToken(tokenText)).thenReturn(token);
        StudentAddressDto studentAddressDto = mapToStudentAddressDtoFromToken(token);

        //then

        MockHttpServletRequestBuilder updateDetails = get("/create_account")
                .param("token","token");
        mockMvc.perform(updateDetails)
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorTitle",is(not(emptyString()))))
                 .andExpect(model().attribute("errorText",is(not(emptyString()))));


    }
    @Test
    void create_accountReturnErrorWhenTokenIsNull() throws Exception {
        //given
        String tokenText="abcnnsldlkdkfk";
        //when
        when(loginService.findTokenByToken(tokenText)).thenReturn(null);

        //then
        MockHttpServletRequestBuilder updateDetails = get("/create_account")
                .param("token",tokenText);
        mockMvc.perform(updateDetails)
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorTitle",is(not(emptyString()))))
                .andExpect(model().attribute("errorText",is(not(emptyString()))));


    }
    @Test
    void create_accountReturnErrorWhenTokenStudentIsNull() throws Exception {
        //given
        Student student = null;
        String tokenText="abcnnsldlkdkfk";
        Token token = new Token();
        token.setStudent(student);
        token.setActive(true);
        //when
        when(loginService.findTokenByToken(tokenText)).thenReturn(token);
        //then
        MockHttpServletRequestBuilder updateDetails = get("/create_account")
                .param("token",tokenText);
        mockMvc.perform(updateDetails)
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorTitle",is(not(emptyString()))))
                .andExpect(model().attribute("errorText",is(not(emptyString()))));
    }
    @Test
    void create_accountReturnErrorWhenTokenIsNotActive() throws Exception {
        //given
        Student student = new Student();
        student.setId(22);
        String tokenText="abcnnsldlkdkfk";
        Token token = new Token();
        token.setStudent(student);
        token.setActive(false);
        //when
        when(loginService.findTokenByToken(tokenText)).thenReturn(token);
        //then
        MockHttpServletRequestBuilder updateDetails = get("/create_account")
                .param("token",tokenText);
        mockMvc.perform(updateDetails)
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorTitle",is(not(emptyString()))))
                .andExpect(model().attribute("errorText",is(not(emptyString()))));
    }
    @Test
    void create_accountReturnErrorWhenTokenStudentIdIs0() throws Exception {
        //given
        Student student = new Student();
        student.setId(0);
        String tokenText="abcnnsldlkdkfk";
        Token token = new Token();
        token.setStudent(student);
        token.setActive(true);
        //when
        when(loginService.findTokenByToken(tokenText)).thenReturn(token);
        //then
        MockHttpServletRequestBuilder updateDetails = get("/create_account")
                .param("token",tokenText);
        mockMvc.perform(updateDetails)
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorTitle",is(not(emptyString()))))
                .andExpect(model().attribute("errorText",is(not(emptyString()))));
    }


    @Test
    void create_accountSaveShouldReturnRegistrationWhenHasBidingError() throws Exception {
        //given
        StudentAddressDto studentAddressDto = new StudentAddressDto();
        studentAddressDto.setEmail("sample");
        //when
        MockHttpServletRequestBuilder updateDetails = post("/create_account/save")
                .flashAttr("tokenStudent",studentAddressDto);
        //then
        mockMvc.perform(updateDetails)
                .andExpect(status().isOk())
                .andExpect(view().name("registration"))
                .andExpect(model().attribute("tokenStudent",studentAddressDto));
    }

    @Test
    void create_accountSaveShouldReturnPasswordErrorWhenPasswordsNotMatch() throws Exception {
        //given
        StudentAddressDto studentAddressDto = new StudentAddressDto();
        studentAddressDto.setCountry("Poland");
        studentAddressDto.setFirstName("Sample");
        studentAddressDto.setLastName("Sample");
        studentAddressDto.setPassword("NotSample");
        studentAddressDto.setConfirmPassword("Sample");
        studentAddressDto.setAddressSecondLine("Sample");
        studentAddressDto.setAddressFirstLine("Sample");
        studentAddressDto.setPostCode("Sample");
        studentAddressDto.setTelephone("Sample");
        studentAddressDto.setEmail("Sample");

        //when
        MockHttpServletRequestBuilder updateDetails = post("/create_account/save")
                .flashAttr("tokenStudent",studentAddressDto);
        //then
        mockMvc.perform(updateDetails)
                .andExpect(status().isOk())
                .andExpect(view().name("registration"))
                .andExpect(model().attribute("passwordError",is(not(emptyString()))))
                .andExpect(model().attribute("tokenStudent",studentAddressDto));
    }

    @Test
    void create_accountSaveShouldReturnErrorWhenStudentAddressDtoEmailIsNull() throws Exception {
        //given
        StudentAddressDto studentAddressDto = new StudentAddressDto();
        studentAddressDto.setCountry("Poland");
        studentAddressDto.setFirstName("Sample");
        studentAddressDto.setLastName("Sample");
        studentAddressDto.setPassword("NotSample");
        studentAddressDto.setConfirmPassword("Sample");
        studentAddressDto.setAddressSecondLine("Sample");
        studentAddressDto.setAddressFirstLine("Sample");
        studentAddressDto.setPostCode("Sample");
        studentAddressDto.setTelephone("Sample");
        //when
        MockHttpServletRequestBuilder updateDetails = post("/create_account/save")
                .flashAttr("tokenStudent",studentAddressDto);
        //then
        mockMvc.perform(updateDetails)
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorTitle",is(not(emptyString()))))
                .andExpect(model().attribute("errorText",is(not(emptyString()))));

    }

    @Test
    void create_accountSaveWhenIsSuccessfulReturnMessageAndExecuteSaveRegistrationAndDeactivateToken() throws Exception {
        //given
        StudentAddressDto studentAddressDto = new StudentAddressDto();
        studentAddressDto.setCountry("Poland");
        studentAddressDto.setFirstName("Sample");
        studentAddressDto.setLastName("Sample");
        studentAddressDto.setPassword("Sample");
        studentAddressDto.setConfirmPassword("Sample");
        studentAddressDto.setAddressSecondLine("Sample");
        studentAddressDto.setAddressFirstLine("Sample");
        studentAddressDto.setPostCode("Sample");
        studentAddressDto.setTelephone("Sample");
        studentAddressDto.setEmail("Sample");
        //when
        MockHttpServletRequestBuilder updateDetails = post("/create_account/save")
                .flashAttr("tokenStudent",studentAddressDto);
        //then
        mockMvc.perform(updateDetails)
                .andExpect(status().isOk())
                .andExpect(view().name("message"))
                .andExpect(model().attribute("messageTitle",is(not(emptyString()))))
                .andExpect(model().attribute("messageText",is(not(emptyString()))));
        verify(loginService).saveRegistration(studentAddressDto);
        verify(loginService).deactivateTokenByStudentId(studentAddressDto.getId());

    }

    @Test
    void loginShouldProvideNewStudentAddressDto() throws Exception {
        //given

        //when
        MockHttpServletRequestBuilder updateDetails = get("/login.html");
        //then
        mockMvc.perform(updateDetails)
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attribute("emailUser",new StudentAddressDto()));

    }
    @Test
    void login_errorShouldThrowErrorAndProvideStudentAddressDto() throws Exception {
        //given

        //when
        MockHttpServletRequestBuilder updateDetails = get("/login-error");
        //then
        mockMvc.perform(updateDetails)
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attribute("emailUser",new StudentAddressDto()))
                        .andExpect(model().attribute("loginError",true));


    }
}