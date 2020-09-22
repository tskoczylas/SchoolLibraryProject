package com.tomsapp.Toms.V2.setup;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomsapp.Toms.V2.entity.*;
import com.tomsapp.Toms.V2.enums.Role;
import com.tomsapp.Toms.V2.mapper.BookJsonToBookMaper;
import com.tomsapp.Toms.V2.service.BooksService;
import com.tomsapp.Toms.V2.service.StudentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SetupService {
    private StudentService studentService;
    private PasswordEncoder passwordEncoder;
    private BooksService booksService;
    private BookJsonToBookMaper bookJsonToBookMaper;

    public SetupService(StudentService studentService, PasswordEncoder passwordEncoder,  BooksService booksService, BookJsonToBookMaper bookJsonToBookMaper) {
        this.studentService = studentService;
        this.passwordEncoder = passwordEncoder;
        this.booksService = booksService;
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


        booksService.saveBooksList(booksList);
    }
    @PostConstruct
    public void CreateAdmin() {

        Student adminStudent = new Student();
        adminStudent.setEnabled(true);
        adminStudent.setEmail("admin");
        adminStudent.setFirstName("AdminTomasz");
        adminStudent.setLastName("AdminSkoczylas");
        adminStudent.setPassword(passwordEncoder.encode("admin"));
        adminStudent.setRole(Role.ROLE_ADMIN);

        studentService.saveSrudent(adminStudent);


    }
    @PostConstruct
    public void CreateUser() {

       Student userStudent=new Student();
        userStudent.setEnabled(true);
        userStudent.setEmail("email");
        userStudent.setFirstName("UserTomasz");
        userStudent.setLastName("UserSkoczylas");
        userStudent.setPassword(passwordEncoder.encode("user"));
        userStudent.setRole(Role.ROLE_USER);

        studentService.saveSrudent(userStudent);


    }
}


