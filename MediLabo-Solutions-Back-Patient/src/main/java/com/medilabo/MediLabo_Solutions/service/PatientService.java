package com.medilabo.MediLabo_Solutions.service;

import com.medilabo.MediLabo_Solutions.model.Patient;
import com.medilabo.MediLabo_Solutions.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    // Get all users
    public Iterable<Patient> getPatients(){
        return patientRepository.findAll();
    }

    // Get all users in Page
    public Page<Patient> getPatientPage(Integer page){ return patientRepository.findAll(PageRequest.of(page, 5));}

    // Get patient by ID
    public Optional<Patient> getPatientById(Integer id){
        return patientRepository.findById(id);
    }

    // Add a patient
    public Patient addPatient(Patient patient){
        return patientRepository.save(patient);
    }

    // Update a patient
    @Transactional
    public void updatePatient(Integer id, Patient patientDetails) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        if (patientDetails.getPrenom() == null || patientDetails.getPrenom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le prénom ne peut pas être vide.");
        }
        if (patientDetails.getNom() == null || patientDetails.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom ne peut pas être vide.");
        }

        if (patientDetails.getDateDeNaissance() == null) {
            throw new IllegalArgumentException("La date de naissance ne peut pas être vide.");
        }

        if (patientDetails.getGenre() == null || patientDetails.getGenre().trim().isEmpty()) {
            throw new IllegalArgumentException("Le genre ne peut pas être vide.");
        }


        patient.setPrenom(patientDetails.getPrenom());
        patient.setNom(patientDetails.getNom());
        patient.setGenre(patientDetails.getGenre());
        patient.setDateDeNaissance(patientDetails.getDateDeNaissance());

        patient.setTelephone(patientDetails.getTelephone());
        patient.setAdressePostale(patientDetails.getAdressePostale());

        patientRepository.save(patient);
    }

    // Delete a patient by ID
    @Transactional
    public void deleteUser(Integer id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        patientRepository.delete(patient);
    }



}
