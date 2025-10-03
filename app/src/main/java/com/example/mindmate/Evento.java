package com.example.mindmate;

import java.util.ArrayList;
import java.util.List;

public class Evento {
    public String titolo;
    public String descrizione;
    public String posizione;
    public String data;
    public double latitudine;
    public double longitudine;
    public static List<Evento> eventiPrenotati = new ArrayList<>();

    public Evento(String titolo, String descrizione, String posizione, String data, double latitudine, double longitudine) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.posizione = posizione;
        this.data = data;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }

    public String getTitolo() { return titolo; }
    public String getDescrizione() { return descrizione; }
    public String getPosizione() { return posizione; }
    public String getData() { return data; }
    public double getLatitudine() { return latitudine; }
    public double getLongitudine() { return longitudine; }
}
