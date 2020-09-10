package com.tomsapp.Toms.V2.controller;

import com.tomsapp.Toms.V2.entity.Books;
import com.tomsapp.Toms.V2.entity.Role;
import com.tomsapp.Toms.V2.entity.RoleEnum;
import com.tomsapp.Toms.V2.entity.Student;
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
import java.util.*;


@Controller
@RequestMapping("/student")
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
  Student students1 = new Student("Feliks",
                "Aleksi",
                "admin",
                "admin",
                passwordEncoder.encode("admin"),
                true);


   //     Student students1 = studentService.findbyId(55);
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

        Student student = new Student();
        model.addAttribute("stu", student);


        return "addStudentForm";
    }
    @PostMapping("/save")
          public String saveStudents(@Valid @ModelAttribute(value ="stu" ) Student tempStudent, BindingResult bindingResult, Model model
    ){

        if(bindingResult.hasErrors()) return "addStudentForm";

        Role byId = roleService.getById(2);
        tempStudent.setPassword(passwordEncoder.encode(tempStudent.getPassword()));
        tempStudent.setEnabled(true);
        tempStudent.setRolesSet(Collections.singletonList(byId));


        studentService.saveSrudent(tempStudent);
        System.out.println(tempStudent);
        return "redirect:/students/list";
        }


@GetMapping("/updateform")
    public String updateStudent(@RequestParam("studentid") int studentID, Model model)

{
    Student student = studentService.findbyId(studentID);



    model.addAttribute("stu", student);

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
    Student student = studentService.findbyId(id);
    List<Books> books = booksService.getBooks();

        return "showBorrowingStudent";

}

@GetMapping("/findStudent")
    public String findStudent(Model model,@RequestParam("searchStudent") String shearchField)
    {
        List<Student> studentByNameorSurname = studentService.findStudentsByNameorSurname(shearchField);

        model.addAttribute("list", studentByNameorSurname);

        return "showStudentForm";
}









}
