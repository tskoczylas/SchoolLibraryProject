package com.tomsapp.Toms.V2.security;

import com.tomsapp.Toms.V2.service.StudentService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserDetailsService {

   StudentService studentService;

    public UserServiceImp(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return new StudentUser(studentService.findStudentByEmailorUsername(s));
    }
}
