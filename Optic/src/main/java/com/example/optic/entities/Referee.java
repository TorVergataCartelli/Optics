package com.example.optic.entities;

public class Referee {
    private String username;
    private String password;
    private String adminCampo;

    public Referee(String username, String password){
        this.username = username;
        this.password = password;
    }

    public Referee(String username, String password, String adminCampo){
        this.username = username;
        this.password = password;
        this.adminCampo = adminCampo;
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

    public String getAdminCampo() {
        return adminCampo;
    }

    public void setAdminCampo(String adminCampo) {
        this.adminCampo = adminCampo;
    }
}
