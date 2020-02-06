package com.testmaps.testmaps;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.TextView;
//import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


public class ViewPastEventsAsyncTask extends AsyncTask<String, Void, String> {

    private Context context;

    protected ViewPastEventsAsyncTask(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {

    }
    String vieweventss;

    @Override
    protected String doInBackground(String... params) {

        vieweventss = params[0];

        String link;

        try {

            link = "https://ianmunge.co.ke/tukioeventhandlers/viewpastevents.php";
            URL url = new URL(link);

            HttpURLConnection httpurlconnection = (HttpURLConnection)url.openConnection();
            httpurlconnection.setRequestMethod("POST");
            httpurlconnection.setDoOutput(true);
            httpurlconnection.setDoInput(true);
            OutputStream outputstream = httpurlconnection.getOutputStream();
            BufferedWriter bufferedwriter = new BufferedWriter(new OutputStreamWriter(outputstream, "UTF-8"));

            SharedPreferences sharedPref = context.getSharedPreferences("usernamepref", Context.MODE_PRIVATE);
            String usernameforviews = sharedPref.getString("prefkeyforusername", "");
            String posteventdata = URLEncoder.encode("viewevents", "UTF-8")+"="+URLEncoder.encode(vieweventss, "UTF-8")+"&"+
                    URLEncoder.encode("usernameforviewsphp", "UTF-8")+"="+URLEncoder.encode(usernameforviews, "UTF-8");

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

            return "";

        }

    }

    String[] query_result;
    JSONObject[] jsonObj;
    String query_result1;

    protected void onPostExecute(String result)
    {
        if (result != null) {

            Object json = new Object();
            try{
                json = new JSONTokener(result).nextValue();
            }
            catch (JSONException e0){

            }


            if (json instanceof JSONObject){
                try{
                    JSONObject jsonObj1 = new JSONObject(result);
                    query_result1 = jsonObj1.getString("query_result");

                    if (query_result1.equals("NOEVENTS"))
                    {
                        //String vieweventsfromviewpast = "current";
                        //new ViewCurrentEventsAsyncTask(context).execute(vieweventsfromviewpast);
                    }

                }
                catch (JSONException e){
                    e.getMessage();
                }

            }

            else if (json instanceof JSONArray){
                try {

                    JSONArray jsonArray = new JSONArray(result);

                    SharedPreferences sharedPref = context.getSharedPreferences("usernamepref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();


                    if(jsonArray != null)
                    {
                        int endloop = jsonArray.length()-1;
                        for(int i=0; i<=endloop; i++)
                        {
                            query_result = new String[jsonArray.length()];
                            query_result[i] = jsonArray.getString(i);
                            jsonObj = new JSONObject[jsonArray.length()];
                            jsonObj[i] = new JSONObject(query_result[i]);
                            jsonObj[i].getString("title");

                            String stringi = Integer.toString(i);

                            editor.putString("pastid"+stringi, jsonObj[i].getString("id"));
                            editor.putString("pasttitle"+stringi, jsonObj[i].getString("title"));
                            editor.putString("pastdescription"+stringi, jsonObj[i].getString("description"));
                            editor.putString("pastdate"+stringi, jsonObj[i].getString("startdate_yyyy_mm_dd"));
                            editor.putString("pasttime"+stringi, jsonObj[i].getString("starttime"));
                            editor.putString("pastcreator"+stringi, jsonObj[i].getString("creator"));
                            editor.putString("pastaswho"+stringi, jsonObj[i].getString("aswho"));
                            editor.putString("pastattendants"+stringi, jsonObj[i].getString("attendants"));
                            editor.putString("pastvenue"+stringi, jsonObj[i].getString("venue"));
                            editor.commit();
                            if(i==endloop)
                            {
                                editor.putInt("noofpastevents", jsonArray.length());
                                editor.commit();

                            }

                        }

                    }


                    //String vieweventsfromviewpast = "current";
                    //new ViewCurrentEventsAsyncTask(context).execute(vieweventsfromviewpast);

                } catch (JSONException e) {
                    //e.printStackTrace();
                    Toast.makeText(context, "async: "+ e.getMessage(), Toast.LENGTH_LONG).show();//"Error parsing JSON data :- "+query_result1
                }

            }

        } else {
            Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
        }

    }

}

