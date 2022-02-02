package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.dto.StudentAddressDto;
import com.tomsapp.Toms.V2.entity.Student;
import com.tomsapp.Toms.V2.entity.Token;
import com.tomsapp.Toms.V2.repository.TokenRepository;

import java.net.SocketException;
import java.util.Optional;

public interface LoginService {

    void createTokenSignStudentAndSendConfMail(StudentAddressDto studentAddressDto);

    Token findTokenByToken(String token);

    void saveRegistration(StudentAddressDto studentAddressDto);

    void deactivateTokenByStudentId(int id);
}
