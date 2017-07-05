package com.tanilsoo.tambet_telvis.qrreader;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tambet-Telvis on 5.05.2017.
 */

public class RemovePacks extends Activity {

    public static List<String> postTypes =  new ArrayList<String>();
    List<Integer> amount =  new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_packs);
        createAmountSpinner();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, postTypes);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.pack_spinner);
        sItems.setAdapter(adapter);


        ArrayAdapter<Integer> adapter2 = new ArrayAdapter<Integer>(
                this, android.R.layout.simple_spinner_item, amount);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems2 = (Spinner) findViewById(R.id.amt_spinner);
        sItems2.setAdapter(adapter2);

        Button removePacksBtn = (Button)findViewById(R.id.remove_packs_button);
        removePacksBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO remove - n from pakkidest.
            }
        });

    }


    private void createAmountSpinner(){
        for(int i=1; i < 100; i++){
            amount.add(i);
        }
    }


}
