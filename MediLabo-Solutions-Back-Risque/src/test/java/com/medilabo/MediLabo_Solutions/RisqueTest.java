package com.medilabo.MediLabo_Solutions;

import com.medilabo.MediLabo_Solutions.controllers.RisqueController;
import com.medilabo.MediLabo_Solutions.model.Note;
import com.medilabo.MediLabo_Solutions.model.Patient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class RisqueTest {


    @Mock
    private Model model;

    @InjectMocks
    private RisqueController risqueController;


    @Test
    public void testCalculateAge() {
        RisqueController risqueController = new RisqueController();

        LocalDate localDate = LocalDate.now().minusYears(30);
        Date birthDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        int expectedAge = 30;
        int actualAge = risqueController.calculateAge(birthDate);

        assertEquals(expectedAge, actualAge);
    }

    @Test
    public void testCalculateAgeFutureDate() {
        RisqueController risqueController = new RisqueController();

        LocalDate localDate = LocalDate.now().plusYears(1);
        Date futureBirthDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        try {
            risqueController.calculateAge(futureBirthDate);
        } catch (IllegalArgumentException e) {
            assertEquals("La date de naissance ne peut pas être dans le futur.", e.getMessage());
        }
    }

    @Test
    public void testCalculateAgeNullDate() {
        RisqueController risqueController = new RisqueController();

        try {
            risqueController.calculateAge(null);
        } catch (IllegalArgumentException e) {
            assertEquals("La date de naissance ne peut pas être nulle.", e.getMessage());
        }
    }

    // Méthode utilitaire pour créer une date de naissance à partir de l'âge
    private Date createDateFromAge(int age) {
        LocalDate localDate = LocalDate.now().minusYears(age);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    // Méthode utilitaire pour créer une note
    private Note createNoteWithText(String text) {
        Note note = new Note();
        note.setNote(text);
        return note;
    }

    @Test
    public void testEvaluationNoneNoTriggers() {
        RisqueController risqueController = new RisqueController();

        // Patient de 40 ans avec aucun déclencheur
        Patient patient = new Patient();
        patient.setDateDeNaissance(createDateFromAge(40));
        patient.setGenre("M");

        List<Note> notes = List.of();
        String riskLevel = risqueController.evaluation(notes, patient);

        assertEquals("None", riskLevel);
    }

    @Test
    public void testEvaluationBorderline() {
        RisqueController risqueController = new RisqueController();

        // Patient de 40 ans avec 3 déclencheurs (2 à 5 déclencheurs, âge > 30)
        Patient patient = new Patient();
        patient.setDateDeNaissance(createDateFromAge(40));
        patient.setGenre("M");

        List<Note> notes = List.of(
                createNoteWithText("Cholestérol"),
                createNoteWithText("Fumeur"),
                createNoteWithText("Hémoglobine A1C")
        );
        String riskLevel = risqueController.evaluation(notes, patient);

        assertEquals("Borderline", riskLevel);
    }

    @Test
    public void testEvaluationInDangerMaleUnder30() {
        RisqueController risqueController = new RisqueController();

        // Patient homme de 25 ans avec 4 déclencheurs (3 à 5 pour les hommes ≤ 30 ans)
        Patient patient = new Patient();
        patient.setDateDeNaissance(createDateFromAge(25));
        patient.setGenre("M");

        List<Note> notes = List.of(
                createNoteWithText("Cholestérol Vertiges Fumeur")
        );
        String riskLevel = risqueController.evaluation(notes, patient);

        assertEquals("In Danger", riskLevel);
    }

    @Test
    public void testEvaluationInDangerFemaleUnder30() {
        RisqueController risqueController = new RisqueController();

        // Patiente femme de 28 ans avec 5 déclencheurs (4 à 5 pour les femmes ≤ 30 ans)
        Patient patient = new Patient();
        patient.setDateDeNaissance(createDateFromAge(28));
        patient.setGenre("F");

        List<Note> notes = List.of(
                createNoteWithText("Cholestérol"),
                createNoteWithText("Fumeuse"),
                createNoteWithText("Hémoglobine A1C"),
                createNoteWithText("Microalbumine"),
                createNoteWithText("Vertiges")
        );
        String riskLevel = risqueController.evaluation(notes, patient);

        assertEquals("In Danger", riskLevel);
    }

    @Test
    public void testEvaluationInDangerMaleOver30() {
        RisqueController risqueController = new RisqueController();

        // Patient homme de 45 ans avec 6 déclencheurs (6 ou 7 déclencheurs pour les > 30 ans)
        Patient patient = new Patient();
        patient.setDateDeNaissance(createDateFromAge(45));
        patient.setGenre("M");

        List<Note> notes = List.of(
                createNoteWithText("Cholestérol"),
                createNoteWithText("Fumeur"),
                createNoteWithText("Hémoglobine A1C"),
                createNoteWithText("Vertiges"),
                createNoteWithText("Réaction"),
                createNoteWithText("Anticorps")
        );
        String riskLevel = risqueController.evaluation(notes, patient);

        assertEquals("In Danger", riskLevel);
    }

    @Test
    public void testEvaluationEarlyOnsetMaleUnder30() {
        RisqueController risqueController = new RisqueController();

        // Patient homme de 29 ans avec 6 déclencheurs (5 à 7 déclencheurs pour les hommes ≤ 30 ans)
        Patient patient = new Patient();
        patient.setDateDeNaissance(createDateFromAge(29));
        patient.setGenre("M");

        List<Note> notes = List.of(
                createNoteWithText("Cholestérol"),
                createNoteWithText("Fumeur"),
                createNoteWithText("Hémoglobine A1C"),
                createNoteWithText("Vertiges"),
                createNoteWithText("Réaction"),
                createNoteWithText("Anticorps")
        );
        String riskLevel = risqueController.evaluation(notes, patient);

        assertEquals("Early onset", riskLevel);
    }

    @Test
    public void testEvaluationEarlyOnsetFemaleUnder30() {
        RisqueController risqueController = new RisqueController();

        // Patiente femme de 27 ans avec 7 déclencheurs (7 déclencheurs pour les femmes ≤ 30 ans)
        Patient patient = new Patient();
        patient.setDateDeNaissance(createDateFromAge(27));
        patient.setGenre("F");

        List<Note> notes = List.of(
                createNoteWithText("Cholestérol"),
                createNoteWithText("Fumeuse"),
                createNoteWithText("Hémoglobine A1C"),
                createNoteWithText("Vertiges"),
                createNoteWithText("Réaction"),
                createNoteWithText("Anticorps"),
                createNoteWithText("Microalbumine")
        );
        String riskLevel = risqueController.evaluation(notes, patient);

        assertEquals("Early onset", riskLevel);
    }

    @Test
    public void testEvaluationEarlyOnsetMaleOver30() {
        RisqueController risqueController = new RisqueController();

        // Patient homme de 40 ans avec 8 déclencheurs (≥ 8 déclencheurs pour les > 30 ans)
        Patient patient = new Patient();
        patient.setDateDeNaissance(createDateFromAge(40));
        patient.setGenre("M");

        List<Note> notes = List.of(
                createNoteWithText("Cholestérol"),
                createNoteWithText("Fumeur"),
                createNoteWithText("Hémoglobine A1C"),
                createNoteWithText("Vertiges"),
                createNoteWithText("Réaction"),
                createNoteWithText("Anticorps"),
                createNoteWithText("Poids"),
                createNoteWithText("Taille")
        );
        String riskLevel = risqueController.evaluation(notes, patient);

        assertEquals("Early onset", riskLevel);
    }
}



