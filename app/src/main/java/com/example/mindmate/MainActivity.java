package com.example.mindmate;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Accesso corretto alla ImageView logoMindMate
        ImageView logoMindMate = findViewById(R.id.logoMindMate);
        // Puoi ora lavorare con logoMindMate, ad esempio cambiare immagine, aggiungere listener, ecc.
    }
}
