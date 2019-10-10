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
        char hej = 65;

        for(int i = 0; i < tastatur.length - 4; i++){
            String næsteKnap = "knap" + hej;
            hej++;
            int knapId = getResources().getIdentifier(næsteKnap, "id", getPackageName());
            tastatur[i] = findViewById(knapId);
            tastatur[i].setOnClickListener(this);

        }



    }

    @Override
    public void onClick(View v) {


    }


}
