package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.dto.BorrowDto;
import com.tomsapp.Toms.V2.entity.Borrow;
import com.tomsapp.Toms.V2.entity.Token;
import com.tomsapp.Toms.V2.mapper.BorrowMapper;
import com.tomsapp.Toms.V2.session.BasketSession;
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

    @Override
    public void sendConformationMessageNewOrder4(Borrow borrow){

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(borrow.getStudent().getEmail());
        mailMessage.setSubject( "Borrow number " + borrow.getId() +  "change status to " + borrow.getBorrowStatusEnum() );


        String text =
                "Dear " + borrow.getStudent().getFirstName() + " " + borrow.getStudent().getLastName()+ "\n" + "\n"
                + "Your borrow number " + borrow.getId() + " changed status to " + borrow.getBorrowStatusEnum() + "." + "\n" +"\n"
                + "You borrowed books: " + "\n"
                + borrow.getBooks() +"\n" + "\n"
                + "Period of borrows: " + borrow.getBorrowPeriodEnum().getDays() + " ." +"\n"
                + "Cost per day: " + borrow.getBorrowPeriodEnum().getTotalCost() + " ." + "\n"
                + "Total cost: " + borrow.getBorrowPeriodEnum().getTotalCost() + " .";

        mailMessage.setText(text);

        javaMailSender.send(mailMessage);}

    public String build(BorrowDto borrowDto) {
        Context context = new Context();
        context.setVariable("borrow", borrowDto);
        return templateEngine.process("order_conformation", context);
    }


    @Override
    public void sendConformationMessageNewOrder(Borrow borrow) {
        BorrowDto borrowDtoMap = BorrowMapper.mapToBorrowDtoFromBorrow(borrow);


        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo( borrowDtoMap.getStudent().getEmail());
            messageHelper.setSubject("Sample mail subject");
            String content = build(borrowDtoMap);
            messageHelper.setText(content, true);
        };
        try {
            javaMailSender.send(messagePreparator);
        } catch (MailException e) {
            System.out.println("error ");
        }
    }




    @Override
    public   String getHost(){
        String hostAddress;
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
