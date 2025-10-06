package com.example.mindmate;

import java.util.Objects;

public class Ispirazione {
    public String tipo;
    public String id;
    public String titolo;
    public Ispirazione(String tipo, String id, String titolo) {
        this.tipo = tipo;
        this.id = id;
        this.titolo = titolo;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ispirazione that = (Ispirazione) o;
        return Objects.equals(tipo, that.tipo) && Objects.equals(id, that.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(tipo, id);
    }
}

