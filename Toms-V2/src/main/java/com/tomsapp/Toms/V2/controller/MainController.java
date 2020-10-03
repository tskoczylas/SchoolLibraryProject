package com.tomsapp.Toms.V2.controller;


import com.tomsapp.Toms.V2.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    StudentService studentService;

    public MainController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/")
    public String demoController(Model model){



        return "redirect:/book";
    }




}
