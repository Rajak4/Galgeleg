package com.example.galgeleg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Hovedmenu_akt extends AppCompatActivity implements View.OnClickListener {

    Galgelogik logik = new Galgelogik();
    Button hjaelpKnap, spilKnap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hovedmenu);


        spilKnap = findViewById(R.id.startspil);
        spilKnap.setOnClickListener(this);

        hjaelpKnap = findViewById(R.id.hjaelp);
        hjaelpKnap.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {
        if(v==spilKnap){
            spilKnap.setText("hej");
        }
        else if(v==hjaelpKnap){
            hjaelpKnap.setText("hej");
        }

    }
}
