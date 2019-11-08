package com.example.galgeleg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Vinder_akt extends AppCompatActivity {

    ImageView billede;
    TextView vinderTekst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vinder_akt);

        //String som blev sendt med i intent gemmes i variabel
        String data = getIntent().getExtras().getString("VINDER_STRING");

        billede = findViewById(R.id.billede);
        vinderTekst = findViewById(R.id.vinderTekst);
        vinderTekst.setText(data);
    }
}
