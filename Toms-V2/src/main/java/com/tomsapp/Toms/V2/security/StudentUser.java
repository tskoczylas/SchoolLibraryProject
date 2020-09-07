package com.tomsapp.Toms.V2.security;

import com.tomsapp.Toms.V2.entity.Students;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StudentUser implements UserDetails {

    private Students students;

    public StudentUser(Students students) {
        this.students = students;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<SimpleGrantedAuthority> authorities =
                students.
                        getRolesSet().
                        stream().
                        map(s -> new SimpleGrantedAuthority(s.getRole().name())).
                        collect(Collectors.toList());
        return authorities;



    }

    public Students getStudents() {
        return students;
    }



    @Override
    public String getPassword() {
        return students.getPassword();
    }

    @Override
    public String getUsername() {
        return students.getEmail();
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
        return students.isEnabled();
    }
}