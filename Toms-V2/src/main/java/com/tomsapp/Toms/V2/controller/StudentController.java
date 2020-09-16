package com.tomsapp.Toms.V2.controller;

import com.tomsapp.Toms.V2.dto.AddressDto;
import com.tomsapp.Toms.V2.dto.StudentDto;
import com.tomsapp.Toms.V2.entity.Books;
import com.tomsapp.Toms.V2.entity.Role;
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

        StudentDto createStudentDto = new StudentDto();
        AddressDto createAddressDto = new AddressDto();

        model.addAttribute("createStudent", createStudentDto);
        model.addAttribute("createAddress", createAddressDto);

        return "addStudentForm";
    }
    @PostMapping("/save")
          public String saveStudents( @ModelAttribute(value ="createStudent" ) StudentDto studentDto,

                                    @ModelAttribute("createAddressDto") AddressDto addressDto,

                                     Model model
    ){
      //  System.out.println(studentbindingResult);
       // System.out.println(addressbindingResult);

        System.out.println(addressDto);
        System.out.println(studentDto);

     //   if(studentbindingResult.hasErrors()) return "addStudentForm";
//

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
