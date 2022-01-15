package com.example.optic.entities;

public class Campo {
    private String Nomec;
    private String Provincia;

    public Campo(String Nomec,String Provincia){
        this.Nomec=Nomec;
        this.Provincia=Provincia;
    }

    public String getNomec() {
        return Nomec;
    }

    public void setNomec(String nomec) {
        Nomec = nomec;
    }

    public String getProvincia() {
        return Provincia;
    }

    public void setProvincia(String provincia) {
        Provincia = provincia;
    }
}
