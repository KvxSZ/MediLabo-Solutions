package com.medilabo.MediLabo_Solutions_Back_Note.service;

import com.medilabo.MediLabo_Solutions_Back_Note.model.Note;
import com.medilabo.MediLabo_Solutions_Back_Note.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    // Get all notes
    public List<Note> getNotes() {
        return noteRepository.findAll();
    }

    // Get notes by patient ID
    public List<Note> getPatientNotes(String patId) {
        return noteRepository.findByPatId(patId);
    }

    // Add a new note
    public Note addNote(Note note) {
        return noteRepository.save(note);
    }

}


