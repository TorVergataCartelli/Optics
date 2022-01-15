package com.example.optic.entities;

public class Report {
    private int idReport;
    private String Motivazione;
    private String fk_UsernameR;
    private String fk_UsernameP;

    public int getIdReport() {
        return idReport;
    }

    public String getMotivazione() {
        return Motivazione;
    }

    public String getFk_UsernameP() {
        return fk_UsernameP;
    }

    public String getFk_UsernameR() {
        return fk_UsernameR;
    }

    public void setIdReport(int idReport) {
        this.idReport = idReport;
    }

    public void setMotivazione(String motivazione) {
        Motivazione = motivazione;
    }

    public void setFk_UsernameR(String fk_UsernameR) {
        this.fk_UsernameR = fk_UsernameR;
    }

    public void setFk_UsernameP(String fk_UsernameP) {
        this.fk_UsernameP = fk_UsernameP;
    }

}
