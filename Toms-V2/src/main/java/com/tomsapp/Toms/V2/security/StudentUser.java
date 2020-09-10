package com.tomsapp.Toms.V2.security;

import com.tomsapp.Toms.V2.entity.Student;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class StudentUser implements UserDetails {

    private Student student;

    public StudentUser(Student student) {
        this.student = student;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<SimpleGrantedAuthority> authorities =
                student.
                        getRolesSet().
                        stream().
                        map(s -> new SimpleGrantedAuthority(s.getRole().name())).
                        collect(Collectors.toList());
        return authorities;



    }

    public Student getStudent() {
        return student;
    }



    @Override
    public String getPassword() {
        return student.getPassword();
    }

    @Override
    public String getUsername() {
        return student.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return student.isEnabled();
    }
}