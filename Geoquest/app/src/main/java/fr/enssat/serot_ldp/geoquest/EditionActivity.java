package fr.enssat.serot_ldp.geoquest;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yserot on 14/01/17.
 */

public class EditionActivity extends AppCompatActivity {
    private Context context;
    private static List<Balises> parcours;
    private static List<String> listParcours;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edition);
        context = this;
        parcours = new ArrayList<>();
        listParcours = new ArrayList<>();

        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listParcours);
        ListView listViewBalises = (ListView) findViewById(R.id.listBalises);
        listViewBalises.setAdapter(itemsAdapter);

        Button boutonBalise = (Button) findViewById(R.id.boutonAjoutBalise);
        boutonBalise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        Button createParcours = (Button) findViewById(R.id.boutonCreerParcours);
        createParcours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nomParcours = (EditText) findViewById(R.id.editNomParcours);
                JsonParser parser = new JsonParser();
                try {
                    parser.saveJsonFile(context, nomParcours.getText().toString(), parcours);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showDialog() {
        DialogFragment dialog = new AjoutBaliseDialogFragment();
        dialog.show(this.getFragmentManager(), "AjoutBaliseDialogFragment");
    }

    public static class AjoutBaliseDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.activity_ajout_balise, null);

            builder.setTitle(R.string.create_balise_title)
                    .setView(dialogView)
                    .setPositiveButton(R.string.create_balise, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            EditText editLong = (EditText) dialogView.findViewById(R.id.editLongitude);
                            EditText editLat = (EditText) dialogView.findViewById(R.id.editLatitude);
                            EditText editTextH = (EditText) dialogView.findViewById(R.id.editIndice);
                            EditText editImageH = (EditText) dialogView.findViewById(R.id.editUlrImage);
                            Coordonnee coordonnee = new Coordonnee(Double.parseDouble(editLong.getText().toString()),
                                    Double.parseDouble(editLat.getText().toString()));
                            Indice indice = new Indice(editTextH.getText().toString(), editImageH.getText().toString());
                            parcours.add(new Balises(coordonnee, indice));
                            listParcours.add("Balise" + parcours.size());
                            editLong.setText("");
                            editLat.setText("");
                            editTextH.setText("");
                            editImageH.setText("");
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            getDialog().cancel();
                        }
                    });
            return builder.create();
        }
    }

    public static class EditBaliseDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();

            builder.setTitle(R.string.modify_balise_title)
                    .setView(inflater.inflate(R.layout.activity_ajout_balise, null))
                    .setPositiveButton(R.string.modify, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // sign in the user ...
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            getDialog().cancel();
                        }
                    })
                    .setNeutralButton(R.string.delete, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            return builder.create();
        }
    }
}
