package com.zboukich.icu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;

//Deze class zorgt ervoor dat elke oogtest een subclass kan worden en
// hierdoor gebruik kan maken van deze functies. Dit voorkomt dubbele code en zorgt voor flexibiliteit.

public class Oogtest extends Activity {

    static int goed, fout = 0;
    static int counter = 1;
    int scale;
    int imageWidth, imageHeight = 77;
    int laatstgeklikt;
    static boolean falsedetector = false;
    String categorie, advies, filename;
    ImageView image;
    TextView vraagTv, timerTv, counterText;
    Button button1, button2, button3, button4;
    Vraag ontvangenVraag;
    Vraag[] vragen;
    Counter timer;


    // elke subclass start op dezelfde manier.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generic_layout);
        vraagTv = (TextView) findViewById(R.id.vraagTv);
        timerTv = (TextView) findViewById(R.id.timerTv);
        timerTv.setTextColor(Color.WHITE);
        counterText = (TextView) findViewById(R.id.counter);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        image = (ImageView) findViewById(R.id.image);
        setImageParams(imageWidth, imageHeight);
        addQuestion();
        showDB(categorie);
        timerTv.setText("00:10:00");
        timer = new Counter(10000, 1000);
        timer.start();


    }
    // Zodra een algemene oogtest doorlopen is moeten de waarde van de variabelen weer terug bij het begin
    @Override
    protected void onStop() {

        timer.cancel();
        timer = null;
        counter = 1;
        goed = 0;
        fout = 0;
        advies = null;
        finish();
        super.onStop();

    }

    // Opvangen welke categorie geklikt is. De laatste klik die gedaan  voordat de tijd voorbij was, wordt opgeslagen in de variabele laatstgeklikt.
    // Op die manier heeft de gebruiker de mogelijkheid nog binnen de tijd van keuze te wisselen.
    public void onButtonClicked(View view) {

        switch (view.getId()) {
            case R.id.button1:
                button1.requestFocus();
                laatstgeklikt = button1.getId();

                break;


            case R.id.button2:
                button2.requestFocus();
                laatstgeklikt = button2.getId();
                break;


            case R.id.button3:
                button3.requestFocus();
                laatstgeklikt = button3.getId();
                break;


            case R.id.button4:
                button4.requestFocus();
                laatstgeklikt = button4.getId();
                break;
        }

    }

    // Advies wordt gegeven op basis van variabele falsedetector . Deze variabele wordt alleen waar wanneer de gebruiker in de eerste 8 vragen een fout maakt
    public void giveAdvice() {
        if (falsedetector) {
            advies = "Wij adviseren u langs een opticien te gaan.";
        } else {
            advies = "Gefeliciteerd, u heeft de algemene test doorstaan. U heeft nu de mogelijkheid om het verkeerspel en kleurenblindheidstest uit te voeren.";
        }
    }


    //Na de 11e vraag beeindig de test, de uitslag wordt opgeslagen en de gebruiker wordt naar het resultatenscherm gestuurd
    // waar die een advies krijgt.
    public void results() {
        if (counter > 11) {
            saveScore();
            Intent intent = new Intent(this, Resultaat.class);
            intent.putExtra("advies", advies);
            startActivity(intent);


        }
    }


    // De uitslag wordt opgeslagen zodat die kan worden bijgehouden in de scores.
    public void saveScore() {

        SharedPreferences sharedPref = getSharedPreferences(filename, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String tijd = DateFormat.getDateTimeInstance().format(new Date());
        editor.putString("tijd", tijd);
        editor.putInt("fout", fout);
        editor.putInt("goed", goed);
        editor.putString("categorie", ontvangenVraag.get_categorie());
        editor.putString("advies", advies);
        editor.apply();

    }



    // goede en juiste invoer worden bijgehouden

    public void addPoints() {
        if (laatstgeklikt != 0) {
            Button laatstgeklikteButton = (Button) findViewById(laatstgeklikt);
            if (laatstgeklikteButton.getText() == ontvangenVraag.get_juist()) {
                goed++;
            } else {
                fout++;
            }
        } else {
            fout++;
        }
    }


// afbeeldinggrootte
    public void setImageParams(int width, int height) {
        if (width > 0 && height > 0) {
            image.getLayoutParams().width = width;
            image.getLayoutParams().height = height;
            image.requestLayout();
        } else {
            imageHeight = 77;
            imageWidth = 77;
        }
    }


    // De vraag wordt in de sqlitedatabase verwerkt
    public void addQuestion() {
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        for (Vraag vraag : vragen) {
            dbHandler.addQuestion(vraag);
        }
    }


    // De vraag wordt opgehaald op basis van de categorie, vervolgens worden de verkregen gegevens opgeslagen in variabelen.
    public void showDB(String categorie) {
        DBHandler dbHandler = new DBHandler(this, null, null, 1);

        ontvangenVraag = dbHandler.findQuestion(categorie);

        if (ontvangenVraag != null) {

            vraagTv.setText(String.valueOf(ontvangenVraag.get_vraag()));
            image.setImageResource(ontvangenVraag.get_afbeeldingid());
            String juist = String.valueOf(ontvangenVraag.get_juist());
            String onjuist = String.valueOf(ontvangenVraag.get_onjuist());
            String onjuist2 = String.valueOf(ontvangenVraag.get_onjuisttwee());
            String onjuist3 = String.valueOf(ontvangenVraag.get_onjuistdrie());

            String[] antwoorden = new String[]{juist, onjuist, onjuist2, onjuist3};

            // Dit zorgt ervoor dat de positie van de variabelen elke keer anders is.
            Button[] array = new Button[]{button1, button2, button3, button4};
            Collections.shuffle(Arrays.asList(array));
            for (int i = 0; i < antwoorden.length; i++) {
                array[i].setText(antwoorden[i]);
            }

        } else {
            vraagTv.setText("No Match Found");
        }
    }
// De lengte van de afbeelding kan worden aangepast.
    public void scaleImage(int subtract) {
        imageWidth = imageWidth - subtract;
        imageHeight = imageHeight - subtract;
        setImageParams(imageWidth, imageHeight);
    }
    // Deze class zorgt ervoor dat de timer bij het starten van het spel de tijd begint af te lopen.
    class Counter extends CountDownTimer {


        public Counter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }


        @Override
        public void onTick(long millisUntilFinished) {
            long millis = millisUntilFinished;
            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

            timerTv.setText(hms);

            if (millis <= 5000) {
                timerTv.setTextColor(Color.RED);
            }

            // via een booleancheck kan er een advies worden gegeven
            if (counter <= 8 && fout >= 1) {
                falsedetector = true;
            }
            counterText.setText("Vraag " + counter + "/11");


        }


        @Override
        public void onFinish() {
            addPoints();

            button1.setFocusable(false);
            button2.setFocusable(false);
            button3.setFocusable(false);
            button4.setFocusable(false);

            showDB(categorie);
            timerTv.setTextColor(Color.WHITE);
            counter++;
            scaleImage(scale);
            giveAdvice();
            results();
            start();

        }

    }
}



