package com.medilabo.MediLabo_Solutions.controllers;


import com.medilabo.MediLabo_Solutions.model.Note;
import com.medilabo.MediLabo_Solutions.model.Patient;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Controller
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private RestTemplate restTemplate;

    private String gateawayUrl = "http://localhost:8082/";

    @GetMapping("/list")
    public String listeNotes(Model model){
        ResponseEntity<Note[]> notes = restTemplate.getForEntity(gateawayUrl+"note/list", Note[].class);
        model.addAttribute("notes", notes.getBody());

        return "note/list";
    }

    @GetMapping("/list/{id}")
    public String listeNotesForPatient(@PathVariable("id") String id, Model model){
        ResponseEntity<List<Note>> response = restTemplate.exchange(
                gateawayUrl + "note/list/{id}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Note>>() {},
                id
        );

        List<Note> listeNotes = response.getBody();
        model.addAttribute("notes", listeNotes);

        return "note/list";
    }

    @GetMapping("/add")
    public String addNote(Model model) {
        ResponseEntity<Patient[]> patients = restTemplate.getForEntity(gateawayUrl+"patient/list", Patient[].class);
        model.addAttribute("patients", patients.getBody());
        model.addAttribute("note", new Note());
        return "note/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid Note note, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "note/add";
        }
        restTemplate.postForEntity(gateawayUrl+"note/add", note, Void.class);
        return "redirect:/note/list";

    }




}
