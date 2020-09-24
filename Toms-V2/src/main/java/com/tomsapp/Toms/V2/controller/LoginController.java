package com.tomsapp.Toms.V2.controller;

import com.tomsapp.Toms.V2.dto.StudentAddressDto;
import com.tomsapp.Toms.V2.entity.Student;
import com.tomsapp.Toms.V2.entity.Token;
import com.tomsapp.Toms.V2.service.LoginService;
import com.tomsapp.Toms.V2.service.StudentServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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


    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("emailUser", new StudentAddressDto());

        return "login";
    }


    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        model.addAttribute("emailUser", new StudentAddressDto());


        return "login";
    }



    @PostMapping("/conformation_email")
    public String sendConformationEmail(@ModelAttribute("emailUser") StudentAddressDto studentAddressDto, Model model)  {

        Optional<Student> studentByEmail =
                studentServiceInt.
                        findStudentByEmail(studentAddressDto.getEmail());

        if(studentAddressDto.getEmail()==null||studentAddressDto.getConfirmEmail()==null)
        {model.addAttribute("message","There is not email or confirm email");}
        else if(!studentAddressDto.isEmailMatch()){
            model.addAttribute("message","This email doesn't match"); }
        else if( studentByEmail.isPresent() ) {
           model.addAttribute("message", "Your email already exist"); }
       else
       { loginService.createTokenSignStudentAndSendConfMail(studentAddressDto);

        model.addAttribute("message", "Email with registration link has been sent");}

       model.addAttribute("emailUser",studentAddressDto);

        return "login.html";
    }

    @GetMapping("/create_account")
    public String createAccount(Model model,
            @RequestParam(name = "token",required = false) String token){

        Token tokenByToken = loginService.findTokenByToken(token);

       if( tokenByToken!=null&&
               tokenByToken.getStudent()!=null&&
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

        if(studentAddressDto==null||studentAddressDto.getEmail()==null)
        {model.addAttribute("errorTitle","Error");
            model.addAttribute("errorText","The link has been used or is invalid or broken!");
            return "error";}
        else if (bindingResult.hasErrors()) {
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
            loginService.deactivateTokenByStudentId(studentAddressDto.getId());
        return "message";}
    }

}
