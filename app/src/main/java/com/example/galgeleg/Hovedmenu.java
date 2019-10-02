package com.example.galgeleg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Hovedmenu extends AppCompatActivity {

    Galgelogik logik = new Galgelogik();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
