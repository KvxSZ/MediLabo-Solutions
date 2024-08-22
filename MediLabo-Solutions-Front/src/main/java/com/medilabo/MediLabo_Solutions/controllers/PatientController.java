package com.medilabo.MediLabo_Solutions.controllers;


import com.medilabo.MediLabo_Solutions.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private RestTemplate restTemplate;

    private String gateawayUrl = "http://localhost:8082/";

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("/list")
    public String listePatients(Model model, @RequestParam(defaultValue = "0") Integer page){
        ResponseEntity<Patient[]> response = restTemplate.getForEntity(gateawayUrl+"patient/list", Patient[].class);
        model.addAttribute("patients", response.getBody());
        return "patient/list";
    }

    @GetMapping("/add")
    public String addPatient(Model model) {
        model.addAttribute("patient", new Patient());
        return "patient/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid Patient patient, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "patient/add";
        }
        restTemplate.postForEntity(gateawayUrl+"patient/add", patient, Void.class);
        return "redirect:/patient/list";

    }
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        ResponseEntity<Patient> response = restTemplate.getForEntity(gateawayUrl+"patient/update/{id}", Patient.class, id);
        Patient patient = response.getBody();
        model.addAttribute("patient", patient);
        return "patient/update";
    }

    @PostMapping("/update/{id}")
    public String updatePatient(@PathVariable("id") Integer id, @Valid Patient patient, BindingResult result) {
        if (result.hasErrors()) {
            return "patient/update";
        }
        restTemplate.put(gateawayUrl+"patient/update/{id}", patient, id);
        return "redirect:/patient/list";
    }

    @GetMapping("delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        restTemplate.delete(gateawayUrl+"patient/delete/{id}", id);
        return "redirect:/patient/list";
    }


}
