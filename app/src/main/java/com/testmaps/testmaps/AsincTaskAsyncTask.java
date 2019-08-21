package com.testmaps.testmaps;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class AsincTaskAsyncTask extends AsyncTask<String, String, String> {

    private Context context;

    public AsincTaskAsyncTask(Context context3) {
        this.context = context3;
    }

    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... params) {

        try {
            String lat = params[0];
            String lng = params[1];

            SharedPreferences sharedPref = context.getSharedPreferences("usernamepref", Context.MODE_PRIVATE);
            String usernamefornew1 = sharedPref.getString("prefkeyforusername", "");
            int keyforid_ = sharedPref.getInt("keyforindividual", -1);
            String strid_ = sharedPref.getString(sharedPref.getString("timecateg", "")+"id"+keyforid_, "");
            //String lat = sharedPref.getString("strttchk", params[0]);
            //String lng = sharedPref.getString("strntchk", params[0]);


            URL url = new URL("http://192.168.41.1/tukioeventhandlers/new1.php");
            HttpURLConnection httpurlconnection = (HttpURLConnection)url.openConnection();
            httpurlconnection.setRequestMethod("POST");
            httpurlconnection.setDoOutput(true);
            httpurlconnection.setDoInput(true);
            OutputStream outputstream = httpurlconnection.getOutputStream();
            BufferedWriter bufferedwriter = new BufferedWriter(new OutputStreamWriter(outputstream, "UTF-8"));
            String posteventdata =
                    URLEncoder.encode("lat", "UTF-8")+"="+URLEncoder.encode(lat, "UTF-8")+"&"+
                    URLEncoder.encode("lng", "UTF-8")+"="+URLEncoder.encode(lng, "UTF-8")+"&"+
                            URLEncoder.encode("id_", "UTF-8")+"="+URLEncoder.encode(strid_, "UTF-8")+"&"+
                    URLEncoder.encode("usernamefornew1", "UTF-8")+"="+URLEncoder.encode(usernamefornew1, "UTF-8");

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
                /*JSONObject jsonObj = new JSONObject(jsonStr);
                String a = jsonObj.getString("aa");
                String b = jsonObj.getString("bb");
                String c = jsonObj.getString("cc");
                String d = jsonObj.getString("dd");
                String e = jsonObj.getString("ee");
                Toast.makeText(context, a+", "+b+", "+c+", "+d+", "+e, Toast.LENGTH_LONG).show();*/

                JSONObject jsonObj = new JSONObject(jsonStr);
                query_result = jsonObj.getString("query_result");
                if (query_result.equals("SUCCESS")) {
                    Toast.makeText(context, "CHK", Toast.LENGTH_LONG).show();
                } else if (query_result.equals("FAILURE")) {
                    double lat = jsonObj.optDouble("aa");
                    double latv = jsonObj.optDouble("bbv", 181.0000);
                    double lng = jsonObj.optDouble("cc");
                    double lngv = jsonObj.optDouble("ddv", 182.0000);
                    Toast.makeText(context, "arvnt:\n"+lat+" > "+latv+"\n"+lng+" > "+lngv, Toast.LENGTH_LONG).show();
                } else if(query_result.equals("ERROR")) {
                    Toast.makeText(context, "Error in logging in.", Toast.LENGTH_SHORT).show();
                }

                else {
                    int i = jsonObj.getInt("query_result");
                    if(i==0){
                        SharedPreferences sharedPref = context.getSharedPreferences("usernamepref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putInt("timeb0", i);
                        editor.commit();

                        Intent viewindividual = new Intent(context, ViewIndividualEventActivity.class);
                        viewindividual.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(viewindividual);
                    }
                    else{
                        SharedPreferences sharedPref = context.getSharedPreferences("usernamepref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putInt("timeb", i);
                        editor.commit();

                        Intent viewindividual = new Intent(context, ViewIndividualEventActivity.class);
                        viewindividual.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(viewindividual);
                    }



                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, "aisinc:- "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
        }
    }


}

