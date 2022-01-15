package com.example.optic.entities;

import java.sql.Blob;

public class Foto {

    private int idFoto;
    private Blob Immagine;
    private String fk_Username;

    public int getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(int idFoto) {
        this.idFoto = idFoto;
    }

    public Blob getImmagine() {
        return Immagine;
    }

    public void setImmagine(Blob immagine) {
        Immagine = immagine;
    }

    public String getFk_Username() {
        return fk_Username;
    }

    public void setFk_Username(String fk_Username) {
        this.fk_Username = fk_Username;
    }
}
