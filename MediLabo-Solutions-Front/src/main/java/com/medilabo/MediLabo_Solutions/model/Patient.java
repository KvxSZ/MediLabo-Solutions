package com.medilabo.MediLabo_Solutions.model;


import java.util.Date;


public class Patient {

    private int id;



    private String prenom;


    private String nom;


    private Date dateDeNaissance;

    private String genre;


    private String adressePostale;


    private String telephone;

    public Patient() {
    }

    public int getPatientId() {
        return id;
    }

    public void setPatientId(int patientId) {
        this.id = patientId;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(Date dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAdressePostale() {
        return adressePostale;
    }

    public void setAdressePostale(String adressePostale) {
        this.adressePostale = adressePostale;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }







}
