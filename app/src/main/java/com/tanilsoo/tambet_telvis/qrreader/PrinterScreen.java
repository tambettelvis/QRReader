package com.tanilsoo.tambet_telvis.qrreader;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tambe on 31.03.2017.
 */

public class PrinterScreen extends Activity {

    public static List<String> data = new ArrayList<>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_qr);

        ListAdapter listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        ListView listView = (ListView)findViewById(R.id.listview);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String pickedValue = String.valueOf(parent.getItemAtPosition(position));
                String[] splittedValues = pickedValue.split(" ");
                String qrId = splittedValues[splittedValues.length-1];

                Toast.makeText(PrinterScreen.this, qrId, Toast.LENGTH_SHORT).show();

                final Intent sendBack = new Intent();
                sendBack.putExtra("qr_id", qrId);
                sendBack.putExtra("diameter", splittedValues[0]);
                sendBack.putExtra("length", splittedValues[1]);
                sendBack.putExtra("wood", splittedValues[2]);


                AlertDialog alertDialog = new AlertDialog.Builder(PrinterScreen.this).create();
                alertDialog.setTitle("Print...");
                alertDialog.setMessage("Kas prindin välja " + splittedValues[0] + " " +
                        splittedValues[1] + " " + splittedValues[2] + "?");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Jah",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("RESPONSE", "OKAYYYY");
                                setResult(RESULT_OK, sendBack);
                                finish();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Ei", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("PRESS", "Pressed Ei.");
                    }
                });
                alertDialog.show();
                Log.d("RESPONSE", "FALSE");

            }
        });

    }

}
