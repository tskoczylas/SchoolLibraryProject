package com.tomsapp.Toms.V2.setup;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomsapp.Toms.V2.entity.*;
import com.tomsapp.Toms.V2.enums.Role;
import com.tomsapp.Toms.V2.mapper.BookJsonToBookMaper;
import com.tomsapp.Toms.V2.service.BooksServiceImp;
import com.tomsapp.Toms.V2.service.StudentServiceInt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SetupService {
    private StudentServiceInt studentServiceInt;
    private BooksServiceImp booksServiceImp;
    private BookJsonToBookMaper bookJsonToBookMaper;

    public SetupService(StudentServiceInt studentServiceInt, BooksServiceImp booksServiceImp, BookJsonToBookMaper bookJsonToBookMaper) {
        this.studentServiceInt = studentServiceInt;

        this.booksServiceImp = booksServiceImp;
        this.bookJsonToBookMaper = bookJsonToBookMaper;
    }

    @Value("${value.admin.login}")
    private String adminLogin;

    @Value("${value.admin.password}")
    private String adminPassword;



    @PostConstruct
    @JsonIgnore
    @JsonProperty(value = "publishedDate")
    public void createBooks() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<BookDto> bookDto =
                objectMapper.readValue(new File("Toms-V2/listOfExampleBooks.json"),
                        objectMapper.getTypeFactory().
                                constructCollectionType(List.class, BookDto.class));

        List<Books> booksList = bookDto.
                stream().
                map(bookJsonToBookMaper::mapToBooks).collect(Collectors.toList());


        booksServiceImp.saveBooksList(booksList);
    }

    public void CreateAdmin() {
        BCryptPasswordEncoder  bCryptPasswordEncoder =new BCryptPasswordEncoder();

        Student adminStudent = new Student();
        adminStudent.setEnabled(true);
        adminStudent.setEmail("admin");
        adminStudent.setFirstName("AdminTomasz");
        adminStudent.setLastName("AdminSkoczylas");
        adminStudent.setPassword(bCryptPasswordEncoder.encode("admin"));
        adminStudent.setRole(Role.ROLE_ADMIN);

        studentServiceInt.saveStudent(adminStudent);


    }
    @PostConstruct
    public void CreateUser() {
        BCryptPasswordEncoder  bCryptPasswordEncoder =new BCryptPasswordEncoder();

       Student userStudent=new Student();
        Adress adress = new Adress();
        adress.setPostCode("34-333");
        adress.setAddressFirstLine("Kopernika");
        adress.setAddressSecondLine("Eastbourne");
        adress.setTelephone("788888");
        adress.setCountry("Uk");
        adress.setAdressStudent(userStudent);
        userStudent.setAdresses(adress);
       userStudent.setId(1);
        userStudent.setEnabled(true);
        userStudent.setEmail("t.skoczylas1@gmail.com");
        userStudent.setFirstName("Adam");
       userStudent.setLastName("Walny");
        userStudent.setPassword(bCryptPasswordEncoder.encode("aaa"));

        userStudent.setRole(Role.ROLE_USER);

        studentServiceInt.saveStudent(userStudent);


    }
}


