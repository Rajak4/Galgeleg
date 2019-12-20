package com.example.galgeleg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Highscore_akt extends AppCompatActivity {

    RecyclerView highscore;
    Galgelogik logik;
    Spil_akt hej = new Spil_akt();
    List<Long> highscoreList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore_akt);

        highscore = new RecyclerView(this);
        highscore.setLayoutManager(new LinearLayoutManager(this));
        highscore.setAdapter(mAdapter);
        highscoreList = hej.getSavedHighscore("NØGLE", this);
        Collections.sort(highscoreList);

        setContentView(highscore);

        System.out.println("Min længde er: " + highscoreList.size());
    }

    RecyclerView.Adapter mAdapter = new RecyclerView.Adapter() {
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View element = getLayoutInflater().inflate(R.layout.activity_highscore_akt, parent, false);
            return new RecyclerView.ViewHolder(element) {
            };
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            TextView placeringPåListe = holder.itemView.findViewById(R.id.placeringPåListe);
            TextView highscoreData = holder.itemView.findViewById(R.id.highscoreData);

            placeringPåListe.setText("" + (position + 1));
            highscoreData.setText("Tid brugt: " + highscoreList.get(position) + "s");
        }

        @Override
        public int getItemCount() {

            return highscoreList.size();
        }
    };
}
