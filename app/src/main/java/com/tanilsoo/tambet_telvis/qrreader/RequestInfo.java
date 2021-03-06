package com.tanilsoo.tambet_telvis.qrreader;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tambe on 06.03.2017.
 */

public class RequestInfo extends AsyncTask<String, String, String> {

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    public static final String URL_IP = "http://www.medesteetika.ee/";

    private boolean connected = false;

    ProgressDialog pdLoading;
    HttpURLConnection conn;
    URL url = null;

    MainActivity mainActivity;

    private String fileName = "";

    public RequestInfo(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        //this method will be running on UI thread


    }

    @Override//Params: 0:file 1:requestype 'send' 'recive'. If send 4. 5. 6. data.
    protected String doInBackground(String... params) {
        try{
            url = new URL(URL_IP + params[0]);
            fileName = params[0];
        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        try{
            conn = (HttpURLConnection)url.openConnection();
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setConnectTimeout(CONNECTION_TIMEOUT);
            conn.setRequestMethod("POST");

            if(params[1].equals("send")) {
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                String data = URLEncoder.encode("qr", "UTF-8") + "="
                        + URLEncoder.encode(params[2], "UTF-8") + "&" + URLEncoder.encode("action", "UTF-8") + "="
                        + URLEncoder.encode(params[3], "UTF-8") + "&" + URLEncoder.encode("employee", "UTF-8") + "=" +
                            URLEncoder.encode(params[4], "UTF-8");
                if(params.length == 6) {
                    data += "&" + URLEncoder.encode("order_id", "UTF-8") + "=" + URLEncoder.encode(params[5], "UTF-8");
                }
                Log.d("RequestInfo", data);

                wr.write(data);
                wr.flush();

                Log.d("post response code", conn.getResponseCode() + " ");

            } else {
                conn.setDoInput(true);
            }
            conn.connect();

        } catch (IOException e){
            e.printStackTrace();
        }


        try {
            int responseCode = conn.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                InputStream input = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                StringBuilder result = new StringBuilder();
                String line;

                while((line = reader.readLine()) != null){
                    result.append(line);
                }

                return result.toString();
            } else {
                return "unsuccessful";
            }

        } catch (IOException e){
            e.printStackTrace();
            return e.getMessage();
        } finally {
            conn.disconnect();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (fileName.equals("request.php")) {
            List<String> list = new ArrayList<String>();
            String[] dataSplit = result.split("\\|");

            for(String pakiInfo : dataSplit){
                String[] infod = pakiInfo.split(";");
                list.add(infod[0] + " " + infod[1] + " " + infod[2] + " " + infod[3]);
                Log.d("1", infod[0] + " " + infod[1] + " " + infod[2] + " " + infod[3]);
            }
            PrinterScreen.data = list;
        } else if(fileName.equals("request_employee.php")){
            List<String> list = new ArrayList<String>();
            String[] dataSplit = result.split("\\|");
            for (String name : dataSplit){
                list.add(name);
            }
            LoginScreen.employees = list;
            //mainActivity.changeToLoginLayout();
        } else if(fileName.equals("request_orders.php")){
            List<String> list = new ArrayList<String>();
            String[] dataSplit = result.split("\\|");
            for(String data : dataSplit){
                String[] a = data.split(",");
                if(!(a.length < 4)) {
                    String s = a[1] + ". " + a[2] + " - " + a[0] + " " + a[3];
                    list.add(s);
                }
            }
            OrderSrceen.orders = list;
        } else if(fileName.equals("request_posts.php")){
            List<String> postInfo =  new ArrayList<String>();
            String[] dataSplit = result.split("\\|");
            for(String data : dataSplit){
                postInfo.add(data);
            }
            RemovePacks.postTypes = postInfo;
        } else if(fileName.equals("request_jobs.php")){
            Map<Integer, String> map = new LinkedHashMap<>();
            String[] dataSplit = result.split("\\|");
            for(String data : dataSplit){
                String[] split = data.split(";");
                if(!(split.length < 2)) {
                    map.put(Integer.parseInt(split[1]), split[0]);
                }
            }
            WorkScreen.jobs = map;
        }
    }

    public boolean isConnected(){
        return connected;
    }
}
