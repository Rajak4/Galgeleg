package com.example.galgeleg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Taber_akt extends AppCompatActivity {

    ImageView billede;
    TextView taberTekst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taber_akt);

        //String som blev sendt med i intent gemmes i variabel
        String data = getIntent().getExtras().getString("TABER_STRING");

        billede = findViewById(R.id.billede);
        taberTekst = findViewById(R.id.taberTekst);
        taberTekst.setText(data);

    }
}
