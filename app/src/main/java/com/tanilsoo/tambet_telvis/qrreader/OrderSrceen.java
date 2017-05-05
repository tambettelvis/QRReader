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
 * Created by Tambet-Telvis on 20.04.2017.
 * Contains activity, that swiches content view to list of jobs.
 */

public class OrderSrceen extends Activity {

    public static List<String> orders = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);


        ListAdapter listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, orders);
        ListView workList = (ListView)findViewById(R.id.work_order);
        workList.setAdapter(listAdapter);
        workList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //MainActivity.currentEmployee = String.valueOf(parent.getItemAtPosition(position));
                Intent intent = getIntent();
                String qrId = intent.getStringExtra("qr_id");
                Log.d("ONITEMCLICK", String.valueOf(parent.getItemAtPosition(position)).split("\\.")[0]);
                new RequestInfo(null).execute("senddata.php", "send", qrId, "4", MainActivity.currentEmployee, String.valueOf(parent.getItemAtPosition(position)).split("\\.")[0]);
                setResult(RESULT_OK);
                finish();
            }
        });

    }
}
