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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tambet-Telvis on 03.05.2017.
 */

public class WorkScreen extends Activity{

    public static Map<Integer, String> jobs = new LinkedHashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        List<String> jobsList = new ArrayList<>();
        Log.d("LIST", jobs.size() + " ======");
        for(Map.Entry entry : jobs.entrySet()){
            jobsList.add(entry.getKey() + ". " + entry.getValue());
            Log.d("LIST", entry.getKey() + ". " + entry.getValue());
        }

        ListAdapter listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, jobsList);
        ListView workList = (ListView)findViewById(R.id.work_list);
        workList.setAdapter(listAdapter);



    }
}
