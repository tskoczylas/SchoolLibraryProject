package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.dto.StudentAddressDto;
import com.tomsapp.Toms.V2.entity.Adress;
import com.tomsapp.Toms.V2.entity.Student;
import com.tomsapp.Toms.V2.entity.Token;

import com.tomsapp.Toms.V2.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import static com.tomsapp.Toms.V2.mapper.StudentAddressMaper.mapToAddressFromStudentAddressDto;
import static com.tomsapp.Toms.V2.mapper.StudentAddressMaper.mapToStudentFromStudentAddressDto;

@Service
public class LoginServiceImp implements LoginService {

    private TokenRepository tokenRepository;
    private EmailService emailService;
    private StudentService studentService;

    @Autowired
    public LoginServiceImp(TokenRepository tokenRepository, EmailService emailService, StudentService studentService) {
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
        this.studentService = studentService;
    }




    @Override
    public void createTokenSignStudentAndSendConfMail(StudentAddressDto studentAddressDto)  {
        Student student =
                mapToStudentFromStudentAddressDto(studentAddressDto);

        studentService.saveStudent(student);

          Token confirmationToken = new Token(student);
         tokenRepository.save(confirmationToken);
        emailService.sendConformationMessageLogin(confirmationToken);

    }




    @Override
    public Token findTokenByToken(String token){


        return tokenRepository.findTokenByTokenIgnoreCase(token);
    }

    @Override
    public void saveRegistration(StudentAddressDto studentAddressDto) {

        Adress adress =mapToAddressFromStudentAddressDto(studentAddressDto);
       Student student= mapToStudentFromStudentAddressDto(studentAddressDto);

        adress.setId(0);

        studentService.saveStudentUserActivateAndAssignAddress(student,adress);


    }

    @Override
    public void deactivateTokenByStudentId(int id){
        Token tokenByStudentId = tokenRepository.findTokenByStudentId(id);
        tokenByStudentId.setActive(false);
        tokenRepository.save(tokenByStudentId);

    }
}
