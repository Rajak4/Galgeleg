package com.example.galgeleg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Spil_akt extends AppCompatActivity implements View.OnClickListener {

    Galgelogik logik = new Galgelogik();

    Button[] tastatur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spil);

        tastatur = new Button[29];

        //ascii værdi A
        char hej = 65;

        //setOnClickListener laves til hver button i tastaturet
        for(int i = 0; i < tastatur.length - 4; i++){
            String næsteKnap = "knap" + hej;
            hej++;
            int knapId = getResources().getIdentifier(næsteKnap, "id", getPackageName());
            tastatur[i] = findViewById(knapId);
            tastatur[i].setOnClickListener(this);

        }
        //Æ, Ø, Å hardcodes
        tastatur[27] = findViewById(R.id.knapÆ);
        tastatur[27].setOnClickListener(this);
        tastatur[28] = findViewById(R.id.knapØ);
        tastatur[28].setOnClickListener(this);
        tastatur[29] = findViewById(R.id.knapÅ);
        tastatur[29].setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {


    }


}
