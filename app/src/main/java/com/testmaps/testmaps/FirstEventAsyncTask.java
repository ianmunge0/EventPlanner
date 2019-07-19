package com.testmaps.testmaps;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


public class FirstEventAsyncTask extends AsyncTask<String, String, String> {

    private Context context;

    public FirstEventAsyncTask(Context context1) {
        this.context = context1;
    }

    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... params) {

        try {
            String timestamptoupdate = params[0];

            SharedPreferences sharedPref = context.getSharedPreferences("usernamepref", Context.MODE_PRIVATE);
            String usernameforchoose1event = sharedPref.getString("prefkeyforusername", "");

            String createevenifsametime = "hasalreadyhappened";
            URL url = new URL("http://172.28.0.1/tukioeventhandlers/setfirstevent.php");
            HttpURLConnection httpurlconnection = (HttpURLConnection)url.openConnection();
            httpurlconnection.setRequestMethod("POST");
            httpurlconnection.setDoOutput(true);
            httpurlconnection.setDoInput(true);
            OutputStream outputstream = httpurlconnection.getOutputStream();
            BufferedWriter bufferedwriter = new BufferedWriter(new OutputStreamWriter(outputstream, "UTF-8"));
            String posteventdata = URLEncoder.encode("timestamptoupdate", "UTF-8")+"="+URLEncoder.encode(timestamptoupdate, "UTF-8")+"&"+
                    URLEncoder.encode("createevenifsametime", "UTF-8")+"="+URLEncoder.encode(createevenifsametime, "UTF-8")+"&"+
                    URLEncoder.encode("usernameforupdate", "UTF-8")+"="+URLEncoder.encode(usernameforchoose1event, "UTF-8");

            bufferedwriter.write(posteventdata);
            bufferedwriter.flush();
            bufferedwriter.close();
            outputstream.close();

            InputStream inputstream = httpurlconnection.getInputStream();
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream, "iso-8859-1"));
            String result = "";
            String line = "";

            while((line = bufferedreader.readLine())!=null)
            {
                result += line;
            }
            bufferedreader.close();
            inputstream.close();
            httpurlconnection.disconnect();

            return result;


        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    String query_result;



    protected void onPostExecute(String result) {
        String jsonStr = result;

        if (jsonStr != null) {
            try {

                JSONObject jsonObj = new JSONObject(jsonStr);
                query_result = jsonObj.getString("query_result");
                if (query_result.equals("SUCCESS")) {
                    Toast.makeText(context, "First event to attend has been selected.", Toast.LENGTH_LONG).show();
                } else if (query_result.equals("FAILURE")) {
                    Toast.makeText(context, "Failed to create event", Toast.LENGTH_SHORT).show();
                } else if(query_result.equals("ERROR")) {
                    Toast.makeText(context, "Error in logging in.", Toast.LENGTH_SHORT).show();
                }

                else {
                    Toast.makeText(context, "Couldn't connect to remote database.", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, "Error parsing JSON data :- "+query_result, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
        }
    }


}

