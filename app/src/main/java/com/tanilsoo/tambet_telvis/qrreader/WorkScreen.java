package com.tanilsoo.tambet_telvis.qrreader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tambet-Telvis on 03.05.2017.
 */

public class WorkScreen extends Activity{

    public static List<String> works = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        ListAdapter listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, works);
        ListView workList = (ListView)findViewById(R.id.work_list);
        workList.setAdapter(listAdapter);



    }
}
