package com.example.galgeleg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class Spil_akt extends AppCompatActivity implements View.OnClickListener {

    //Logik-objekt oprettes, så logik-metoder kan bruges her
    Galgelogik logik = new Galgelogik();

    Button[] tastatur;
    EditText felt;
    ImageView billede;
    TextView antalForsoeg;

    public List<Integer> getHighscoreList() {
        return highscoreList;
    }

    private List<Integer> highscoreList = new ArrayList<>();

    //Gemmer highscore lokalt med PreferenceManager
    public void saveHighscore(List<Integer> liste, String key, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(liste);
        editor.putString(key,json);
        editor.commit();
    }

    //Henter den gemte highscore med PreferenceManager
    public List<Integer> getSavedHighscore(String key, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String gemt = prefs.getString(key, "HEJ");
        Type type = new TypeToken<List <Integer>>(){}.getType();
        if(gemt.equals("HEJ")){
            return new ArrayList<>();
        } else {
           return gson.fromJson(gemt, type);
        }
    }

    //Tilføjer score til highscore vha. ovenstående saveHighscore-metode
    public void addScore(int score, String key, Context context){
        List<Integer> tempList = getSavedHighscore(key, context);
        tempList.add(score);
        saveHighscore(tempList, key, context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spil);

        //Baggrundstråd oprettes, hvori ord hentes fra DR
        new AsyncTask(){
            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    logik.hentOrdFraDr();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {

            }
        }.execute();



        felt = findViewById(R.id.felt);
        billede = findViewById(R.id.billede);
        antalForsoeg = findViewById(R.id.antalForsoeg);

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
                antalForsoeg.setText("Forsøg brugt: " + logik.getBrugteBogstaver().size());


                if(!logik.erSidsteBogstavKorrekt()){

                    //Ved forkert gæt farves bogstav rødt
                    tastatur[i].setTextColor(Color.parseColor("#FF0000"));

                    //Opdaterer billedet efter hvor mange forkerte gæt man har
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
                    //Ved rigtigt gæt farves bogstav grønt
                    tastatur[i].setTextColor(Color.parseColor("#08A026"));
                }

                if(logik.erSpilletVundet()){

                    //Hvis spillet vindes, skifter skærmbillede, og data med antal brugte forsøg sendes med intent
                    Intent vinderIntent = new Intent(this, Vinder_akt.class);
                    String strToPutTVinder = "Forsøg brugt: " + logik.getBrugteBogstaver().size();
                    vinderIntent.putExtra("VINDER_STRING",strToPutTVinder);

                    //Antal brugte forsøg gemmes lokalt i highscore
                    addScore(logik.getBrugteBogstaver().size(),"NØGLE", this);

                    startActivity(vinderIntent);

                    //Hvis spillet tabes, skifter skærmbillede, og det rigtige ord vises (ordet sendes med intent)
                } else if(logik.erSpilletTabt()){
                    Intent taberIntent = new Intent(this,Taber_akt.class);
                    String strToPutTaber = "Det rigtige ord var: " + logik.getOrdet();

                    taberIntent.putExtra("TABER_STRING", strToPutTaber);
                    startActivity(taberIntent);
                }
            }
        }
    }
}
