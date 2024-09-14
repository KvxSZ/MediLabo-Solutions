package com.medilabo.MediLabo_Solutions.controllers;


import com.medilabo.MediLabo_Solutions.model.Note;
import com.medilabo.MediLabo_Solutions.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/risque")
public class RisqueController {


    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/evaluation/{id}")
    public String evaluationDesRisques(@PathVariable("id") Integer id){
        ResponseEntity<List<Note>> notes = restTemplate.exchange(
                 "http://back-note:8083/note/list/{id}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Note>>() {},
                id
        );
        List<Note> listeNotes = notes.getBody();
        ResponseEntity<Patient> response = restTemplate.getForEntity("http://back-patient:8081/patient/{id}", Patient.class, id);
        Patient patient = response.getBody();


        return evaluation(listeNotes, patient);
    }



    public String evaluation(List<Note> listNotes, Patient patient){
        int age = calculateAge(patient.getDateDeNaissance());
        int nbrDeclencheurs = declencheurs(listNotes);

        if (nbrDeclencheurs == 0) {
            return "None";
        }

        if (nbrDeclencheurs >= 2 && nbrDeclencheurs <= 5 && age > 30) {
            return "Borderline";
        }

        if (age <= 30) {
            if (patient.getGenre().equalsIgnoreCase("M") && nbrDeclencheurs >= 3 && nbrDeclencheurs < 5) {
                return "In Danger";
            }
            if (patient.getGenre().equalsIgnoreCase("F") && nbrDeclencheurs >= 4 && nbrDeclencheurs < 6) {
                return "In Danger";
            }
        } else {
            if (nbrDeclencheurs == 6 || nbrDeclencheurs == 7) {
                return "In Danger";
            }
        }

        if (age <= 30) {
            if (patient.getGenre().equalsIgnoreCase("M") && nbrDeclencheurs >= 5 && nbrDeclencheurs < 8) {
                return "Early onset";
            }
            if (patient.getGenre().equalsIgnoreCase("F") && nbrDeclencheurs == 7) {
                return "Early onset";
            }
        } else {
            if (nbrDeclencheurs >= 8) {
                return "Early onset";
            }
        }


        return "None";
    }

    public int declencheurs(List<Note> listNotes) {
        List<String> listDelencheurs = Arrays.asList(
                "Hémoglobine A1C",
                "Microalbumine",
                "Taille",
                "Poids",
                "Fumeur",
                "Fumeuse",
                "Anormal",
                "Cholestérol",
                "Vertiges",
                "Rechute",
                "Réaction",
                "Anticorps"
        );

        int count = 0;

        for (Note note : listNotes) {
            String texteNote = note.getNote().toLowerCase();
            for (String declencheur : listDelencheurs) {
                if (texteNote.contains(declencheur.toLowerCase())) {
                    count++;
                }
            }
        }

        return count;
    }


    public int calculateAge(Date birthDate) {
        if (birthDate != null) {
            LocalDate birthDateLocal = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate currentDate = LocalDate.now();

            if (!birthDateLocal.isAfter(currentDate)) {
                return Period.between(birthDateLocal, currentDate).getYears();
            } else {
                throw new IllegalArgumentException("La date de naissance ne peut pas être dans le futur.");
            }
        } else {
            throw new IllegalArgumentException("La date de naissance ne peut pas être nulle.");
        }
    }



}



