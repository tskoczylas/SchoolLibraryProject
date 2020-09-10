package com.tomsapp.Toms.V2.controller;

import com.tomsapp.Toms.V2.entity.Adress;
import com.tomsapp.Toms.V2.entity.Student;
import com.tomsapp.Toms.V2.service.AdressServiceInt;
import com.tomsapp.Toms.V2.service.StudentServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/adress")
public class AdressController {


    AdressServiceInt adressServiceInt;
    StudentServiceInt studentServiceInt;

    @Autowired
    public AdressController(AdressServiceInt adressServiceInt, StudentServiceInt studentServiceInt) {
        this.adressServiceInt = adressServiceInt;
        this.studentServiceInt = studentServiceInt;
    }

    @GetMapping("/showAdress")
    public String showAdressForm(Model model,
                                 @RequestParam("studentId") int studentId){

       Student student = studentServiceInt
               .findbyId(studentId);
       List<Adress> adresses = adressServiceInt.
               findAdressByStudentId(studentId);

       model.addAttribute("adress",adresses);
       model.addAttribute("viewStudent", student);

  return "showAdressForm";
   }

   @GetMapping("/add")
    public String addAdress(@RequestParam("studentId") int studentId,Model model){

       model.addAttribute("addAdress",new Adress());

       Student student =
               studentServiceInt.
                       findbyId(studentId);

       model.addAttribute("viewStudent", student);
       return "addAdressForm";
   }

   @PostMapping("/save")
    public String saveAdress(@Valid  @ModelAttribute("addAdress") Adress adress,
                             BindingResult bidingresult,
                             @RequestParam("studentId") int studentID){

       if(bidingresult.hasErrors())
       {return  "redirect:/adress/add?studentId="+studentID;}

       adressServiceInt.save(adress,studentID);

       return "redirect:/adress/showAdress?studentId="+studentID;
   }
   @GetMapping("/delete")
   public String deleteAdress(@RequestParam("adressId") int adressId){

       Student studentById =
               adressServiceInt.
                       deleteAndFindStudent(adressId);


       return "redirect:/adress/showAdress?studentId="+studentById.getId();
   }



}
