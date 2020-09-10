package com.tomsapp.Toms.V2.service;

import com.tomsapp.Toms.V2.entity.Adress;
import com.tomsapp.Toms.V2.entity.Role;
import com.tomsapp.Toms.V2.entity.RoleEnum;
import com.tomsapp.Toms.V2.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

@Controller
public class SetupService {
      private   StudentService studentService;
    private PasswordEncoder passwordEncoder;
   private RoleService roleService;
    @Autowired
    public SetupService(StudentService studentService, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.studentService = studentService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Value("${value.admin.login}")
    private String adminLogin;

    @Value("${value.admin.password}")
    private String adminPassword;

    @PostConstruct
    public void createRoles(){
        Role roleUser = new Role(RoleEnum.ROLE_USER);
        Role roleAdmin = new Role(RoleEnum.ROLE_ADMIN);
        roleService.saveRole(roleAdmin);
        roleService.saveRole(roleUser);

    }


    @PostConstruct
    public void CreateAdmin() {

        List<Role> roleEnums = Collections.singletonList(new Role(2,RoleEnum.ROLE_ADMIN));
        Student adminStudent=new Student(
                "Admin",
                "Admin",
                this.adminLogin,
                this.adminLogin,
                passwordEncoder.encode(this.adminPassword),
                true,
                roleEnums );

        studentService.saveSrudent(adminStudent);



    }

}
