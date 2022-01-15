package com.example.optic.bean;

import java.util.Calendar;

public class GiornataBean implements java.io.Serializable {
    private int idPlay;
    private Calendar data;
    private int nGiocatori;
    private String evento;
    private String admin;

    public void GiornataBean(){
    }

    public int getIdPlay() {
        return idPlay;
    }

    public void setIdPlay(int idPlay) {
        this.idPlay = idPlay;
    }

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }

    public int getnGiocatori() {
        return nGiocatori;
    }

    public void setnGiocatori(int nGiocatori) {
        this.nGiocatori = nGiocatori;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }
}
