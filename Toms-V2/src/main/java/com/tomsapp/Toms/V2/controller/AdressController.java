package com.tomsapp.Toms.V2.controller;

import com.tomsapp.Toms.V2.entity.Adress;
import com.tomsapp.Toms.V2.entity.Students;
import com.tomsapp.Toms.V2.service.AdressServiceInt;
import com.tomsapp.Toms.V2.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/adress")
public class AdressController {

    @Autowired
    AdressServiceInt adressServiceInt;
    @Autowired
    StudentService studentService;

   @GetMapping("/showAdress")
    public String showAdressForm(Model model,@RequestParam("studentId") int studentId){
       Students students = studentService.findbyId(studentId);
       List<Adress> adresses = adressServiceInt.findAdressByStudentId(studentId);
       model.addAttribute("adress",adresses);
       model.addAttribute("viewStudent",students);

  return "showAdressForm";
   }

   @GetMapping("/add")
    public String addAdress(@RequestParam("studentId") int studentId,Model model){
       model.addAttribute("addAdress",new Adress());
       Students students = studentService.findbyId(studentId);
       model.addAttribute("viewStudent",students);
       return "addAdressForm";
   }

   @PostMapping("/save")
    public String saveAdress(@Valid  @ModelAttribute("addAdress") Adress adress,BindingResult bidingresult, @RequestParam("studentId") int studentID){

       System.out.println(bidingresult.hasFieldErrors());

       if(bidingresult.hasErrors()) {return  "redirect:/adress/add?studentId="+studentID;}

       Students students = studentService.findbyId(studentID);
       adress.setAdressStudents(students);
       adressServiceInt.save(adress);

       return "redirect:/adress/showAdress?studentId="+studentID;
   }
   @GetMapping("/delete")
   public String deleteAdress(@RequestParam("adressId") int adressID){

       Adress adress = adressServiceInt.getById(adressID);
       int studentId = adress.getAdressStudents().getId();
       adressServiceInt.delete(adressID);


       return "redirect:/adress/showAdress?studentId="+studentId;
   }



}
