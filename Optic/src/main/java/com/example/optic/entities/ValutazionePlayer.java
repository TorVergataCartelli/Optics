package com.example.optic.entities;
public class ValutazionePlayer {
    private String user;
    private String descrizione;

    public ValutazionePlayer(String user, String descrizione){
        this.user = user;
        this.descrizione = user+" :\n"+descrizione;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
