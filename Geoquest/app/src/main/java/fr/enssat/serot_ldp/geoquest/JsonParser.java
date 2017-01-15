package fr.enssat.serot_ldp.geoquest;

import org.json.JSONException;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;

import fr.enssat.serot_ldp.geoquest.Balises;

/**
 * Created by paul on 12/12/16.
 */

public class JsonParser {

    public Balises[] JsonParser(String Localisationgeojson) throws JSONException {

        Balises[] list = null;
        Gson gson = new Gson();
        try {
            list = gson.fromJson(new FileReader("/home/paul/Android_Projet_Geoloc/Geoquest/app/src/main/java/fr/enssat/serot_ldp/geoquest/Test.json"), Balises[].class);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }
}


