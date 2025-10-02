package com.example.mindmate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Obiettivo implements Serializable {
    private final String titolo;
    private final int iconaResId;

    public Obiettivo(String titolo, int iconaResId) {
        this.titolo = titolo;
        this.iconaResId = iconaResId;
    }

    public String getTitolo() { return titolo; }
    public int getIconaResId() { return iconaResId; }

    // Lista statica di tutti gli obiettivi disponibili
    public static List<Obiettivo> getAllObiettivi() {
        List<Obiettivo> list = new ArrayList<>();
        list.add(new Obiettivo("Ridurre lo stress", R.drawable.obiettivo1));
        list.add(new Obiettivo("Rilassarsi di più", R.drawable.obiettivo2));
        list.add(new Obiettivo("Gestire la rabbia", R.drawable.obiettivo3));
        list.add(new Obiettivo("Avere più motivazione", R.drawable.obiettivi4));
        list.add(new Obiettivo("Migliorare la qualità del sonno", R.drawable.obiettivi5));
        list.add(new Obiettivo("Aumentare l'autostima", R.drawable.obiettivi6));
        list.add(new Obiettivo("Imparare a prendersi cura di sé", R.drawable.obiettivi7));
        list.add(new Obiettivo("Superare gli attacchi di panico", R.drawable.obiettivi8));
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Obiettivo that = (Obiettivo) o;
        return titolo.equals(that.titolo);
    }

    @Override
    public int hashCode() {
        return titolo.hashCode();
    }
}
