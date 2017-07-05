package com.tanilsoo.tambet_telvis.qrreader;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    public static String currentEmployee = "DEFAULT";

    private Button laduBtn;
    private Button immutusBtn;
    private Button laadmineBtn;
    private int scanMode = 0; //0:Laud, 1:Immutus, 2:Laadimine

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            //Result from scan of camera.
            case (49374): {
                IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

                if (result != null) {
                    if (result.getContents() == null) {
                        Toast.makeText(this, "Canceled SCanning", Toast.LENGTH_SHORT).show();
                    } else {


                        String qrId = result.getContents().split(";")[result.getContents().split(";").length-1];
                        Toast.makeText(this, qrId, Toast.LENGTH_SHORT).show();

                        if(scanMode == 0) {
                            Log.d("SCANMODE", "SIIN: " + qrId.trim());
                            new RequestInfo(this).execute("senddata.php", "send", qrId, "2", currentEmployee);
                        } else if(scanMode == 1){
                            new RequestInfo(this).execute("senddata.php", "send", qrId, "3", currentEmployee);
                        } else if(scanMode == 2){

                            Intent orderScreenIntent = new Intent(this, OrderSrceen.class);
                            orderScreenIntent.putExtra("qr_id", qrId);
                            final int request = 3;
                            startActivityForResult(orderScreenIntent, request);

                            //new RequestInfo(this).execute("senddata.php", "send", qrId, "4", currentEmployee);


                        }
                    }
                } else {
                    super.onActivityResult(requestCode, resultCode, data);
                }
            }
            break;

            //Result from printer screen
            case(1):
            {
                super.onActivityResult(requestCode, resultCode, data);
                if(resultCode != 0) {
                    new RequestInfo(this).execute("senddata.php", "send", data.getStringExtra("qr_id"), "1", currentEmployee);
                    Log.d("MainActivity", "Result 1");
                }

            }
            break;

            case(2):
            {
                TextView textView = (TextView) findViewById(R.id.employee_logged);
                textView.setText("Sisselogitud: " + currentEmployee);
            }
            break;

            case(3):
            {
                //TODO
            }
            break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        laduBtn = (Button)findViewById(R.id.lao_scan);
        immutusBtn = (Button)findViewById(R.id.immutus_scan);
        laadmineBtn = (Button)findViewById(R.id.laadimine_scan);

        final Activity activity = this;

        laduBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanMode = 0;
                initiateScan(activity);
            }
        });

        immutusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanMode = 1;
                initiateScan(activity);
            }
        });

        laadmineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanMode = 2;
                initiateScan(activity);
            }
        });

        //Get info from web.
        new RequestInfo(this).execute("request.php", "recive");
        new RequestInfo(this).execute("request_employee.php", "recive");
        new RequestInfo(this).execute("request_orders.php", "recive");
        new RequestInfo(this).execute("request_posts.php", "recive");
        new RequestInfo(this).execute("request_jobs.php", "recive");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.change_user){
            Intent getScreenIntent = new Intent(this, LoginScreen.class);
            final int result = 2;
            startActivityForResult(getScreenIntent, result);
            return true;
        } else if(id == R.id.go_work_screen){
            Intent getScreenInent = new Intent(this, WorkScreen.class);
            startActivity(getScreenInent);
            return true;
        } else if(id == R.id.go_remove_pack_screen){
            Intent getScreenInent = new Intent(this, RemovePacks.class);
            startActivity(getScreenInent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void changeToPrintLayout(View view){
        Intent getScreenIntent = new Intent(this, PrinterScreen.class);
        final int result = 1;
        startActivityForResult(getScreenIntent, result);

    }

    public void changeToLoginLayout(){
        Intent getScreenIntent = new Intent(this, LoginScreen.class);
        final int result = 2;
        startActivityForResult(getScreenIntent, result);
    }

    private void initiateScan(Activity activity){
        IntentIntegrator integrator = new IntentIntegrator(activity);

        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(false);

        integrator.initiateScan();
    }


}
