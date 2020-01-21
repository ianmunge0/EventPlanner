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


public class ViewCurrentEventsAsyncTask extends AsyncTask<String, Void, String> {

    private Context context;

    public ViewCurrentEventsAsyncTask(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {

    }
    String vieweventss;
    //String usernameforviews;
    //String passwordforviews;
    @Override
    protected String doInBackground(String... params) {
        //String result = MainActivity.doInBackgroundForViewEvents(context, vieweventss, params[0]);
        //return result;


        //String past = params[0];
        vieweventss = params[0];
        //usernameforviews = params[1];
        //passwordforviews = params[2];

        String link;
        //String data;
        //BufferedReader bufferedReader;
        //String result;

        try {
//            data = "?username=" + URLEncoder.encode(username, "UTF-8");
//            data += "&password=" + URLEncoder.encode(passWord, "UTF-8");

            link = "https://ianmunge.co.ke/tukioeventhandlers/viewcurrentevents.php";// + data; //exact host url
            URL url = new URL(link);

            HttpURLConnection httpurlconnection = (HttpURLConnection)url.openConnection();
            httpurlconnection.setRequestMethod("POST");
            httpurlconnection.setDoOutput(true);
            httpurlconnection.setDoInput(true);
            OutputStream outputstream = httpurlconnection.getOutputStream();
            BufferedWriter bufferedwriter = new BufferedWriter(new OutputStreamWriter(outputstream, "UTF-8"));
            //String posteventdata = URLEncoder.encode("viewevents", "UTF-8")+"="+URLEncoder.encode(vieweventss, "UTF-8");

            SharedPreferences sharedPref = context.getSharedPreferences("usernamepref", Context.MODE_PRIVATE);
            String usernameforviews = sharedPref.getString("prefkeyforusername", "");
        	/*String posteventdata = URLEncoder.encode("viewevents", "UTF-8")+"="+URLEncoder.encode(vieweventss, "UTF-8")+"&"+
        			URLEncoder.encode("usernameforviewsphp", "UTF-8")+"="+URLEncoder.encode(usernameforviews, "UTF-8")+"&"+
        			URLEncoder.encode("passwordforviewsphp", "UTF-8")+"="+URLEncoder.encode(passwordforviews, "UTF-8");*/
            String posteventdata = URLEncoder.encode("viewevents", "UTF-8")+"="+URLEncoder.encode(vieweventss, "UTF-8")+"&"+
                    URLEncoder.encode("usernameforviewsphp", "UTF-8")+"="+URLEncoder.encode(usernameforviews, "UTF-8");

            bufferedwriter.write(posteventdata);
            bufferedwriter.flush();
            bufferedwriter.close();
            outputstream.close();

            InputStream inputstream = httpurlconnection.getInputStream();
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream, "iso-8859-1"));
            //LineNumberReader linenumberreader = new LineNumberReader(new InputStreamReader(inputstream, "iso-8859-1"));
            //String[] result = {};
            String result = "";
            String line = "";

            while((line = bufferedreader.readLine())!=null)
            {
                //result[linenumberreader.getLineNumber()] += line;
                result += line;
            }
            bufferedreader.close();
            inputstream.close();
            httpurlconnection.disconnect();

            return result;
            //return "";
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//
//            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
//            result = bufferedReader.readLine();
//            return result;
        } catch (Exception e) {
            //String[] result2 = {e.getLocalizedMessage(), e.getMessage(), e.getMessage()};
            //return result2;
            return "";
            //return new String("Exception: " + e.getMessage());
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
                        String vieweventss = "upcoming";
                        new ViewUpcomingEventsAsyncTask(context).execute(vieweventss);
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

                            editor.putString("currentid"+i, jsonObj[i].getString("id"));
                            editor.putString("currenttitle"+i, jsonObj[i].getString("title"));
                            editor.putString("currentdescription"+i, jsonObj[i].getString("description"));
                            editor.putString("currentdate"+i, jsonObj[i].getString("startdate_yyyy_mm_dd"));
                            editor.putString("currenttime"+i, jsonObj[i].getString("starttime"));
                            editor.putString("currentcreator"+i, jsonObj[i].getString("creator"));
                            editor.putString("currentaswho"+i, jsonObj[i].getString("aswho"));
                            editor.putString("currentattendants"+i, jsonObj[i].getString("attendants"));
                            editor.putString("currentvenue"+i, jsonObj[i].getString("venue"));
                            editor.commit();
                            if(i==endloop)
                            {
                                editor.putInt("noofcurrentevents", jsonArray.length());
                                editor.commit();
                            }
                        }

                    }

                    String vieweventss = "upcoming";
                    new ViewUpcomingEventsAsyncTask(context).execute(vieweventss);

                } catch (JSONException e) {
                    //e.printStackTrace();
                    Toast.makeText(context, "async2: "+ e.getMessage(), Toast.LENGTH_LONG).show();//"Error parsing JSON data :- "+query_result1
                }
            }
        }
        else {
            Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
        }

    }

}
