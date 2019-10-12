package com.example.galgeleg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


public class Spil_akt extends AppCompatActivity implements View.OnClickListener {

    //Logik-objekt oprettes, så logik-metoder kan bruges her
    Galgelogik logik = new Galgelogik();

    Button[] tastatur;
    EditText felt;
    ImageView billede;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spil);

        felt = findViewById(R.id.felt);
        billede = findViewById(R.id.billede);

        //Tastatur-array af Button views oprettes
        tastatur = new Button[29];

        //ascii værdi A
        char hej = 65;

        //setOnClickListener laves til hver button i tastaturet. -3 pga ÆØÅ
        for(int i = 0; i < tastatur.length - 3; i++){

            //Knappernes id er navngivet knapA, knapB osv...
            String næsteKnap = "knap" + hej;
            hej++;
            int knapId = getResources().getIdentifier(næsteKnap, "id", getPackageName());
            tastatur[i] = findViewById(knapId);
            tastatur[i].setOnClickListener(this);

        }
        //Æ, Ø, Å hardcodes
        tastatur[26] = findViewById(R.id.knapÆ);
        tastatur[26].setOnClickListener(this);
        tastatur[27] = findViewById(R.id.knapØ);
        tastatur[27].setOnClickListener(this);
        tastatur[28] = findViewById(R.id.knapÅ);
        tastatur[28].setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        //Tjekker hvad der trykkes på
        for(int i=0; i<tastatur.length; i++){
            if(v == tastatur[i]){

                //Gætter på det bogstav der trykkes på - ændres til lille bogstav pga logik
                logik.gætBogstav(tastatur[i].getText().toString().toLowerCase());
                logik.logStatus();

                //Opdater ord-feltet, når der gættes
                felt.setText(logik.getSynligtOrd());

                //Opdaterer billedet efter hvor mange forkerte gæt man har
                if(!logik.erSidsteBogstavKorrekt()){

                    tastatur[i].setTextColor(Color.parseColor("#FF0000"));

                    switch (logik.getAntalForkerteBogstaver()){

                        case 1:
                            billede.setImageResource(R.drawable.forkert1);
                            break;
                        case 2:
                            billede.setImageResource(R.drawable.forkert2);
                            break;
                        case 3:
                            billede.setImageResource(R.drawable.forkert3);
                            break;
                        case 4:
                            billede.setImageResource(R.drawable.forkert4);
                            break;
                        case 5:
                            billede.setImageResource(R.drawable.forkert5);
                            break;
                        case 6:
                            billede.setImageResource(R.drawable.forkert6);
                            break;
                    }


                }
                else {
                    tastatur[i].setTextColor(Color.parseColor("#08A026"));
                }
                if(logik.erSpilletVundet()){
                    billede.setImageResource(R.drawable.vinder);
                } else if(logik.erSpilletTabt()){
                    billede.setImageResource(R.drawable.taber);
                }

            }
        }


    }


}
