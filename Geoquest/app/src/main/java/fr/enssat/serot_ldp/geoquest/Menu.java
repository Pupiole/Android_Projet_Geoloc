package fr.enssat.serot_ldp.geoquest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paul on 01/12/16.
 */

public class Menu extends AppCompatActivity{

    private Spinner spinner;
    private Button btnjouer, btnediter;
    public String map_game = "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addItemsOnSpinner();
        addListenerOnButton();
        addListenerOnSpinnerItemSelection();
    }



    public void addItemsOnSpinner() {

        spinner = (Spinner) findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        list.add("Jeux1");
        list.add("Jeux2");
        list.add("Jeux3");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    public void addListenerOnButton(){

        btnjouer = (Button) findViewById(R.id.button4);
        btnediter = (Button) findViewById(R.id.button6);

        btnjouer.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent activity_play = new Intent(Menu.this, GeoQuestMap.class);
                activity_play.putExtra(map_game,"test");
            }
        });


        btnediter.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
               // Intent activity_play = new Intent(Menu.this, editeur.class);

            }
        });





        spinner.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(Menu.this,"OnClickListener : Spinner 1 : "+ String.valueOf(spinner.getSelectedItem()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addListenerOnSpinnerItemSelection(){

        spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }



}
