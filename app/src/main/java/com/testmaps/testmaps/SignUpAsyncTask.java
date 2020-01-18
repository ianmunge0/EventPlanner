package com.testmaps.testmaps;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
//import android.view.View;
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


public class SignUpAsyncTask extends AsyncTask<String, Void, String> {

    private Context context;

    public SignUpAsyncTask(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... params) {

        String newusername = params[0];
        String newemail = params[1];
        String newpassword = params[2];
        SharedPreferences sharedPref = context.getSharedPreferences("usernamepref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("prefkeyforusername", newusername);
        editor.commit();

        String link;
        //String data;
        //BufferedReader bufferedReader;
        //String result;

        try {
//            data = "?username=" + URLEncoder.encode(username, "UTF-8");
//            data += "&password=" + URLEncoder.encode(passWord, "UTF-8");

            link = "https://ianmunge.co.ke/tukioeventhandlers/signup.php";// + data; //exact host url
            URL url = new URL(link);

            HttpURLConnection httpurlconnection = (HttpURLConnection)url.openConnection();
            httpurlconnection.setRequestMethod("POST");
            httpurlconnection.setDoOutput(true);
            httpurlconnection.setDoInput(true);
            OutputStream outputstream = httpurlconnection.getOutputStream();
            BufferedWriter bufferedwriter = new BufferedWriter(new OutputStreamWriter(outputstream, "UTF-8"));
            String posteventdata = URLEncoder.encode("newusernamekey", "UTF-8")+"="+URLEncoder.encode(newusername, "UTF-8")+"&"+
                    URLEncoder.encode("newemailkey", "UTF-8")+"="+URLEncoder.encode(newemail, "UTF-8")+"&"+
                    URLEncoder.encode("newpasswordkey", "UTF-8")+"="+URLEncoder.encode(newpassword, "UTF-8");

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

    String query_result1;

    @Override
    protected void onPostExecute(String result) {
        String jsonStr1 = result;

        if (jsonStr1 != null) {
            try {
                //JSONObject jsonObj1 = new JSONObject().getJSONObject(jsonStr);
                JSONObject jsonObj1 = new JSONObject(jsonStr1);
                query_result1 = jsonObj1.getString("query_result");
                if (query_result1.equals("SUCCESS")) {
                    Toast.makeText(context, "Welcome", Toast.LENGTH_SHORT).show();
                    //Intent starthomepage = new Intent(context.getApplicationContext(), Home.class);
                    //Intent starthomepage = new Intent(context.getApplicationContext(), EventsTabs.class);
                    Intent starthomepage = new Intent(context.getApplicationContext(), Home.class);
                    context.startActivity(starthomepage);

                } else if (query_result1.equals("EXISTS")) {
                    Toast.makeText(context, "User already exist", Toast.LENGTH_SHORT).show();
                } else if(query_result1.equals("EMPTY")) {
                    Toast.makeText(context, "Fill in all values.", Toast.LENGTH_SHORT).show();
                } else if(query_result1.equals("ERROR")) {
                    Toast.makeText(context, "Error in logging in.", Toast.LENGTH_SHORT).show();
                }
                else if(query_result1.equals("INVALID")) {
                    Toast.makeText(context, "Enter a valid Email address", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(context, "Couldn't connect to remote database.", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Toast.makeText(context, query_result1+": "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();//"Error parsing JSON data :- "+query_result1
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

