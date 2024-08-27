package com.medilabo.MediLabo_Solutions.model;

public class Note {

    private String patId;

    private String patient;

    private String note;
    public Note() {}

    public Note(String patId, String patient, String note) {
        this.patId = patId;
        this.patient = patient;
        this.note = note;
    }

    public String getPatId() {
        return patId;
    }

    public void setPatId(String patId) {
        this.patId = patId;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
