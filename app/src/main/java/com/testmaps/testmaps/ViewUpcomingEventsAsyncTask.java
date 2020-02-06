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


public class ViewUpcomingEventsAsyncTask extends AsyncTask<String, Void, String> {

    private Context context;

    public ViewUpcomingEventsAsyncTask(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {

    }
    String vieweventss;

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

            link = "https://ianmunge.co.ke/tukioeventhandlers/viewupcomingevents.php";// + data; //exact host url
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
    /*String query_result2;*/

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
                        //Intent startviewpage = new Intent(context.getApplicationContext(), EventsTab.class);
                        //startviewpage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //context.startActivity(startviewpage);
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
                        for(int r=0; r<=endloop; r++)
                        {
                            query_result = new String[jsonArray.length()];
                            query_result[r] = jsonArray.getString(r);
                            jsonObj = new JSONObject[jsonArray.length()];
                            jsonObj[r] = new JSONObject(query_result[r]);
                            jsonObj[r].getString("title");

                            String stringr = Integer.toString(r);
                            
                            editor.putString("upcomingid"+stringr, jsonObj[r].getString("id"));
                            editor.putString("upcomingtitle"+stringr, jsonObj[r].getString("title"));
                            editor.putString("upcomingdescription"+stringr, jsonObj[r].getString("description"));
                            editor.putString("upcomingdate"+stringr, jsonObj[r].getString("startdate_yyyy_mm_dd"));
                            editor.putString("upcomingtime"+stringr, jsonObj[r].getString("starttime"));
                            editor.putString("upcomingcreator"+stringr, jsonObj[r].getString("creator"));
                            editor.putString("upcomingaswho"+stringr, jsonObj[r].getString("aswho"));
                            editor.putString("upcomingattendants"+stringr, jsonObj[r].getString("attendants"));
                            editor.putString("upcomingvenue"+stringr, jsonObj[r].getString("venue"));
                            editor.commit();
                            if(r==endloop)
                            {
                                editor.putInt("noofupcomingevents", jsonArray.length());
                                editor.commit();
                                int noofeventsretrieved = sharedPref.getInt("noofupcomingevents", 0);

                            }

                        }

                    }

                    //Intent startviewpage = new Intent(context.getApplicationContext(), EventsTab.class);
                    //startviewpage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //context.startActivity(startviewpage);

                } catch (JSONException e) {
                    //e.printStackTrace();
                    Toast.makeText(context, "async3: "+ e.getMessage(), Toast.LENGTH_LONG).show();//"Error parsing JSON data :- "+query_result1
                }

            }

        } else {
            Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
        }

    }

}
