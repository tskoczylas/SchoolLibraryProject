package com.tomsapp.Toms.V2.security;

import com.tomsapp.Toms.V2.service.StudentServiceInt;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserDetailsService {

   StudentServiceInt studentServiceInt;

    public UserServiceImp(StudentServiceInt studentServiceInt) {
        this.studentServiceInt = studentServiceInt;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return new StudentUser(studentServiceInt.findStudentByEmailorUsername(s));
    }
}
