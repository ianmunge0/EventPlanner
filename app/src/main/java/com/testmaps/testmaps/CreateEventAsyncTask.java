package com.testmaps.testmaps;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;

import static android.content.Context.NOTIFICATION_SERVICE;


public class CreateEventAsyncTask extends AsyncTask<String, String, String> {

    private Context context;
    String startyearofevent;
    String startmonthofevent;
    String startdayofevent;
    String eventstarthour;
    String eventstartminute;
    String endyearofevent;
    String endmonthofevent;
    String enddayofevent;
    String eventendhour;
    String eventendminute;
    String eventtitle;
    String eventdescription;

    public CreateEventAsyncTask(Context context1) {
        this.context = context1;
        startyearofevent = null;
        startmonthofevent = null;
        startdayofevent = null;
        eventstarthour = null;
        eventstartminute = null;

        endyearofevent = null;
        endmonthofevent = null;
        enddayofevent = null;
        eventendhour = null;
        eventendminute = null;
    }

    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... params) {

        try {
            eventtitle = params[0];
            eventdescription = params[1];
            startdayofevent = params[2];
            startmonthofevent = params[3];
            startyearofevent = params[4];
            eventstarthour = params[5];
            eventstartminute = params[6];
            enddayofevent = params[7];
            endmonthofevent = params[8];
            endyearofevent = params[9];
            eventendhour = params[10];
            eventendminute = params[11];
            String attendants = params[12];

            SharedPreferences sharedPref = context.getSharedPreferences("usernamepref", Context.MODE_PRIVATE);
            String usernameforpostevent = sharedPref.getString("prefkeyforusername", "");
            String strrlatitude = sharedPref.getString("strrlatitude", "-1.2921");
            String strrlongitude = sharedPref.getString("strrlongitude", "36.8219");


            URL url = new URL("http://172.28.0.1/tukioeventhandlers/createevent.php");
            HttpURLConnection httpurlconnection = (HttpURLConnection)url.openConnection();
            httpurlconnection.setRequestMethod("POST");
            httpurlconnection.setDoOutput(true);
            httpurlconnection.setDoInput(true);
            OutputStream outputstream = httpurlconnection.getOutputStream();
            BufferedWriter bufferedwriter = new BufferedWriter(new OutputStreamWriter(outputstream, "UTF-8"));
            String posteventdata = URLEncoder.encode("eventtitlekey", "UTF-8")+"="+URLEncoder.encode(eventtitle, "UTF-8")+"&"+
                    URLEncoder.encode("eventdescriptionkey", "UTF-8")+"="+URLEncoder.encode(eventdescription, "UTF-8")+"&"+
                    URLEncoder.encode("startdayofeventkey", "UTF-8")+"="+URLEncoder.encode(startdayofevent, "UTF-8")+"&"+
                    URLEncoder.encode("startmonthofeventkey", "UTF-8")+"="+URLEncoder.encode(startmonthofevent, "UTF-8")+"&"+
                    URLEncoder.encode("startyearofeventkey", "UTF-8")+"="+URLEncoder.encode(startyearofevent, "UTF-8")+"&"+
                    URLEncoder.encode("eventstarthourkey", "UTF-8")+"="+URLEncoder.encode(eventstarthour, "UTF-8")+"&"+
                    URLEncoder.encode("eventstartminutekey", "UTF-8")+"="+URLEncoder.encode(eventstartminute, "UTF-8")+"&"+
                    URLEncoder.encode("enddayofeventkey", "UTF-8")+"="+URLEncoder.encode(enddayofevent, "UTF-8")+"&"+
                    URLEncoder.encode("endmonthofeventkey", "UTF-8")+"="+URLEncoder.encode(endmonthofevent, "UTF-8")+"&"+
                    URLEncoder.encode("endyearofeventkey", "UTF-8")+"="+URLEncoder.encode(endyearofevent, "UTF-8")+"&"+
                    URLEncoder.encode("eventendhourkey", "UTF-8")+"="+URLEncoder.encode(eventendhour, "UTF-8")+"&"+
                    URLEncoder.encode("eventendminutekey", "UTF-8")+"="+URLEncoder.encode(eventendminute, "UTF-8")+"&"+
                    URLEncoder.encode("attendantskey", "UTF-8")+"="+URLEncoder.encode(attendants, "UTF-8")+"&"+
                    URLEncoder.encode("strrlatitude", "UTF-8")+"="+URLEncoder.encode(strrlatitude, "UTF-8")+"&"+
                    URLEncoder.encode("strrlongitude", "UTF-8")+"="+URLEncoder.encode(strrlongitude, "UTF-8")+"&"+
                    URLEncoder.encode("usernamekey", "UTF-8")+"="+URLEncoder.encode(usernameforpostevent, "UTF-8");

            bufferedwriter.write(posteventdata);//check if long can be written separately
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

 /*   protected void onProgressUpdate(String result)
    {
    	String jsonStr = result;
    	if (jsonStr != null)
    	{
    		try
    		{
    			JSONObject jsonObj = new JSONObject(jsonStr);
    			query_result = jsonObj.getString("query_result");
                if (query_result.equals("TIMECLASH"))
                {

        				// TODO Auto-generated method stub
        				AlertDialog.Builder x = new AlertDialog.Builder(context);
        				x.setTitle("Time Clash");
        				x.setMessage("You are attending another event at the same time. Choose what to do with this event");
        				x.setCancelable(false);
        				x.setCancelable(false);
        				x.setPositiveButton("Create", new DialogInterface.OnClickListener() {

        					@Override
        					public void onClick(DialogInterface dialog, int which) {
        						// TODO Auto-generated method stub
        						//Toast.makeText(context, "Create clicked", Toast.LENGTH_SHORT).show();
        						dialog.cancel();
        					}
        				});
        				x.setNegativeButton("Don't Create", new DialogInterface.OnClickListener() {

        					@Override
        					public void onClick(DialogInterface dialog, int which) {
        						// TODO Auto-generated method stub
        						PostEvent.this.cancel(true);

        					}
        				});
        				x.create().show();





                }
    		}
    		catch(JSONException e)
    		{

    		}

    	}
    }*/

    protected void onPostExecute(String result) {
        String jsonStr = result;

        if (jsonStr != null) {
            try {
                //JSONObject jsonObj1 = new JSONObject().getJSONObject(jsonStr);
                JSONObject jsonObj = new JSONObject(jsonStr);
                query_result = jsonObj.getString("query_result");
                if (query_result.equals("SUCCESS")) {
                    Toast.makeText(context, "Event created successfully.", Toast.LENGTH_SHORT).show();
                    Date a = new Date(0000, 0, 0, 00, 00, 00);
                    Timer b = new Timer();
                    //b.schedule(new Reminder(context), a);
                    try
                    {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                        Date dateToChange = sdf.parse(startyearofevent+"-"+startmonthofevent+"-"+startdayofevent+"T"+eventstarthour+":"+eventstartminute);
                        long changedDate = dateToChange.getTime();
                        long notificTime = changedDate-1800000;
                        Date currentTime = new Date();
                        long currentTimeInMillis = currentTime.getTime();
                        long delayTime = notificTime - currentTimeInMillis;
                        if (notificTime > currentTimeInMillis){
                        //b.schedule(new Reminder(context), 10000);
                        b.schedule(new Reminder(context), delayTime);
                        SharedPreferences sharedPref = context.getSharedPreferences("usernamepref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("titlefornotific", eventtitle);
                        editor.putString("descriptionfornotific", eventdescription);
                        editor.commit();
                    }
                        //if(notificTime <= 0)
                        else
                        {
                            b.schedule(new Reminder(context), 2000);
                            SharedPreferences sharedPref = context.getSharedPreferences("usernamepref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("titlefornotific", eventtitle);
                            editor.putString("descriptionfornotific", eventdescription);
                            editor.commit();
                        }


                    }catch(ParseException e)
                    {
                        Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                } else if (query_result.equals("FAILURE")) {
                    Toast.makeText(context, "Failed to create event", Toast.LENGTH_SHORT).show();
                } else if(query_result.equals("ERROR")) {
                    Toast.makeText(context, "Error in logging in.", Toast.LENGTH_SHORT).show();
                }
                else if (query_result.equals("TIMECLASH"))
                {


                    // TODO Auto-generated method stub
                    AlertDialog.Builder x = new AlertDialog.Builder(context);
                    x.setTitle("Time Clash");
                    x.setMessage("You are attending another event at the same time. Choose what to do with this event");
                    x.setCancelable(false);
                    x.setCancelable(false);
                    x.setPositiveButton("Create", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            // TODO Auto-generated method stub
                            //AlertDialog.Builder _2 = new AlertDialog.Builder(context);
                            //_2.setMessage("Choose which one of them to attend first"); //if not in same location

                            //dialog.cancel();
                            try
                            {
                                SharedPreferences sharedPref = context.getSharedPreferences("usernamepref", Context.MODE_PRIVATE);
                                String usernameforupdateevent = sharedPref.getString("prefkeyforusername", "");
                                String createevenifsametime = "true";
                                new UpdateEventAsyncTask(context).execute(usernameforupdateevent, createevenifsametime);

                                //AsyncTask<String, Void, String> startupdate = new UpdateEventAsyncTask(context).execute(createevenifsametime);
                                //startupdate.execute(runnable);
                            }
                            catch(Exception e)
                            {
                                Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                    x.setNegativeButton("Don't Create", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            SharedPreferences sharedPref = context.getSharedPreferences("usernamepref", Context.MODE_PRIVATE);
                            String usernameforupdateevent = sharedPref.getString("prefkeyforusername", "");
                            String createevenifsametime = "false";
                            new UpdateEventAsyncTask(context).execute(usernameforupdateevent, createevenifsametime);
                            dialog.cancel();
                            //CreateEventAsyncTask.this.cancel(true);

                        }
                    });
                    x.create().show();
                }
                else if(query_result.equals("EARLIERDATE")){
                    Toast.makeText(context, "Starting Time cannot be earlier than now", Toast.LENGTH_SHORT).show();
                }
                else if(query_result.equals("EARLIERDATEFOREND")){
                    Toast.makeText(context, "Ending Time cannot be earlier than now", Toast.LENGTH_SHORT).show();
                }
                else if(query_result.equals("STARTEND")){
                    Toast.makeText(context, "Start time cannot be later than end time", Toast.LENGTH_SHORT).show();
                }
                else if(query_result.equals("STARTENDEQUAL")){
                    Toast.makeText(context, "Start time and end time cannot be the same time", Toast.LENGTH_SHORT).show();
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


