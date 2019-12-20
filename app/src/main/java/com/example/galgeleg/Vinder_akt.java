package com.example.galgeleg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class Vinder_akt extends AppCompatActivity implements View.OnClickListener {

    ImageView billede;
    TextView vinderTekst;
    Button hovedmenu, spilIgen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vinder_akt);

        //String som blev sendt med i intent gemmes i variabel
        String data = getIntent().getExtras().getString("VINDER_STRING");

        billede = findViewById(R.id.billede);
        vinderTekst = findViewById(R.id.vinderTekst);
        vinderTekst.setText(data);
        hovedmenu = findViewById(R.id.hovedmenuVinder);
        spilIgen = findViewById(R.id.spilIgenVinder);

        hovedmenu.setOnClickListener(this);
        spilIgen.setOnClickListener(this);

        YoYo.with(Techniques.RubberBand)
                .duration(500)
                .repeat(25)
                .playOn(billede);


    }


    @Override
    public void onClick(View v) {
        if(v == hovedmenu){
            Intent i = new Intent(this, Hovedmenu_akt.class);
            startActivity(i);
        } else if(v == spilIgen){
            Intent i = new Intent(this, Spil_akt.class);
            startActivity(i);
        }
    }
}
