package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.entity.Student;
import com.tomsapp.Toms.V2.entity.Token;
import com.tomsapp.Toms.V2.session.BasketSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;

import java.net.NetworkInterface;
import java.net.SocketException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
@RunWith(PowerMockRunner.class)
@PrepareForTest(NetworkInterface.class)

class EmailServiceImpTest {

    private JavaMailSender javaMailSender;
    private TemplateEngine templateEngine;
        private BasketSession basketSession;
    private EmailServiceImp emailServiceImp;
    @BeforeEach
    void createMocks(){
        javaMailSender=mock(JavaMailSender.class);
        emailServiceImp = new EmailServiceImp(javaMailSender,templateEngine,basketSession);
    }


    @Test
    void sendConformationMessageShouldContainTokenHaveSubjectAndContainsEmailMessageFromStudent() {
        //given
        String sampleMessage = "sample";
        String[] sampleMessageArray={sampleMessage};
        Student student = new Student();
        Token token= new Token(student);
        student.setEmail(sampleMessage);
        token.setToken(sampleMessage);
        emailServiceImp.sendConformationMessageLogin(token);

        //when
        ArgumentCaptor<SimpleMailMessage> argumentCaptor =ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(javaMailSender).send(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue().getTo(),is(sampleMessageArray));
        assertThat(argumentCaptor.getValue().getText(),containsString("/create_account?token=" + token.getToken()));
        assertThat(argumentCaptor.getValue().getSubject(),not(emptyString()));
    }

    @Test
    @Disabled
    void getHost() throws SocketException {
        //given
        PowerMockito.mockStatic(NetworkInterface.class);
        Mockito.when(NetworkInterface.getNetworkInterfaces().nextElement().getInetAddresses().nextElement().getHostAddress()).thenReturn("k");
//        emailServiceImp.getHost();
        //when
        //then
   //     System.out.println(emailServiceImp.getHost());
    }
}