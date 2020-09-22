package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.dto.StudentAddressDto;
import com.tomsapp.Toms.V2.entity.Adress;
import com.tomsapp.Toms.V2.entity.Student;
import com.tomsapp.Toms.V2.entity.Token;
import com.tomsapp.Toms.V2.enums.Role;
import com.tomsapp.Toms.V2.repository.StudentsRepository;
import com.tomsapp.Toms.V2.repository.TokenRepository;
import org.modelmapper.ModelMapper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Optional;

import static com.tomsapp.Toms.V2.mapper.StudentAddressMaper.mapToStudentFromStudentAddressDto;

@Service
public class LoginServiceImp implements LoginService {
    TokenRepository tokenRepository;
    StudentsRepository  studentsRepository;
    HttpServletRequest httpServletRequest;
    JavaMailSender javaMailSender;
    PasswordEncoder passwordEncoder;
    EmailService emailService;


    public LoginServiceImp(TokenRepository tokenRepository, StudentsRepository studentsRepository, HttpServletRequest httpServletRequest, JavaMailSender javaMailSender, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.tokenRepository = tokenRepository;
        this.studentsRepository = studentsRepository;
        this.httpServletRequest = httpServletRequest;
        this.javaMailSender = javaMailSender;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public void createTokenAndSignStudent(StudentAddressDto studentAddressDto)  {
        Student student =
                mapToStudentFromStudentAddressDto(studentAddressDto);
        studentsRepository.save(student);

        Token confirmationToken = new Token(student);
        tokenRepository.save(confirmationToken);

       emailService.sendConformationMessage(confirmationToken);

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
    @Override
    public Token findTokenByToken(String token){


        return tokenRepository.findTokenByTokenIgnoreCase(token);
    }

    @Override
    public void saveRegistration(StudentAddressDto studentAddressDto) {
        ModelMapper modelMapper=new ModelMapper();
        Student student = new Student();
        Adress adress = new Adress();
        modelMapper.map(studentAddressDto,student);
        modelMapper.map(studentAddressDto,adress);
        adress.setId(0);
        adress.setAdressStudent(student);
        student.setRole(Role.ROLE_USER);
        student.setAdresses(adress);
        student.setEnabled(true);
        student.setPassword(passwordEncoder.encode(studentAddressDto.getPassword()));

        studentsRepository.save(student);
        Token tokenByStudentId = tokenRepository.findTokenByStudentId(studentAddressDto.getId());
        tokenByStudentId.setActive(false);
        tokenRepository.save(tokenByStudentId);


    }
}
