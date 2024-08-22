package com.medilabo.MediLabo_Solutions.controllers;

import com.medilabo.MediLabo_Solutions.model.Patient;
import com.medilabo.MediLabo_Solutions.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("/list")
    public Iterable<Patient> listePatients(@RequestParam(defaultValue = "0") Integer page){
        Iterable<Patient> patients = patientService.getPatients();
        return patients;
    }



    @PostMapping("/add")
    public void validate(@Valid @RequestBody Patient patient) {
        patientService.addPatient(patient);
    }

    @GetMapping("/update/{id}")
    public Patient showUpdateForm(@PathVariable("id") Integer id){
        Patient patient = patientService.getPatientById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalide user ID: " + id));
        return patient;
    }

    @PutMapping("/update/{id}")
    public void updatePatient(@PathVariable("id") Integer id, @Valid @RequestBody Patient patient) {
        patientService.updatePatient(id, patient);
    }

    @DeleteMapping("delete/{id}")
    public void deleteUser(@PathVariable("id") Integer id, Model model) {
        patientService.deleteUser(id);
    }
}
