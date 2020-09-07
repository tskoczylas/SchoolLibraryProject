package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.dto.BorrowingDto;
import com.tomsapp.Toms.V2.entity.Borrowing;
import com.tomsapp.Toms.V2.entity.Students;
import com.tomsapp.Toms.V2.security.StudentUser;
import com.tomsapp.Toms.V2.mapper.BorrowingMapper;
import com.tomsapp.Toms.V2.repository.BorrowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BorrowingService implements BorrowingServiceInt{


    @Autowired
    BorrowingRepository borrowingRepository;

    @Autowired
    StudentService  studentService;

    List<Students> findUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Set<SimpleGrantedAuthority> userRole = Collections.singleton(new SimpleGrantedAuthority("ROlE_USER"));
        Set<SimpleGrantedAuthority> adminRole = Collections.singleton(new SimpleGrantedAuthority("ROlE_ADMIN"));
        boolean equals = authentication.getAuthorities().equals(userRole);
        boolean equals2 = authentication.getAuthorities().equals(adminRole);
        System.out.println(equals);
        System.out.println(equals2);


        if(authentication.isAuthenticated()&&authentication.getAuthorities().equals(userRole)){
        return Collections.singletonList((Students) authentication.getPrincipal());}
        else if(authentication.isAuthenticated()&&authentication.getAuthorities().equals(adminRole)){
            return studentService.getStudents(); }
        else return Collections.emptyList();
    }

    @Override
    public List<BorrowingDto> borrowingList() {
        return  borrowingRepository.
                findAll().
                stream().
              map(BorrowingMapper::borrowingDto).
              collect(Collectors.toList());




    }

    @Override
    public void save(Borrowing borrowing) {
        borrowingRepository.save(borrowing);
    }

    @Override
    public void delete(int borowId) {
        borrowingRepository.deleteById(borowId);
    }

    @Override
    public Borrowing getByID(int borowId) {
        return borrowingRepository.findById(borowId).get();
    }
}
