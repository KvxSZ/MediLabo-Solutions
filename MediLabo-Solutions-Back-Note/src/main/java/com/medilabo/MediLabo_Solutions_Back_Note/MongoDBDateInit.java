package com.medilabo.MediLabo_Solutions_Back_Note;


import com.medilabo.MediLabo_Solutions_Back_Note.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MongoDBDateInit {

    @Autowired
    private MongoTemplate mongoTemplate;


    public void InitMongoDB(){
        mongoTemplate.remove(new Query(), "notes");

        List<Note> noteList = new ArrayList<>();

        Note note1 = new Note("1", "TestNone", "Le patient déclare qu'il 'se sent très bien' Poids égal ou inférieur au poids recommandé");
        noteList.add(note1);

        Note note2 = new Note("2", "TestBorderline", "Le patient déclare qu'il ressent beaucoup de stress au travail Il se plaint également que son audition est anormale dernièrement");
        noteList.add(note2);
        Note note3 = new Note("2", "TestBorderline", "Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois Il remarque également que son audition continue d'être anormale");
        noteList.add(note3);

        Note note4 = new Note("3", "TestInDanger", "Le patient déclare qu'il fume depuis peu");
        noteList.add(note4);
        Note note5 = new Note("3", "TestInDanger", "Le patient déclare qu'il est fumeur et qu'il a cessé de fumer l'année dernière Il se plaint également de crises d’apnée respiratoire anormales Tests de laboratoire indiquant un taux de cholestérol LDL élevé");
        noteList.add(note5);

        Note note6 = new Note("4", "TestEarlyOnset", "Le patient déclare qu'il lui est devenu difficile de monter les escaliers Il se plaint également d’être essoufflé Tests de laboratoire indiquant que les anticorps sont élevés Réaction aux médicaments");
        noteList.add(note6);
        Note note7 = new Note("4", "TestEarlyOnset", "Le patient déclare qu'il a mal au dos lorsqu'il reste assis pendant longtemps");
        noteList.add(note7);
        Note note8 = new Note("4", "TestEarlyOnset", "Le patient déclare avoir commencé à fumer depuis peu Hémoglobine A1C supérieure au niveau recommandé");
        noteList.add(note8);
        Note note9 = new Note("4", "TestEarlyOnset", "Taille, Poids, Cholestérol, Vertige et Réaction");
        noteList.add(note9);

        mongoTemplate.insert(noteList, "notes");
    }



}
