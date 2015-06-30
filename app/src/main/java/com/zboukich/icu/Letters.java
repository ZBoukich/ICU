package com.zboukich.icu;

import android.os.Bundle;


public class Letters extends Oogtest {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // De onderstaande vragen worden aan de superclass meegegeven, zodat die het verder kan afhandelen in de Sqlitedatabase.

        super.vragen = Vragen.letters;
        super.categorie = "letters";
        super.filename = "scores";
        // scale van de afbeelding, die met 7px om de vraag kleiner wordt.
        super.scale = 7;

        super.onCreate(savedInstanceState);

    }


    // vraagobjecten die ieder een rij presenteren in de SQLitedatabase.
    // Bij toekomstige uitbreiding kunnen meer vragen op deze manier gemakkelijk worden toegevoegd.

    static class Vragen {


        public static Vraag[] letters = new Vraag[]{
                new Vraag("letters", "Welke letter ziet u in beeld? ", "c", "g", "a", "b", R.drawable.c),
                new Vraag("letters", "Welke letter ziet u in beeld? ", "d", "j", "y", "f", R.drawable.d),
                new Vraag("letters", "Welke letter ziet u in beeld? ", "e", "g", "a", "m", R.drawable.e),
                new Vraag("letters", "Welke letter ziet u in beeld? ", "f", "t", "g", "v", R.drawable.f),
                new Vraag("letters", "Welke letter ziet u in beeld? ", "l", "r", "j", "i", R.drawable.l),
                new Vraag("letters", "Welke letter ziet u in beeld? ", "o", "w", "q", "f", R.drawable.o),
                new Vraag("letters", "Welke letter ziet u in beeld? ", "p", "b", "n", "h", R.drawable.p),
                new Vraag("letters", "Welke letter ziet u in beeld? ", "z", "b", "n", "h", R.drawable.z)

        };
    }


}
