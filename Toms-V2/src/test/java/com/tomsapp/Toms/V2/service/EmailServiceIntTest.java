package com.tomsapp.Toms.V2.service;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
@Disabled
public class EmailServiceIntTest {

    private GreenMail smtpServer;

    @BeforeEach
    public void setUp() throws Exception {
        smtpServer = new GreenMail(new ServerSetup(25, null, "smtp"));
        smtpServer.start();
    }

    @AfterEach
    public void tearDown() throws Exception {
        smtpServer.stop();
    }


}
