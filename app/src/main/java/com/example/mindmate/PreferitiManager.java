package com.example.mindmate;

import java.util.ArrayList;
import java.util.List;

public class PreferitiManager {
    public static List<Ispirazione> preferiti = new ArrayList<>();

    public static void aggiungiPreferito(Ispirazione ispirazione) {
        if (!preferiti.contains(ispirazione)) {
            preferiti.add(ispirazione);
        }
    }

    public static void rimuoviPreferito(Ispirazione ispirazione) {
        preferiti.remove(ispirazione);
    }

    public static boolean isPreferito(Ispirazione ispirazione) {
        return preferiti.contains(ispirazione);
    }
}

