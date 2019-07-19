package com.testmaps.testmaps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;
//import android.view.View;
import android.widget.Toast;
import android.content.SharedPreferences;
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

public class LogInActivity extends AsyncTask<String, Void, String> {

    private Context context;
    public LogInActivity(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {

    }
    String username;
    String passWord;

    @Override
    protected String doInBackground(String... params) {

        username = params[0];
        passWord = params[1];
        SharedPreferences sharedPref = context.getSharedPreferences("usernamepref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("prefkeyforusername", username);
        editor.commit();

        String link;
        //String data;
        //BufferedReader bufferedReader;
        //String result;

        try {
//            data = "?username=" + URLEncoder.encode(username, "UTF-8");
//            data += "&password=" + URLEncoder.encode(passWord, "UTF-8");

            link = "http://172.28.0.1/tukioeventhandlers/login.php";// + data; //exact host url
            URL url = new URL(link);

            HttpURLConnection httpurlconnection = (HttpURLConnection)url.openConnection();
            httpurlconnection.setRequestMethod("POST");
            httpurlconnection.setDoOutput(true);
            httpurlconnection.setDoInput(true);
            OutputStream outputstream = httpurlconnection.getOutputStream();
            BufferedWriter bufferedwriter = new BufferedWriter(new OutputStreamWriter(outputstream, "UTF-8"));
            String posteventdata = URLEncoder.encode("userNamekey", "UTF-8")+"="+URLEncoder.encode(username, "UTF-8")+"&"+
                    URLEncoder.encode("passwordkey", "UTF-8")+"="+URLEncoder.encode(passWord, "UTF-8");

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

//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//
//            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
//            result = bufferedReader.readLine();
//            return result;
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
    }

    String query_result;

    @Override
    protected void onPostExecute(String result) {
        String jsonStr = result;

        if (jsonStr != null) {
            try {
                //JSONObject jsonObj1 = new JSONObject().getJSONObject(jsonStr);
                JSONObject jsonObj = new JSONObject(jsonStr);
                query_result = jsonObj.getString("query_result");
                if (query_result.equals("SUCCESS")) {
                    //Toast.makeText(context, query_result, Toast.LENGTH_SHORT).show();
                    Intent starthommepage = new Intent(context.getApplicationContext(), Home.class);

                    //Intent starthomepage = new Intent(context.getApplicationContext(), Home.class);
                    //String strName = null;
                    //starthomepage.putExtra("stringineed", strName);
                    //starthommepage.putExtra("usernameforhome", username);
                    //starthommepage.putExtra("passwordforhome", passWord);
                    starthommepage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(starthommepage);

                    //LogInActivity.this.cancel(true);


                    //context.startActivity(new Intent(context.getApplicationContext(), Home.class));
                    //new Intent(context.getApplicationContext(), Home.class);

                    //MainActivity.asyncTaskDataRecieved(context, query_result);


                } else if (query_result.equals("FAILURE")) {
                    Toast.makeText(context, "User does not exist", Toast.LENGTH_SHORT).show();
                } else if(query_result.equals("ERROR")) {
                    Toast.makeText(context, "Error in logging in.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(context, "Couldn't connect to remote database.", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, "No Network", Toast.LENGTH_SHORT).show();//"Error parsing JSON data :- "+query_result1
            }
        } else {
            Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
        }
    }
//    public void startanactivity(View view)
//	{
//		Intent starthomepage = new Intent(context.getApplicationContext(), Home.class);
//    	context.startActivity(starthomepage);
//    	//startActivity(new Intent(context.getApplicationContext(), Home.class));
//	}




}

