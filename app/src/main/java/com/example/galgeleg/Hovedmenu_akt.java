package com.example.galgeleg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Hovedmenu_akt extends AppCompatActivity implements View.OnClickListener {

    Button hjaelpKnap, spilKnap, highscoreKnap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hovedmenu);

        //Listeners på knapper
        spilKnap = findViewById(R.id.startspil);
        spilKnap.setOnClickListener(this);

        hjaelpKnap = findViewById(R.id.hjaelp);
        hjaelpKnap.setOnClickListener(this);

        highscoreKnap = findViewById(R.id.highscoreKnap);
        highscoreKnap.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v==spilKnap){
            Intent i = new Intent(this, Spil_akt.class);
            startActivity(i);
        }
        else if(v==hjaelpKnap){
            Intent i = new Intent(this, Hjaelp_akt.class);
            startActivity(i);
        }
        else if(v==highscoreKnap){
            Intent i = new Intent(this, Highscore_akt.class);
            startActivity(i);
        }
    }
}
