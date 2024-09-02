package com.medilabo.MediLabo_Solutions_Back_Note.repository;

import com.medilabo.MediLabo_Solutions_Back_Note.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {

    List<Note> findByPatId(String patId);

}
