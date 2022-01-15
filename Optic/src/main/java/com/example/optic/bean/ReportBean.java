package com.example.optic.bean;

public class ReportBean {
    String motivazione;
    String referee;
    String player;

    public String getMotivazione() {
        return motivazione;
    }

    public void ReportBean(){}

    public void setMotivazione(String motivazione) {
        this.motivazione = motivazione;
    }

    public String getReferee() {
        return referee;
    }

    public void setReferee(String referee) {
        this.referee = referee;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getFormattedText(){
        return "Ricevuto da "+referee+"\nMotivazione: "+motivazione;
    }
}
