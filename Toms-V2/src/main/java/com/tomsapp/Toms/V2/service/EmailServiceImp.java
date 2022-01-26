package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.dto.BorrowDto;
import com.tomsapp.Toms.V2.entity.Borrow;
import com.tomsapp.Toms.V2.entity.Token;
import com.tomsapp.Toms.V2.mapper.BorrowMapper;
import com.tomsapp.Toms.V2.session.BasketSession;
import com.tomsapp.Toms.V2.utils.HostUtils;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.tomsapp.Toms.V2.utils.HostUtils.getHost;

@Service
public class EmailServiceImp implements EmailService {

    private JavaMailSender javaMailSender;
    private TemplateEngine templateEngine;
   private BasketSession basketSession;

    public EmailServiceImp(JavaMailSender javaMailSender, TemplateEngine templateEngine, BasketSession basketSession) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.basketSession = basketSession;
    }




    @Override
    public void sendConformationMessageLogin(Token confirmationToken){

    SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(confirmationToken.getStudent().getEmail());
        mailMessage.setSubject("Thank you for  registration!");

        mailMessage.setText("To create account, please click here : "+ getHost()
                + "/create_account?token="+confirmationToken.getToken());

        javaMailSender.send(mailMessage);

}



    public String build(BorrowDto borrowDto) {
        Context context = new Context();
        context.setVariable("borrow", borrowDto);
        return templateEngine.process("order_conformation", context);
    }


    @Override
    public void sendConformationMessageNewOrder(BorrowDto borrow) {


        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo( borrow.getStudent().getEmail());
            messageHelper.setSubject("Order number: " + borrow.getId() + " change status to " + borrow.getBorrowStatusEnum());
            String content = build(borrow);
            messageHelper.setText(content, true);
        };
        try {
            javaMailSender.send(messagePreparator);
        } catch (MailException e) {
            System.out.println("error ");
        }
    }





}
