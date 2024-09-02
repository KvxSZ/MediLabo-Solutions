package com.medilabo.MediLabo_Solutions.model;

import jakarta.persistence.*;

import java.util.Date;

public class Patient {

    private Date dateDeNaissance;

    private String genre;


    public Patient() {
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








}
