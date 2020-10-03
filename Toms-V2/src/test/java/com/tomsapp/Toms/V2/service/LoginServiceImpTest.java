package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.dto.StudentAddressDto;
import com.tomsapp.Toms.V2.entity.Adress;
import com.tomsapp.Toms.V2.entity.Student;
import com.tomsapp.Toms.V2.entity.Token;
import com.tomsapp.Toms.V2.repository.TokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginServiceImpTest {



 private    TokenRepository tokenRepository;
   private EmailService emailService;
  private   LoginServiceImp loginServiceImp;
private StudentService studentService;

    @BeforeEach
    void createMocks(){
         tokenRepository = mock(TokenRepository.class);
        emailService=mock(EmailService.class);
         studentService=mock(StudentServiceInt.class);

        loginServiceImp =
             new LoginServiceImp(tokenRepository,emailService,studentService);
    }

    @Test
    void createTokenSignStudentAndSendConfMail() {
        //given
        StudentAddressDto studentAddressDtoTemp = new StudentAddressDto();
        studentAddressDtoTemp.setEmail("sample@gmail.com");
        studentAddressDtoTemp.setStudentId(2);

        //when
        loginServiceImp.createTokenSignStudentAndSendConfMail(studentAddressDtoTemp);
        ArgumentCaptor<Token> argumentCaptorToken=ArgumentCaptor.forClass(Token.class);
        ArgumentCaptor<Student> argumentCaptorStudent=ArgumentCaptor.forClass(Student.class);

        verify(studentService).saveStudent(argumentCaptorStudent.capture());
        verify(tokenRepository).save((argumentCaptorToken.capture()));


        //then
        assertEquals(argumentCaptorToken.getValue().getStudent(),argumentCaptorStudent.getValue());
        assertThat(argumentCaptorStudent.getValue().getEmail(),is(equalTo(studentAddressDtoTemp.getEmail())));
        assertThat(argumentCaptorToken.getValue().getStudent().getEmail(),is(equalTo(studentAddressDtoTemp.getEmail())));
        assertThat(argumentCaptorStudent.getValue().getId(),is(equalTo(studentAddressDtoTemp.getStudentId())));
        assertThat(argumentCaptorToken.getValue().getStudent().getId(),is(equalTo(studentAddressDtoTemp.getStudentId())));
        assertThat(argumentCaptorToken.getValue().isActive(),is(equalTo(true)));
        assertThat(argumentCaptorToken.getValue().getToken(),is(not(emptyString())));


        verify(emailService).sendConformationMessageLogin(argumentCaptorToken.getValue());




    }

    @Test
    void findTokenByToken() {
        //given
        String sampleToken="sample";
        Token token = new Token();
        token.setToken(sampleToken);
        //when
        loginServiceImp.findTokenByToken(sampleToken);
        //then
        verify(tokenRepository).findTokenByTokenIgnoreCase(sampleToken);


    }


    @Test
    void saveRegistrationShouldMapAdressDtoDtoToAddressAndStudentAndSetAdressIdTo0AndSendTosaveStudentUserActivateAndAssignAddress() {
       //given
        StudentAddressDto studentAddressDto = new StudentAddressDto();
        studentAddressDto.setStudentId(22);
        studentAddressDto.setEmail("email");
        studentAddressDto.setAddressFirstLine("address");
        loginServiceImp.saveRegistration(studentAddressDto);
        //when
        ArgumentCaptor<Adress> argumentCaptorAddress=ArgumentCaptor.forClass(Adress.class);
        ArgumentCaptor<Student> argumentCaptorStudent=ArgumentCaptor.forClass(Student.class);

        verify(studentService).saveStudentUserActivateAndAssignAddress(argumentCaptorStudent.capture(),argumentCaptorAddress.capture());
        //then
        assertThat(argumentCaptorAddress.getValue().getId(),is(0));
        assertThat(argumentCaptorAddress.getValue().getAddressFirstLine(),is("address"));
        assertThat(argumentCaptorStudent.getValue().getEmail(),is("email"));



    }

    @Test
    void deactivateTokenByStudentId() {
        //given
        int studentId=22;
        Student student = new Student();
        student.setId(studentId);
        Token token = new Token(student);
        //when
        ArgumentCaptor<Token> argumentCaptorToken=ArgumentCaptor.forClass(Token.class);
        when(tokenRepository.findTokenByStudentId(studentId)).thenReturn(token);
        loginServiceImp.deactivateTokenByStudentId(studentId);
        //then
       verify(tokenRepository).save(argumentCaptorToken.capture());
        //when
        assertFalse(argumentCaptorToken.getValue().isActive());
        assertEquals(argumentCaptorToken.getValue().getStudent(),student);

    }
}