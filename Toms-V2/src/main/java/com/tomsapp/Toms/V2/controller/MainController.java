package com.tomsapp.Toms.V2.controller;


import com.tomsapp.Toms.V2.entity.Borrowing;

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





    @GetMapping("/")
    public String demoController(Model model){

        model.addAttribute("time",new java.util.Date());

        Map<Period,Borrowing>  periods = new HashMap<>();
        List<Map.Entry<Period, Borrowing>> collect =
                periods.entrySet().stream().filter(s -> s.getKey().isNegative()).collect(Collectors.toList());


        model.addAttribute("return",collect);
        return "start";
    }




}
