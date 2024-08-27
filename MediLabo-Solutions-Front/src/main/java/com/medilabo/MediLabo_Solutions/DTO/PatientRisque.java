package com.medilabo.MediLabo_Solutions.DTO;

import java.util.Date;

public record PatientRisque(String risque, int patientId,String nom, String prenom, Date dateDeNaissance, String genre, String adressePostale, String telephone) {
}
