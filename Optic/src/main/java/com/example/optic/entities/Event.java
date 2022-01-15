package com.example.optic.entities;

public class Event {
    private String nome;
    private String descrizione;
    private int giocatoriCons = 0;

    public Event(String nome){
        this.nome = nome;
    }
    public Event(String nome, String descrizione){
        this.nome = nome;
        this.descrizione = descrizione;
    }
    public Event(String nome, String descrizione, int giocatoriCons){
        this.nome = nome;
        this.descrizione = descrizione;
        this.giocatoriCons = giocatoriCons;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getGiocatoriCons() {
        return giocatoriCons;
    }

    public void setGiocatoriCons(int giocatoriCons) {
        this.giocatoriCons = giocatoriCons;
    }

    public String getFormattedText(){
        String s = nome+"\n"+descrizione+"\n"+"Giocatori consigliati: "+giocatoriCons;
        return s;
    }
}
