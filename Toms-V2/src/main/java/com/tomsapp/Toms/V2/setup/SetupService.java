package com.tomsapp.Toms.V2.setup;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomsapp.Toms.V2.entity.*;
import com.tomsapp.Toms.V2.mapper.BookJsonToBookMaper;
import com.tomsapp.Toms.V2.service.BooksService;
import com.tomsapp.Toms.V2.service.RoleService;
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
    private RoleService roleService;
    private BooksService booksService;
    private BookJsonToBookMaper bookJsonToBookMaper;

    public SetupService(StudentService studentService, PasswordEncoder passwordEncoder, RoleService roleService, BooksService booksService, BookJsonToBookMaper bookJsonToBookMaper) {
        this.studentService = studentService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.booksService = booksService;
        this.bookJsonToBookMaper = bookJsonToBookMaper;
    }

    @Value("${value.admin.login}")
    private String adminLogin;

    @Value("${value.admin.password}")
    private String adminPassword;


    @PostConstruct
    public void createRoles() {
        Role roleUser = new Role(RoleEnum.ROLE_USER);
        Role roleAdmin = new Role(RoleEnum.ROLE_ADMIN);
        roleService.saveRole(roleAdmin);
        roleService.saveRole(roleUser);

    }
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

    public void CreateAdmin() {


        List<Role> roleEnums = Collections.singletonList(new Role(1, RoleEnum.ROLE_ADMIN));
        Student adminStudent = new Student(
                "Admin",
                "Admin",
                this.adminLogin,
                this.adminLogin,
                passwordEncoder.encode(this.adminPassword),
                true,
                roleEnums);

        studentService.saveSrudent(adminStudent);


    }


    public void CreateUser() {


        List<Role> roleEnums = Collections.singletonList(new Role(2, RoleEnum.ROLE_USER));
        Student adminStudent = new Student(
                "User",
                "User",
                "User",
                "User",
                passwordEncoder.encode("user"),
                true,
                roleEnums);

        studentService.saveSrudent(adminStudent);


    }
}


