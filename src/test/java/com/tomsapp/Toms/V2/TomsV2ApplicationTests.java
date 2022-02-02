package com.tomsapp.Toms.V2;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TomsV2ApplicationTests {
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
