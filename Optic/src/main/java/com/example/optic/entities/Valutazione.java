package com.example.optic.entities;

public class Valutazione {
    private int idValutazione;
    private String descrizione;
    private int stelle;
    private String fk_UsernameP1;
    private String fk_UsernameP2;
    private String fk_UsernameA;

    //costruttore per recensione da utente a utente
    public Valutazione(String descrizione, String recensore, String recensito, int stelle){
        this.descrizione = descrizione;
        this.fk_UsernameP1 = recensore;
        this.fk_UsernameP2 = recensito;
        this.stelle = stelle;
    }

    public int getIdValutazione() {
        return idValutazione;
    }

    public void setIdValutazione(int idValutazione) {
        this.idValutazione = idValutazione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        descrizione = descrizione;
    }

    public int getStelle() {
        return stelle;
    }

    public void setStelle(int stelle) {
        stelle = stelle;
    }

    public String getFk_UsernameP1() {
        return fk_UsernameP1;
    }

    public void setFk_UsernameP1(String fk_UsernameP1) {
        this.fk_UsernameP1 = fk_UsernameP1;
    }

    public String getFk_UsernameP2() {
        return fk_UsernameP2;
    }

    public void setFk_UsernameP2(String fk_UsernameP2) {
        this.fk_UsernameP2 = fk_UsernameP2;
    }

    public String getFk_UsernameA() {
        return fk_UsernameA;
    }

    public void setFk_UsernameA(String fk_UsernameA) {
        this.fk_UsernameA = fk_UsernameA;
    }

    public String getFormattedText(){
        String desc = this.fk_UsernameP1+" :\n"+descrizione;
        return desc;
    }
}
