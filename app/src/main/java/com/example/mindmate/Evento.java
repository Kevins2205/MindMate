package com.example.mindmate;

public class Evento {
    public String titolo;
    public String descrizione;
    public double latitudine;
    public double longitudine;

    public Evento(String titolo, String descrizione, double latitudine, double longitudine) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }
}
