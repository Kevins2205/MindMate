package com.example.mindmate;

public class Evento {
    public String titolo;
    public String descrizione;
    public String posizione;
    public String data;
    public double latitudine;
    public double longitudine;

    public Evento(String titolo, String descrizione, String posizione, String data, double latitudine, double longitudine) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.posizione = posizione;
        this.data = data;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }
}
