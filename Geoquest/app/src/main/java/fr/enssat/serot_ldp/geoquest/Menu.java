package fr.enssat.serot_ldp.geoquest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by paul on 01/12/16.
 */

public class Menu extends AppCompatActivity {

    private Spinner spinner;
    private Button btnjouer, btnediter;
    public String map_game = "test";
    private static final String TAG = "menu";
    String[] filesnames = null;
    List<String> list = null;
    String selected_item =" ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        btnjouer = (Button) findViewById(R.id.button4);
        btnjouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity_play = new Intent(Menu.this, GeoQuestMap.class);
                activity_play.putExtra("menu",selected_item);
                Log.d("Filename selected :", selected_item);
                startActivity(activity_play);
            }
        });

        btnediter = (Button) findViewById(R.id.button6);
        btnediter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent activity_editor = new Intent(Menu.this, EditionActivity.class);
                startActivity(activity_editor);
            }
        });

        spinner = (Spinner) findViewById(R.id.spinner);
        getFilenames();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_item = spinner.getSelectedItem().toString();
                Log.d(TAG,selected_item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selected_item = spinner.getSelectedItem().toString();
                Log.d(TAG,selected_item);
            }
        });

    }

    private void getFilenames() {

        this.filesnames = getApplicationContext().getFilesDir().list();
        this.list = new ArrayList<String>();
        //Pattern p = Pattern.compile("parcours_./");

        for (int i = 0; i < filesnames.length; i++) {

            // Matcher m = p.matcher(filesnames[i]);
            //if(m.matches()) {
            list.add(filesnames[i]);

            //            }
        }
        ArrayAdapter<String> filesnameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, list);
        spinner.setAdapter(filesnameAdapter);


    }
}
