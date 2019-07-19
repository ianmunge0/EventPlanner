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

            link = "http://172.28.0.1/tukioeventhandlers/viewcurrentevents.php";// + data; //exact host url
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
    /*String query_result1;
    String query_result2;*/

    protected void onPostExecute(String result)
    {
        String jsonStr = result;
        if (jsonStr != null) {
            try {
                //JSONObject jsonObj1 = new JSONObject().getJSONObject(jsonStr);
                //JSONObject jsonObj = new JSONObject(jsonStr);
                //query_result1 = jsonObj.getString("query_result");
                JSONArray jsonArray = new JSONArray(jsonStr);

            	/*JSONArray jsonArray0 = new JSONArray(jsonStr[0]);
                query_result0 = jsonArray0.getString(0);

                JSONArray jsonArray1 = new JSONArray(jsonStr[1]);
                query_result1 = jsonArray1.getString(0);

                JSONArray jsonArray2 = new JSONArray(jsonStr[2]);
                query_result2 = jsonArray2.getString(0);*/

//            	vieweventss.setText(query_result1);


                /*else if (query_result1.equals("FAILURE")) {
                    Toast.makeText(context, "User does not exist", Toast.LENGTH_SHORT).show();
                } else if(query_result1.equals("ERROR")) {
                	Toast.makeText(context, "Error in logging in.", Toast.LENGTH_SHORT).show();
                }*/
                /*if(query_result0.equals("EMPTY VALUES"))
                {
                	Toast.makeText(context, query_result0, Toast.LENGTH_SHORT).show();
                }*/
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
                /*else if (query_result0.equals("NOEVENTS"))
                {
                	Toast.makeText(context, "No Events Created", Toast.LENGTH_SHORT).show();
                	/*TextView thisText = new TextView(context);
                	thisText.setText("No events created.");*/
                /*}*/

                SharedPreferences sharedPref = context.getSharedPreferences("usernamepref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                    /*editor.putString("prefkeyforviewevents", query_result1);
                    editor.commit();*/

                //ArrayList<String> list = new ArrayList<String>();

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
                        editor.putString("currentattendants"+i, jsonObj[i].getString("attendants"));
                        editor.putString("currentvenue"+i, jsonObj[i].getString("venue"));
                        editor.commit();
                        if(i==endloop)
                        {
                            editor.putInt("noofcurrentevents", jsonArray.length());
                            editor.commit();

                        }
                    }
                       /*int len = jsonArray.length();
                       for (int i=0;i<len;i++)
                       {
                    	   list.add(jsonArray.get(i).toString());
                       }
                       String[] newlist = list.toArray(new String[list.size()]);*/
                       /*StringBuilder sb = new StringBuilder();
                       for(int i = 0; i < newlist.length; i++)
                       {
                           sb.append(newlist[i]).append(",");
                       }
                       editor.putString("prefkeyforviewevents", sb.toString());*/
                    //editor.putString("prefkey1forviewevents", jsonArray.toString());
                    //editor.commit();
                }
                    /*if(jsonArray1 != null)
                    {
                    	int len = jsonArray1.length();
                        for (int i=0;i<len;i++)
                        {
                     	   list.add(jsonArray1.get(i).toString());
                        }
                        String[] newlist = list.toArray(new String[list.size()]);
                        /*StringBuilder sb = new StringBuilder();
                        for(int i = 0; i < newlist.length; i++)
                        {
                            sb.append(newlist[i]).append(",");
                        }
                        editor.putString("prefkeyforviewevents", sb.toString());*/

                    /*    editor.putString("prefkey1forviewevents", jsonArray1.toString());
                        editor.commit();
                    }*/
                    /*if(jsonArray2 != null)
                    {
                    	int len = jsonArray2.length();
                        for (int i=0;i<len;i++)
                        {
                     	   list.add(jsonArray2.get(i).toString());
                        }
                        String[] newlist = list.toArray(new String[list.size()]);
                        /*StringBuilder sb = new StringBuilder();
                        for(int i = 0; i < newlist.length; i++)
                        {
                            sb.append(newlist[i]).append(",");
                        }
                        editor.putString("prefkeyforviewevents", sb.toString());*/

                     /*   editor.putString("prefkey2forviewevents", jsonArray2.toString());
                        editor.commit();
                    }*/

                //Intent startviewpage = new Intent(context.getApplicationContext(), ViewEvents.class);

                    /*Intent startviewpage = new Intent(context.getApplicationContext(), EventsTab1.class);
                    //startviewpage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startviewpage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(startviewpage);*/
                String vieweventss = "upcoming";
                new ViewUpcomingEventsAsyncTask(context).execute(vieweventss);

                //ViewEvents.displayEventsFromAsyncTask(context, query_result1);


            } catch (JSONException e) {
                //e.printStackTrace();
                Toast.makeText(context, "async2"+ e.getLocalizedMessage(), Toast.LENGTH_LONG).show();//"Error parsing JSON data :- "+query_result1
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

