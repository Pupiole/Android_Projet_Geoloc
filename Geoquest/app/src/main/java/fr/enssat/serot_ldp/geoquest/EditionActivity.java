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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by yserot on 14/01/17.
 */

public class EditionActivity extends AppCompatActivity {
    private Context context;
    private static List<Balises> parcours;
    private static List<String> listParcours;
    private static int posBalise;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edition);
        context = this;
        parcours = new ArrayList<>();
        listParcours = new ArrayList<>();

        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listParcours);
        ListView listViewBalises = (ListView) findViewById(R.id.listBalises);
        listViewBalises.setAdapter(itemsAdapter);
        listViewBalises.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                posBalise = position;
                showDialogEdit();
            }
        });

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

        Button cancelParcours = (Button) findViewById(R.id.boutonCancelParcours);
        cancelParcours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showDialog() {
        DialogFragment dialog = new AjoutBaliseDialogFragment();
        dialog.show(this.getFragmentManager(), "AjoutBaliseDialogFragment");
    }

    private void showDialogEdit() {
        DialogFragment dialog = new EditBaliseDialogFragment();
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
                            if (checkDouble(editLong.getText().toString()) && checkDouble(editLat.getText().toString())
                                    && (!editTextH.getText().toString().isEmpty() || !editImageH.getText().toString().isEmpty())) {
                                Coordonnee coordonnee = new Coordonnee(Double.parseDouble(editLong.getText().toString()),
                                        Double.parseDouble(editLat.getText().toString()));
                                Indice indice = new Indice(editTextH.getText().toString(), editImageH.getText().toString());
                                parcours.add(new Balises(coordonnee, indice));
                                listParcours.add("Balise" + parcours.size());
                            }
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
            final View dialogView = inflater.inflate(R.layout.activity_ajout_balise, null);

            EditText editLong = (EditText) dialogView.findViewById(R.id.editLongitude);
            EditText editLat = (EditText) dialogView.findViewById(R.id.editLatitude);
            EditText editHintText = (EditText) dialogView.findViewById(R.id.editIndice);
            EditText editHintImage = (EditText) dialogView.findViewById(R.id.editUlrImage);

            editLong.setText("" + parcours.get(posBalise).getC().getLongitude());
            editLat.setText("" + parcours.get(posBalise).getC().getLatitude());
            editHintText.setText("" + parcours.get(posBalise).getI().getText());
            editHintImage.setText("" + parcours.get(posBalise).getI().getImage());

            builder.setTitle(R.string.modify_balise_title)
                    .setView(dialogView)
                    .setPositiveButton(R.string.modify, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            EditText editLong = (EditText) dialogView.findViewById(R.id.editLongitude);
                            EditText editLat = (EditText) dialogView.findViewById(R.id.editLatitude);
                            EditText editTextH = (EditText) dialogView.findViewById(R.id.editIndice);
                            EditText editImageH = (EditText) dialogView.findViewById(R.id.editUlrImage);
                            if (checkDouble(editLong.getText().toString()) && checkDouble(editLat.getText().toString())
                                    && (!editTextH.getText().toString().isEmpty() || !editImageH.getText().toString().isEmpty())) {
                                Coordonnee coordonnee = new Coordonnee(Double.parseDouble(editLong.getText().toString()),
                                        Double.parseDouble(editLat.getText().toString()));
                                Indice indice = new Indice(editTextH.getText().toString(), editImageH.getText().toString());
                                parcours.add(posBalise, new Balises(coordonnee, indice));
                            }
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            getDialog().cancel();
                        }
                    })
                    .setNeutralButton(R.string.delete, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            parcours.remove(posBalise);
                            listParcours.remove(listParcours.size()-1);
                        }
                    });
            return builder.create();
        }
    }

    static private boolean checkDouble(String text) {
        final String Digits = "(\\p{Digit}+)";
        final String HexDigits = "(\\p{XDigit}+)";
        // an exponent is 'e' or 'E' followed by an optionally
        // signed decimal integer.
        final String Exp = "[eE][+-]?" + Digits;
        final String fpRegex =
                ("[\\x00-\\x20]*" + // Optional leading "whitespace"
                        "[+-]?(" +         // Optional sign character
                        "NaN|" +           // "NaN" string
                        "Infinity|" +      // "Infinity" string

                        // A decimal floating-point string representing a finite positive
                        // number without a leading sign has at most five basic pieces:
                        // Digits . Digits ExponentPart FloatTypeSuffix
                        //
                        // Since this method allows integer-only strings as input
                        // in addition to strings of floating-point literals, the
                        // two sub-patterns below are simplifications of the grammar
                        // productions from the Java Language Specification, 2nd
                        // edition, section 3.10.2.

                        // Digits ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
                        "(((" + Digits + "(\\.)?(" + Digits + "?)(" + Exp + ")?)|" +

                        // . Digits ExponentPart_opt FloatTypeSuffix_opt
                        "(\\.(" + Digits + ")(" + Exp + ")?)|" +

                        // Hexadecimal strings
                        "((" +
                        // 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
                        "(0[xX]" + HexDigits + "(\\.)?)|" +

                        // 0[xX] HexDigits_opt . HexDigits BinaryExponent FloatTypeSuffix_opt
                        "(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" +

                        ")[pP][+-]?" + Digits + "))" +
                        "[fFdD]?))" +
                        "[\\x00-\\x20]*");// Optional trailing "whitespace"

        if (Pattern.matches(fpRegex, text)) {
            return true;
        } else {
            return false;
        }
    }

}
