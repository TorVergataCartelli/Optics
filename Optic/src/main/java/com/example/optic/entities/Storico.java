package com.example.optic.entities;
public class Storico {
    private String data;
    private String campo;

    public Storico(String data, String campo){
        this.data = data;
        this.campo = campo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }
}
