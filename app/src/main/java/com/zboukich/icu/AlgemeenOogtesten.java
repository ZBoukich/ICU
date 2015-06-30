package com.zboukich.icu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class AlgemeenOogtesten extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oogtesten);
    }

    // Opvangen welke categorie geklikt wordt.
    public void startOogtest(View view) {
        switch (view.getId()) {
            case R.id.letters:
                Intent intent = new Intent(this, Letters.class);
                startActivity(intent);
                finish();
                break;
            case R.id.symbolen:
                Intent i = new Intent(this, Symbolen.class);
                startActivity(i);
                finish();
                break;
            case R.id.kinderversie:
                Intent k = new Intent(this, Kinderplaatjes.class);
                startActivity(k);
                finish();
                break;


        }
    }
}
