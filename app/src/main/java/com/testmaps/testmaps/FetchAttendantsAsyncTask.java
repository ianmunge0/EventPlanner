package com.testmaps.testmaps;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
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
import java.util.ArrayList;


public class FetchAttendantsAsyncTask extends AsyncTask<String, Void, String> {

    private Context context;

    public FetchAttendantsAsyncTask(Context context) {
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


            link = "https://ianmunge.co.ke/tukioeventhandlers/fetchattendants.php";
            URL url = new URL(link);

            HttpURLConnection httpurlconnection = (HttpURLConnection)url.openConnection();
            httpurlconnection.setRequestMethod("POST");
            httpurlconnection.setDoOutput(true);
            httpurlconnection.setDoInput(true);
            OutputStream outputstream = httpurlconnection.getOutputStream();
            BufferedWriter bufferedwriter = new BufferedWriter(new OutputStreamWriter(outputstream, "UTF-8"));


            SharedPreferences sharedPref = context.getSharedPreferences("usernamepref", Context.MODE_PRIVATE);
            String usernameforviews = sharedPref.getString("prefkeyforusername", "");

            String evntid = sharedPref.getString("eventIdForViewIndiv", "zer0");
            String posteventdata = URLEncoder.encode("eventidforcheckcheckers", "UTF-8")+"="+URLEncoder.encode(evntid, "UTF-8")+"&"+
                    URLEncoder.encode("viewevents", "UTF-8")+"="+URLEncoder.encode(vieweventss, "UTF-8")+"&"+
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

            return new String("Exception: " + e.getMessage());
        }

    }

    String query_result1;
    String queryresult2;

    protected void onPostExecute(String result)
    {
        String jsonStr = result;
        if (jsonStr != null) {
            try {

                JSONArray jsonArray = new JSONArray(jsonStr);
                /*query_result1 = jsonArray.getString(0);


                if(query_result1.equals("EMPTY VALUES"))
                {
                    Toast.makeText(context, query_result1, Toast.LENGTH_SHORT).show();
                }

                else if (query_result1.equals("NOEVENTS"))
                {
                    Toast.makeText(context, "No Events Created", Toast.LENGTH_SHORT).show();

                }
                else
                {*/
                    SharedPreferences sharedPref = context.getSharedPreferences("usernamepref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();

                    if(jsonArray != null && vieweventss == "")
                    {
                        String[] query_result;
                        JSONObject[] jsonObj;

                        int endloop = jsonArray.length()-1;
                        for(int i=0; i<=endloop; i++)
                        {
                            query_result = new String[jsonArray.length()];
                            query_result[i] = jsonArray.getString(i);
                            jsonObj = new JSONObject[jsonArray.length()];
                            jsonObj[i] = new JSONObject(query_result[i]);
                            editor.putString("username"+i, jsonObj[i].getString("username"));
                            editor.putString("ifcheckedin"+i, jsonObj[i].getString("ifcheckedin"));
                            if(i==endloop)
                            {
                                editor.putInt("noofattendants", jsonArray.length());
                                editor.commit();

                            }
                        }


                        Intent startviewpage = new Intent(context, ViewIndividualEventActivity.class);
                        startviewpage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(startviewpage);
                    }

                    else if(vieweventss=="past") {
                        ArrayList<String> list = new ArrayList<String>();

                        if (jsonArray != null) {
                            int len = jsonArray.length();
                            for (int i = 0; i < len; i++) {
                                list.add(jsonArray.get(i).toString());
                            }
                            String[] newlist = list.toArray(new String[list.size()]);

                            editor.putString("prefkeyforattendants", jsonArray.toString());
                            editor.commit();
                        }

                        Intent startviewpage = new Intent(context.getApplicationContext(), CreateEvent.class);

                        startviewpage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(startviewpage);


                    }

               // } closing elseblock
            } catch (JSONException e) {

                Toast.makeText(context, "fetch attendants aisinc"+e.getLocalizedMessage(), Toast.LENGTH_LONG).show();//"Error parsing JSON data :- "+query_result1
            }
        } else {
            Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
        }

    }








}

