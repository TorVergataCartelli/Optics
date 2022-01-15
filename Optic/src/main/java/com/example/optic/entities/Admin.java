package com.example.optic.entities;

public class Admin {
    private String username;
    private String password;
    private String descrizioneC;
    private String nomeC;
    private String ig;
    private String fb;
    private String wa;
    private String via;
    private String provincia;

    public Admin(){}

    public Admin(String username, String password){
        this.username = username;
        this.password = password;
    }

    public Admin(String username, String password, String via, String nomeC, String provincia){
        this.username = username;
        this.password = password;
        this.via = via;
        this.nomeC = nomeC;
        this.provincia = provincia;
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

    public String getDescrizioneC() {
        return descrizioneC;
    }

    public void setDescrizioneC(String descrizioneC) {
        this.descrizioneC = descrizioneC;
    }

    public String getNomeC() {
        return nomeC;
    }

    public void setNomeC(String nomeC) {
        this.nomeC = nomeC;
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

    public String getWa() {
        return wa;
    }

    public void setWa(String wa) {
        this.wa = wa;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
}
