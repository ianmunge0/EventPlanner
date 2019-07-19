package com.testmaps.testmaps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.DateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.InflateException;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.security.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.view.View.OnClickListener;
import android.webkit.WebView;

import static java.lang.System.currentTimeMillis;

public class CreateEvent extends Activity {
    private Context context1;
    EditText eteventtitle, eteventDescription;
    DatePicker starteventdate;
    TimePicker starttimepicker;
    DatePicker endeventdate;
    TimePicker endtimepicker;

//	String query_result;

    String eventTitle;
    String eventdescription;
    String startdayofevent;
    String startmonthofevent;
    String startyearofevent;
    String eventstarthour;
    String eventstartminute;
    String enddayofevent;
    String endmonthofevent;
    String endyearofevent;
    String eventendhour;
    String eventendminute;
    AutoCompleteTextView attendants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        context1 = this;

        eteventtitle = findViewById(R.id.eventtitle);
        eteventDescription = findViewById(R.id.eventdescription);

        starteventdate = findViewById(R.id.startDatePicker);
        starttimepicker = findViewById(R.id.startTimePicker);

        endeventdate = findViewById(R.id.endDatePicker);
        endtimepicker = findViewById(R.id.endTimePicker);
        //attendants = (AutoCompleteTextView)findViewById(R.id.eventattendants);

        final SharedPreferences sharedPref = getSharedPreferences("usernamepref", Context.MODE_PRIVATE);
        String jsonAttendants = sharedPref.getString("prefkeyforattendants", "");
        try {
            JSONArray jsonArray = new JSONArray(jsonAttendants);
            String[] attendantsArray = jsonArray.toString().replace("[", "").replace("]", "").replace("\"", "").split(",");


            ArrayAdapter<String> adapter = new ArrayAdapter<String> (this, android.R.layout.simple_dropdown_item_1line, attendantsArray);



            /*attendants.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if(!hasFocus){
                        Button attendants = new Button(getBaseContext());

                        Toast.makeText(getBaseContext(), attendants.getText().toString(), Toast.LENGTH_LONG).show();
                    }
                }
            });


            attendants.setAdapter(adapter);
            attendants.setThreshold(1);*/
        }
        catch(JSONException e)
        {

        }


        Button createeventbtn = (Button)findViewById(R.id.btncreate);

        createeventbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View vi) {
                eventTitle = eteventtitle.getText().toString();
                eventdescription = eteventDescription.getText().toString();

                int startdayofeventint = starteventdate.getDayOfMonth();
                startdayofevent = Integer.toString(startdayofeventint);

                int startmonthOfEventint = starteventdate.getMonth()+1;
                startmonthofevent = Integer.toString(startmonthOfEventint);

                int startyearofeventint = starteventdate.getYear();
                startyearofevent = Integer.toString(startyearofeventint);

                int eventstarthourint = starttimepicker.getCurrentHour();
                eventstarthour = Integer.toString(eventstarthourint);

                int eventstartminuteint = starttimepicker.getCurrentMinute();
                eventstartminute = Integer.toString(eventstartminuteint);


                int enddayofeventint = endeventdate.getDayOfMonth();
                enddayofevent = Integer.toString(enddayofeventint);

                int endmonthOfEventint = endeventdate.getMonth()+1;
                endmonthofevent = Integer.toString(endmonthOfEventint);

                int endyearofeventint = endeventdate.getYear();
                endyearofevent = Integer.toString(endyearofeventint);

                int eventendhourint = endtimepicker.getCurrentHour();
                eventendhour = Integer.toString(eventendhourint);

                int eventendminuteint = endtimepicker.getCurrentMinute();
                eventendminute = Integer.toString(eventendminuteint);


                //Toast.makeText(getApplicationContext(), eventTitle, Toast.LENGTH_LONG).show();

//				Toast.makeText(getApplicationContext(), eventtitle+" "+eventdescription+" "+dayofevent+" "+monthofevent+" "+yearofevent+" "+eventhour+" "+
//			        		eventminute, 20000).show();
//		        new PostEvent(this).execute(eventtitle, eventdescription, dayofevent, monthofevent, yearofevent, eventhour,
//		        		eventminute);
                 //Editable attendantss = attendants.getText();



                String selectedAttendants = sharedPref.getString("prefkeyforselectedattendants", "");
                //new PostEvent(getApplicationContext()).execute(eventTitle, eventdescription, dayofevent, monthofevent, yearofevent, eventhour, eventminute, attendantss.toString());
                new CreateEventAsyncTask(context1).execute(eventTitle, eventdescription, startdayofevent, startmonthofevent, startyearofevent, eventstarthour, eventstartminute, enddayofevent, endmonthofevent, endyearofevent, eventendhour, eventendminute, selectedAttendants);//attendantss.toString());
                //toDatabase();
                //onPostExecute(toDatabase());


            }

            //protected DataInputStream toDatabase() {
/*			protected void toDatabase() {
		    	   String data;
			        try {
			            data = "?eventtitle=" + URLEncoder.encode(eventTitle, "UTF-8");
//			            data += "&eventdescription=" + URLEncoder.encode(eventDescription, "UTF-8");
//			            data += "&dayofevent=" + URLEncoder.encode(eventDay, "UTF-8");
//			            data += "&monthofevent=" + URLEncoder.encode(eventMonth, "UTF-8");
//			            data += "&yearofevent=" + URLEncoder.encode(eventYear, "UTF-8");
//			            data += "&eventhour=" + URLEncoder.encode(eventHour, "UTF-8");
//			            data += "&eventminute=" + URLEncoder.encode(eventMinute, "UTF-8");

			            String link = "http://172.28.0.1/tukioeventhandlers/createevent.php"+data; //exact host url
			            //Toast.makeText(getApplicationContext(), link, 50000).show();
			            URL url = new URL(link);
			            //url.getContent();
			            //HttpURLConnection con = (HttpURLConnection) url.openConnection();


			            //String con = eventTitle+" - "+eventDescription;
			            //bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			            //InputStreamReader theReadBytesToChar = new InputStreamReader(con.getInputStream());
			            InputStreamReader theReadBytesToChar = new InputStreamReader(url.openStream());
			            Toast.makeText(getApplicationContext(), theReadBytesToChar.toString(), 50000).show();
			            //boolean ifInputs = con.getDoInput();
			            //BufferedReader bufferedReader = new BufferedReader(streamReader);
			            //String theResult = streamReader.toString();
			            //Toast.makeText(getApplicationContext(), "Error is: "+theResult, 50000).show();
			            //Toast.makeText(getApplicationContext(), con, Toast.LENGTH_LONG).show();
			            //return streamReader;
			            //return ifInputs;

			        } catch (Exception e) {
			            //String theResult = new String("Exception: " + e.getLocalizedMessage());
			            //DataInputStream streamReader = null;
			            //boolean ifInputs = false;
			            //return ifInputs;

			            //return streamReader;

			        }

			       }*/




/*		    protected void onPostExecute(DataInputStream newstream) {
		        //String jsonStr = newstream;

		        if (newstream != null) {
		            try {
		            	JSONObject jsonObj = new JSONObject(newstream.toString());
		                //JSONObject jsonObj = new JSONObject(jsonStr);
		                query_result = jsonObj.getString("query_result");
		                if (query_result.equals("SUCCESS")) {
		                    Toast.makeText(getApplicationContext(), "Event created successfully.", Toast.LENGTH_SHORT).show();
		                } else if (query_result.equals("FAILURE")) {
		                    Toast.makeText(getApplicationContext(), "Failed to create event", Toast.LENGTH_SHORT).show();
		                } else if(query_result.equals("ERROR")) {
		                	Toast.makeText(getApplicationContext(), "Error in logging in.", Toast.LENGTH_SHORT).show();
		                }
		                else {
		                    Toast.makeText(getApplicationContext(), "Couldn't connect to remote database.", Toast.LENGTH_SHORT).show();
		                }
		            } catch (JSONException e) {
		                e.printStackTrace();
		                Toast.makeText(getApplicationContext(), "Error parsing JSON data :- "+e.getLocalizedMessage()+" - "+newstream, Toast.LENGTH_LONG).show();
		            }
		        } else {
		            Toast.makeText(getApplicationContext(), "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
		        }

		    }*/






        });



    }

    public void setVenue(View v)
    {
		/*Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=Current%20Location&daddr=51.5074, 0.1278"));
		startActivity(browserIntent);*/

		/*Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.openstreetmap.org/"));
		startActivity(browserIntent);*/
        try
        {
            Intent startviewpage = new Intent(CreateEvent.this, MapsActivity.class);
            startviewpage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(startviewpage);
        }
        catch(IllegalStateException e)
        {
            Toast.makeText(CreateEvent.this, "webview error: "+e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }


    }
    public void setAttendants(View view){
        Intent startAttendantsPage = new Intent(CreateEvent.this, TokenActivity.class);
        startAttendantsPage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(startAttendantsPage);
    }
    public void onBackPressed()
    {
        finish();
    }


}
