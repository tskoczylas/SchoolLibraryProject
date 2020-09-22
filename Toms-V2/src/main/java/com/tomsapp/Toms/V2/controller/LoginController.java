package com.tomsapp.Toms.V2.controller;

import com.tomsapp.Toms.V2.dto.StudentAddressDto;
import com.tomsapp.Toms.V2.entity.Student;
import com.tomsapp.Toms.V2.entity.Token;
import com.tomsapp.Toms.V2.mapper.StudentAddressMaper;
import com.tomsapp.Toms.V2.service.LoginService;
import com.tomsapp.Toms.V2.service.StudentService;
import com.tomsapp.Toms.V2.service.StudentServiceInt;
import com.tomsapp.Toms.V2.session.BorrowCart;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.SocketException;
import java.util.Optional;

import static com.tomsapp.Toms.V2.mapper.StudentAddressMaper.mapToStudentAddressDtoFromToken;

@Controller
public class LoginController {

    LoginService loginService;
    StudentServiceInt studentServiceInt;

    @Autowired
    public LoginController(LoginService loginService, StudentServiceInt studentServiceInt) {
        this.loginService = loginService;
        this.studentServiceInt = studentServiceInt;
    }


    @GetMapping("login.html")
    public String login(Model model) {
        model.addAttribute("emailUser", new StudentAddressDto());

        return "login.html";
    }


    @RequestMapping("/login-error.html")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        model.addAttribute("emailUser", new StudentAddressDto());


        return "login.html";
    }



    @PostMapping("/conformation_email")
    public String sendConformationEmail(@ModelAttribute() StudentAddressDto studentAddressDto, Model model)  {

        Optional<Student> studentByEmail =
                studentServiceInt.
                        findStudentByEmail(studentAddressDto.getEmail());
        if(!studentAddressDto.isEmailMatch()){
            model.addAttribute("message","This email doesn't match");
        }
        else if( studentByEmail.isPresent() ) {
           model.addAttribute("message", "Your email already exist"); }
       else
       { loginService.createTokenAndSignStudent(studentAddressDto);
        model.addAttribute("message", "Email with registration link has been sent");}

       model.addAttribute("emailUser",studentAddressDto);

        return "login.html";
    }

    @GetMapping("/create_account")
    public String createAccount(Model model,
            @RequestParam(name = "token",required = false) String token){

        Token tokenByToken = loginService.findTokenByToken(token);

       if( tokenByToken!=null&&
               tokenByToken.isActive()&&
               tokenByToken.getStudent().getId()!=0)
       {
           model.addAttribute("tokenStudent",  mapToStudentAddressDtoFromToken(tokenByToken));
           return "registration";
       }
       else{
           model.addAttribute("errorTitle","Error");
           model.addAttribute("errorText","The link has been used or is invalid or broken!");
           return "error";
       }
    }

    @PostMapping("/create_account/save")
    public String saveAccount(@Valid @ModelAttribute(name = "tokenStudent") StudentAddressDto studentAddressDto, BindingResult bindingResult,
                              Model model){


        if(bindingResult.hasErrors()) {
            model.addAttribute("tokenStudent", studentAddressDto);
            return "registration";
        }
        else if(!studentAddressDto.isPasswordMatch()){
            model.addAttribute("passwordError","This password doesn't match");
            model.addAttribute("tokenStudent", studentAddressDto);
            return "registration";
        }
        else{
            model.addAttribute("messageTitle","Successful Registration");
            model.addAttribute("messageText", studentAddressDto.getFirstName() +
                    " " + studentAddressDto.getLastName() +
                    " welcome to our library, now you can log in" );
            loginService.saveRegistration(studentAddressDto);
        return "message";}
    }

}
