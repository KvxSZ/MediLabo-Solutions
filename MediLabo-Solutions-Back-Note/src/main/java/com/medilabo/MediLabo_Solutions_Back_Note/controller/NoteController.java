package com.medilabo.MediLabo_Solutions_Back_Note.controller;


import com.medilabo.MediLabo_Solutions_Back_Note.model.Note;
import com.medilabo.MediLabo_Solutions_Back_Note.service.NoteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteService noteService;


    @GetMapping("/list")
    public List<Note> listeNotes(){
        return noteService.getNotes();
    }

    @GetMapping("/list/{id}")
    public List<Note> listeNotesForPatient(@PathVariable("id") String id){
        return noteService.getPatientNotes(id);
    }

    @PostMapping("/add")
    public void validate(@Valid @RequestBody Note note){
        noteService.addNote(note);
    }



}
