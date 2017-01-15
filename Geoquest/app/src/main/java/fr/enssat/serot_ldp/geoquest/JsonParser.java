package fr.enssat.serot_ldp.geoquest;


import android.content.Context;
import android.content.ContextWrapper;

import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by paul on 12/12/16. 
 */

public class JsonParser {

    public void JsonParser(String Localisationgeojson) throws JSONException {

        Gson gson = new Gson();
        Balises[] staff = null;
        try {
            staff = gson.fromJson(new FileReader("/home/paul/Android_Projet_Geoloc/Geoquest/app/src/main/java/fr/enssat/serot_ldp/geoquest/Test.json"), Balises[].class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for(int i =0;i<staff.length;i++) {
            System.out.println(staff[i]);
        }
    }

    public void saveJsonFile(Context context, String parcoursName, List<Balises> parcours) throws JSONException {
        String json = new Gson().toJson(parcours);
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new
                    File(context.getFilesDir() + File.separator + "parcours_" + parcoursName)));
            bufferedWriter.write(json);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} 