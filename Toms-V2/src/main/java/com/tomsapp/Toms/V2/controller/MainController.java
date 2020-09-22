package com.tomsapp.Toms.V2.controller;


import com.tomsapp.Toms.V2.entity.Borrowing;

import com.tomsapp.Toms.V2.entity.Student;
import com.tomsapp.Toms.V2.service.StudentServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Period;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class MainController {

    StudentServiceInt studentServiceInt;

    public MainController(StudentServiceInt studentServiceInt) {
        this.studentServiceInt = studentServiceInt;
    }

    @GetMapping("/")
    public String demoController(Model model){



        return "redirect:/books/showbooks";
    }




}
