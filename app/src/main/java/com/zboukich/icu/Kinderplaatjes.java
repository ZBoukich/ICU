package com.zboukich.icu;

import android.os.Bundle;


public class Kinderplaatjes extends Oogtest {



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // De onderstaande vragen worden aan de superclass meegegeven, zodat die het verder kan afhandelen in de Sqlitedatabase
        // en door de superclass in de functies gebruikt kan worden

        super.vragen = Vragen.kinderen;
        super.categorie = "kinderen";
        super.filename = "scores";

        // scale van de afbeelding, die met 4px om de vraag kleiner wordt.
        super.scale = 4;
        super.onCreate(savedInstanceState);


    }



    static class Vragen {

        // Vraagobjecten die ieder een rij presenteren in de SQLitedatabase.
        // Bij toekomstige uitbreiding kunnen meer vragen op deze manier gemakkelijk worden toegevoegd.

        public static Vraag[] kinderen = new Vraag[]{
                new Vraag("kinderen", "Welk plaatje zie je in beeld? ", "vis", "slang", "rat", "zeemermin", R.drawable.vis),
                new Vraag("kinderen", "Welk plaatje zie je in beeld? ", "vlinder", "spin", "bij", "wesp", R.drawable.vlinder),
                new Vraag("kinderen", "Welk plaatje zie je in beeld? ", "slang", "spin", "vis", "vogel", R.drawable.slang),
                new Vraag("kinderen", "Welk plaatje zie je in beeld? ", "paard", "ezel", "neushoorn", "kameel", R.drawable.paard),
                new Vraag("kinderen", "Welk plaatje zie je in beeld?", "muis", "hond", "kat", "mol", R.drawable.muis),
                new Vraag("kinderen", "Welk plaatje zie je in beeld? ", "aap", "hond", "tijger", "leeuw", R.drawable.aap),
                new Vraag("kinderen", "Welk plaatje zie je in beeld?", "aap", "mens", "hond", "spin", R.drawable.aap),
                new Vraag("kinderen", "Welk plaatje zie je in beeld?", "beer", "zwijn", "stier", "panter", R.drawable.beer),
                new Vraag("kinderen", "Welk plaatje zie je in beeld?", "giraffe", "kangaroo", "sprinkhaan", "zwaan", R.drawable.giraffe),
                new Vraag("kinderen", "Welk plaatje zie je in beeld?", "hond", "beer", "wolf", "hyena", R.drawable.hond)
        };
    }
}
