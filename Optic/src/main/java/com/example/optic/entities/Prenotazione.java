package com.example.optic.entities;

public class Prenotazione {
    private String fk_Username;
    private String fk_idGiornata;

    public String getFk_Username() {
        return fk_Username;
    }

    public void setFk_Username(String fk_Username) {
        this.fk_Username = fk_Username;
    }

    public String getFk_idGiornata() {
        return fk_idGiornata;
    }

    public void setFk_idGiornata(String fk_idGiornata) {
        this.fk_idGiornata = fk_idGiornata;
    }
}
