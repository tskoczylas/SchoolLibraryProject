package com.tomsapp.Toms.V2.controller;

import com.tomsapp.Toms.V2.entity.Books;
import com.tomsapp.Toms.V2.entity.Role;
import com.tomsapp.Toms.V2.entity.RoleEnum;
import com.tomsapp.Toms.V2.security.StudentUser;
import com.tomsapp.Toms.V2.entity.Students;
import com.tomsapp.Toms.V2.service.BooksService;
import com.tomsapp.Toms.V2.service.RoleService;
import com.tomsapp.Toms.V2.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("/students")
public class StudentController {

StudentService studentService;
BooksService booksService;
PasswordEncoder passwordEncoder;
RoleService roleService;

    @Autowired
    public StudentController(StudentService studentService,
                             BooksService booksService,
                             PasswordEncoder passwordEncoder, RoleService roleService) {
        this.studentService = studentService;
        this.booksService = booksService;
        this.passwordEncoder=passwordEncoder;
        this.roleService=roleService;

    }


    @PostConstruct
    void createStudent(){
/*
  Students students1 = new Students("Feliks",
                "Aleksi",
                "admin",
                "admin",
                passwordEncoder.encode("admin"),
                true);


   //     Students students1 = studentService.findbyId(55);
        Role role = roleService.getById(2);

        List<Role> roleList=Collections.singletonList(role);

        students1.setRolesSet(roleList);

        studentService.saveSrudent(students1);
*/



    }

    @GetMapping("/list")
    public String createList(Model model){

        model.addAttribute("list",studentService.getStudents());

        return "showStudentForm";
    }

    @GetMapping("/showaddform")
        public String showaddform(Model model) {

        Students students = new Students();
        model.addAttribute("stu",students);


        return "addStudentForm";
    }
    @PostMapping("/save")
          public String saveStudents(@Valid @ModelAttribute(value ="stu" ) Students tempStudents, BindingResult bindingResult,Model model
    ){

        if(bindingResult.hasErrors()) return "addStudentForm";
       // tempStudents.setRolesSet(Collections.singleton(new Role(RoleEnum.ROLE_USER)));
        tempStudents.setPassword(passwordEncoder.encode(tempStudents.getPassword()));

        studentService.saveSrudent(tempStudents);
        return "redirect:/students/list";
        }


@GetMapping("/updateform")
    public String updateStudent(@RequestParam("studentid") int studentID, Model model)

{
    Students students = studentService.findbyId(studentID);



    model.addAttribute("stu",students);

    return "addStudentForm";
}

@GetMapping("/deleteStudent")
    public String deleteStudent(@RequestParam("studentId") int studentId){
    System.out.println(studentId);

        studentService.deleteStudentbyId(studentId);

    return "redirect:/students/list";

}

@GetMapping("/listOfBoroowingStudents")
    public String listOfBoroowingStudents(@RequestParam("studentId") int id, Model model){
    Students students = studentService.findbyId(id);
    List<Books> books = booksService.getBooks();

        return "showBorrowingStudent";

}

@GetMapping("/findStudent")
    public String findStudent(Model model,@RequestParam("searchStudent") String shearchField)
    {
        List<Students> studentsByNameorSurname = studentService.findStudentsByNameorSurname(shearchField);

        model.addAttribute("list",studentsByNameorSurname);

        return "showStudentForm";
}









}
