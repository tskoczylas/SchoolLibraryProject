package com.tomsapp.Toms.V2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.jws.soap.SOAPBinding;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SeciurityConfig extends WebSecurityConfigurerAdapter {

 UserServiceImp userServiceImp;

 @Autowired
 public SeciurityConfig(UserServiceImp userServiceImp){
     this.userServiceImp = userServiceImp;
 }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userServiceImp);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
http.authorizeRequests().
        antMatchers("/conformation_email").permitAll().
        antMatchers("/css/**","/1/**","/fonts/**","/images/**","/js/**","/create_account/**").permitAll().
        anyRequest().authenticated().

        and().
                formLogin().
                loginPage("/login.html").permitAll().
                failureUrl("/login-error.html").permitAll().

        and().
                logout().permitAll();

    }

@Bean
   public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
}

}

