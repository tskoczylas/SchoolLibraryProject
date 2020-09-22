package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.entity.Token;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.net.NetworkInterface;
import java.net.SocketException;

@Service
public class EmailServiceImp implements EmailService {

    JavaMailSender javaMailSender;

    public EmailServiceImp(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendConformationMessage(Token confirmationToken){

    SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(confirmationToken.getStudent().getEmail());
        mailMessage.setSubject("Thank you for  registration!");

        mailMessage.setText("To create account, please click here : "+ getHost()
                + "/create_account?token="+confirmationToken.getToken());

        javaMailSender.send(mailMessage);

}

    @Override
    public   String getHost(){
        String hostAddress =
                null;
        try {
            hostAddress = NetworkInterface.
                    getNetworkInterfaces().
                    nextElement().getInetAddresses().
                    nextElement().getHostAddress();
        } catch (SocketException e) {
            hostAddress="unknown";
        }

        return hostAddress;
    }
}
