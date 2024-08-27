package com.medilabo.MediLabo_Solutions_Back_Note.service;

import com.medilabo.MediLabo_Solutions_Back_Note.model.Note;
import com.medilabo.MediLabo_Solutions_Back_Note.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    // Update an existing note
    public Note updateNote(Note updatedNote) {
        Optional<Note> existingNoteOptional = noteRepository.findById(updatedNote.getPatId());
        if (existingNoteOptional.isPresent()) {
            Note existingNote = existingNoteOptional.get();
            existingNote.setPatient(updatedNote.getPatient());
            existingNote.setNote(updatedNote.getNote());
            return noteRepository.save(existingNote);
        } else {
            throw new RuntimeException("Note not found with ID: " + updatedNote.getPatId());
        }
    }

    // Delete a note by ID
    public void deleteNoteById(String id) {
        noteRepository.deleteById(id);
    }

    // Delete a note by Note
    public void deleteNoteByContent(String noteContent) {
        noteRepository.deleteByNote(noteContent);
    }
}
