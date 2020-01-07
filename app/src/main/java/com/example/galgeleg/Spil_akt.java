package com.example.galgeleg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class Spil_akt extends AppCompatActivity implements View.OnClickListener {

    //Logik-objekt oprettes, så logik-metoder kan bruges her
    Galgelogik logik = new Galgelogik();

    TextView spilOverskrift;
    Button[] tastatur;
    EditText felt;
    ImageView billede;
    TextView antalForsoeg;
    private SoundPool soundPool;
    private int taberLyd;
    private int vinderLyd;
    private long startTid;
    private long slutTid;
    private long tidBrugt;
    private int livTilbage = 7;

    public List<Long> getHighscoreList() {
        return highscoreList;
    }

    private List<Long> highscoreList = new ArrayList<>();

    //Gemmer highscore lokalt med PreferenceManager
    public void saveHighscore(List<Long> liste, String key, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(liste);
        editor.putString(key,json);
        editor.commit();
    }

    //Henter den gemte highscore med PreferenceManager
    public List<Long> getSavedHighscore(String key, Context context){
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
    public void addScore(long score, String key, Context context){
        List<Long> tempList = getSavedHighscore(key, context);
        tempList.add(score);
        saveHighscore(tempList, key, context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spil);

        spilOverskrift = findViewById(R.id.spilOverskrift);

        //Animation
        YoYo.with(Techniques.FadeInLeft)
                .duration(2000)
                .repeat(2)
                .playOn(spilOverskrift);
        spilOverskrift.setText("Henter ord fra DR...");

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
                spilOverskrift.setText("Gæt løs!");
                //Opdater ord-feltet, når der gættes
                felt.setText(logik.getSynligtOrd());

                for(Button button : tastatur){
                    button.setClickable(true);
                }
            }
        }.execute();


        //Tjekker Android version, da SoundPool skal initialiseres forskelligt alt efter version
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(2)
                    .setAudioAttributes(audioAttributes)
                    .build();


            // Til ældre versioner
        } else {
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        }
        taberLyd = soundPool.load(this, R.raw.taberlyd, 1);
        vinderLyd = soundPool.load(this, R.raw.vinderlyd, 1);


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
            tastatur[i].setClickable(false);

        }
        //Æ, Ø, Å hardcodes
        tastatur[26] = findViewById(R.id.knapÆ);
        tastatur[26].setOnClickListener(this);
        tastatur[26].setClickable(false);
        tastatur[27] = findViewById(R.id.knapØ);
        tastatur[27].setOnClickListener(this);
        tastatur[27].setClickable(false);
        tastatur[28] = findViewById(R.id.knapÅ);
        tastatur[28].setOnClickListener(this);
        tastatur[28].setClickable(false);
    }

    @Override
    public void onClick(View v) {

        //Tjekker hvad der trykkes på
        for(int i=0; i<tastatur.length; i++){
            if(v == tastatur[i]){
                if(logik.getBrugteBogstaver().size() == 0){
                    startTid = System.currentTimeMillis();
                }

                //Gætter på det bogstav der trykkes på - ændres til lille bogstav pga logik
                logik.gætBogstav(tastatur[i].getText().toString().toLowerCase());
                logik.logStatus();

                //Opdater ord-feltet, når der gættes
                felt.setText(logik.getSynligtOrd());


                if(!logik.erSidsteBogstavKorrekt()){
                    livTilbage --;
                    antalForsoeg.setText("Liv tilbage: " + livTilbage);

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
                    slutTid = System.currentTimeMillis();
                    tidBrugt = (slutTid - startTid) / 1000;

                    //Hvis spillet vindes, skifter skærmbillede, og data med antal brugte forsøg sendes med intent
                    Intent vinderIntent = new Intent(this, Vinder_akt.class);
                    String strToPutTVinder = "Tid brugt: " + tidBrugt + "s";
                    vinderIntent.putExtra("VINDER_STRING",strToPutTVinder);

                    soundPool.play(vinderLyd, 1, 1, 0, 0, 1);

                    //Tid brugt gemmes lokalt i highscore
                    addScore(tidBrugt,"NØGLE", this);

                    startActivity(vinderIntent);

                    //Hvis spillet tabes, skifter skærmbillede, og det rigtige ord vises (ordet sendes med intent)
                } else if(logik.erSpilletTabt()){

                    Intent taberIntent = new Intent(this,Taber_akt.class);
                    String strToPutTaber = "Det rigtige ord var: " + logik.getOrdet();
                    soundPool.play(taberLyd, 1, 1, 0, 0, 1);

                    taberIntent.putExtra("TABER_STRING", strToPutTaber);
                    startActivity(taberIntent);
                }
            }
        }
    }
}
