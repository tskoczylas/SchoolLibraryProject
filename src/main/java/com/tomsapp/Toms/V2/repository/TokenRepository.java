package com.tomsapp.Toms.V2.repository;

import com.tomsapp.Toms.V2.entity.Student;
import com.tomsapp.Toms.V2.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Long> {

    Token findTokenByTokenIgnoreCase(String token);

    Token findTokenByStudentId(int studentId);
}
