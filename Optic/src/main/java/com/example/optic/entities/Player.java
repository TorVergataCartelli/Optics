package com.example.optic.entities;

public class Player {
    private String username;
    private String password;
    private String descrizione;
    private int valutazione = 0;
    private String stelle = "";
    private String ig;
    private String fb;
    private String stato = "nullo";

    public Player(){}

    public Player(String username){
        this.username = username;
    }

    public Player(String username, String password){
        this.username = username;
        this.password = password;
    }

    public Player(String username, int valutazione){
        this.username = username;
        this.valutazione = valutazione;
        this.setStarsFromVal(valutazione);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getValutazione() {
        return valutazione;
    }

    public void setValutazione(int valutazione) {
        this.valutazione = valutazione;
    }

    public String getStelle() {
        return stelle;
    }

    public void setStelle(String stelle) {
        this.stelle = stelle;
    }

    public String getIg() {
        return ig;
    }

    public void setIg(String ig) {
        this.ig = ig;
    }

    public String getFb() {
        return fb;
    }

    public void setFb(String fb) {
        this.fb = fb;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public void setStarsFromVal(int valutazione){
        switch (valutazione){
            case 1: this.stelle = "*";
            break;
            case 2: this.stelle = "* *";
            break;
            case 3: this.stelle = "* * *";
            break;
            case 4: this.stelle = "* * * *";
            break;
            case 5: this.stelle = "* * * * *";
            default:;
        }
    }
}
