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


public class ListForDeleteEventsAsyncTask extends AsyncTask<String, Void, String> {

    private Context context;

    public ListForDeleteEventsAsyncTask(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {

    }
    String deleteeventss;
    //String usernameforviews;
    //String passwordforviews;
    @Override
    protected String doInBackground(String... params) {
        //String result = MainActivity.doInBackgroundForViewEvents(context, vieweventss, params[0]);
        //return result;


        //String past = params[0];
        deleteeventss = params[0];
        //usernameforviews = params[1];
        //passwordforviews = params[2];

        String link;
        //String data;
        //BufferedReader bufferedReader;
        //String result;

        try {
//            data = "?username=" + URLEncoder.encode(username, "UTF-8");
//            data += "&password=" + URLEncoder.encode(passWord, "UTF-8");

            String linkk = "https://ianmunge.co.ke/tukioeventhandlers/viewevents.php";// + data; //exact host url
            link = "https://ianmunge.co.ke/tukioeventhandlers/listfordeleteevents.php";
            URL url = new URL(link);

            HttpURLConnection httpurlconnection = (HttpURLConnection)url.openConnection();
            httpurlconnection.setRequestMethod("POST");
            httpurlconnection.setDoOutput(true);
            httpurlconnection.setDoInput(true);
            OutputStream outputstream = httpurlconnection.getOutputStream();
            BufferedWriter bufferedwriter = new BufferedWriter(new OutputStreamWriter(outputstream, "UTF-8"));
            //String posteventdata = URLEncoder.encode("viewevents", "UTF-8")+"="+URLEncoder.encode(vieweventss, "UTF-8");

            SharedPreferences sharedPref = context.getSharedPreferences("usernamepref", Context.MODE_PRIVATE);
            String usernamefordelete = sharedPref.getString("prefkeyforusername", "");
        	/*String posteventdata = URLEncoder.encode("viewevents", "UTF-8")+"="+URLEncoder.encode(vieweventss, "UTF-8")+"&"+
        			URLEncoder.encode("usernameforviewsphp", "UTF-8")+"="+URLEncoder.encode(usernameforviews, "UTF-8")+"&"+
        			URLEncoder.encode("passwordforviewsphp", "UTF-8")+"="+URLEncoder.encode(passwordforviews, "UTF-8");*/
            String posteventdata = URLEncoder.encode("deleteevents", "UTF-8")+"="+URLEncoder.encode(deleteeventss, "UTF-8")+"&"+
                    URLEncoder.encode("usernamefordeletephp", "UTF-8")+"="+URLEncoder.encode(usernamefordelete, "UTF-8");

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
            //String[] result2 = {"result2", "resulT2"};
            //return result2;
            //return "";
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
                //JSONObject jsonObj1 = new JSONObject().getJSONObject(jsonStr);
                //JSONObject jsonObj = new JSONObject(jsonStr);
                //query_result1 = jsonObj.getString("query_result");

                JSONArray jsonArray = new JSONArray(jsonStr);
                query_result1 = jsonArray.getString(0);

//            	vieweventss.setText(query_result1);


                /*else if (query_result1.equals("FAILURE")) {
                    Toast.makeText(context, "User does not exist", Toast.LENGTH_SHORT).show();
                } else if(query_result1.equals("ERROR")) {
                	Toast.makeText(context, "Error in logging in.", Toast.LENGTH_SHORT).show();
                }*/
                if(query_result1.equals("EMPTY VALUES"))
                {
                    Toast.makeText(context, query_result1, Toast.LENGTH_SHORT).show();
                }
                /*else {
                    Toast.makeText(context, "Couldn't connect to remote database.", Toast.LENGTH_SHORT).show();
                }*/
                /*else if (query_result1.equals("SUCCESS")) {
                    //Toast.makeText(context, "Data inserted successfully. Signup successful.", Toast.LENGTH_SHORT).show();
                	Toast.makeText(context, query_result1, Toast.LENGTH_SHORT).show();
                	//Intent starthomepage = new Intent(context.getApplicationContext(), Home.class);

                	//Intent starthomepage = new Intent(context.getApplicationContext(), Home.class);
                	//context.startActivity(starthomepage);
                	//Toast.makeText(context, query_result1+"!", Toast.LENGTH_SHORT).show();
//                	vieweventss.setText(result);
                	 //queryresult2 = result;

                } */
                else
                {
                    SharedPreferences sharedPref = context.getSharedPreferences("usernamepref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    /*editor.putString("prefkeyforviewevents", query_result1);
                    editor.commit();*/

                    ArrayList<String> list = new ArrayList<String>();

                    if(jsonArray != null)
                    {
                        int len = jsonArray.length();
                        for (int i=0;i<len;i++)
                        {
                            list.add(jsonArray.get(i).toString());
                        }
                        String[] newlist = list.toArray(new String[list.size()]);
                       /*StringBuilder sb = new StringBuilder();
                       for(int i = 0; i < newlist.length; i++)
                       {
                           sb.append(newlist[i]).append(",");
                       }
                       editor.putString("prefkeyforviewevents", sb.toString());*/
                        editor.putString("prefkeyfordeleteevents", jsonArray.toString());
                        editor.commit();
                    }

                    Intent startviewpage = new Intent(context.getApplicationContext(), DeleteEvents.class);
                    startviewpage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(startviewpage);

                    //ViewEvents.displayEventsFromAsyncTask(context, query_result1);

                }
            } catch (JSONException e) {
                //e.printStackTrace();
                Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();//"Error parsing JSON data :- "+query_result1
            }
        } else {
            Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
        }
        //String []
        //return result;
        //return "";
    }





//    public void startanactivity(View view)
//	{
//		Intent starthomepage = new Intent(context.getApplicationContext(), Home.class);
//    	context.startActivity(starthomepage);
//    	//startActivity(new Intent(context.getApplicationContext(), Home.class));
//	}


}

