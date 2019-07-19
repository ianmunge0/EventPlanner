package com.testmaps.testmaps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.*;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class ViewIndividualEventActivity extends Activity {//implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_individual_event);

        SharedPreferences sharedPref = getSharedPreferences("usernamepref", Context.MODE_PRIVATE);
        int pasteventkeyplusone = sharedPref.getInt("pasteventkeyplusone", -1);
        int currenteventkeyplusone = sharedPref.getInt("currenteventkeyplusone", -1);
        int upcomingeventkeyplusone = sharedPref.getInt("upcomingeventkeyplusone", -1);

        int timeb = sharedPref.getInt("timeb", 0);
        int timeb0 = sharedPref.getInt("timeb0", -1);

        if(pasteventkeyplusone != -1 && pasteventkeyplusone != 0){
            int pasteventkey = pasteventkeyplusone -1;
            TextView pastTitle = (TextView) findViewById(R.id.ttitle);
            pastTitle.setText(sharedPref.getString("pasttitle"+pasteventkey, ""));

            TextView pastDescription = (TextView) findViewById(R.id.ddescription);
            pastDescription.setText(sharedPref.getString("pastdescription"+pasteventkey, ""));

            TextView pastDate = (TextView) findViewById(R.id.ddate);
            pastDate.setText(sharedPref.getString("pastdate"+pasteventkey, ""));

            TextView pastTime = (TextView) findViewById(R.id.ttime);
            pastTime.setText(sharedPref.getString("pasttime"+pasteventkey, ""));

            TextView pastCreator = (TextView) findViewById(R.id.ccreator);
            pastCreator.setText(sharedPref.getString("pastcreator"+pasteventkey, ""));

            TextView pastAttendants = (TextView) findViewById(R.id.aattendants);
            //pastAttendants.setText(sharedPref.getString("pastattendants"+pasteventkey, ""));
            int noofattendants = sharedPref.getInt("noofattendants", 0);
            for(int j = 0; j < noofattendants; j++){
                pastAttendants.append(sharedPref.getString("username"+j, "attendant")+": "+"CHECKED IN\n");//sharedPref.getString("ifcheckedin"+j, "if checked in")+"\n");
            }

            TextView pastVenue = (TextView) findViewById(R.id.vvenue);
            pastVenue.setText(sharedPref.getString("pastvenue"+pasteventkey, ""));


            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("keyforindividual", pasteventkey);
            editor.commit();
        }
        else if(currenteventkeyplusone != -1 && currenteventkeyplusone != 0){
            int currenteventkey = currenteventkeyplusone-1;
            TextView currentTitle = (TextView) findViewById(R.id.ttitle);
            currentTitle.setText(sharedPref.getString("currenttitle"+currenteventkey, ""));

            TextView currentDescription = (TextView) findViewById(R.id.ddescription);
            currentDescription.setText(sharedPref.getString("currentdescription"+currenteventkey, ""));

            TextView currentDate = (TextView) findViewById(R.id.ddate);
            currentDate.setText(sharedPref.getString("currentdate"+currenteventkey, ""));

            TextView currentTime = (TextView) findViewById(R.id.ttime);
            currentTime.setText(sharedPref.getString("currenttime"+currenteventkey, ""));

            TextView currentCreator = (TextView) findViewById(R.id.ccreator);
            currentCreator.setText(sharedPref.getString("currentcreator"+currenteventkey, ""));

            TextView currentAttendants = (TextView) findViewById(R.id.aattendants);
            currentAttendants.setText(sharedPref.getString("currentattendants"+currenteventkey, ""));

            TextView currentVenue = (TextView) findViewById(R.id.vvenue);
            currentVenue.setText(sharedPref.getString("currentvenue"+currenteventkey, ""));

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("keyforindividual", currenteventkey);
            editor.commit();
        }
        else if(upcomingeventkeyplusone != -1 && upcomingeventkeyplusone != 0){
            int upcomingeventkey = upcomingeventkeyplusone-1;
            TextView pastTitle = (TextView) findViewById(R.id.ttitle);
            pastTitle.setText(sharedPref.getString("upcomingtitle"+upcomingeventkey, ""));

            TextView upcomingDescription = (TextView) findViewById(R.id.ddescription);
            upcomingDescription.setText(sharedPref.getString("upcomingdescription"+upcomingeventkey, ""));

            TextView upcomingDate = (TextView) findViewById(R.id.ddate);
            upcomingDate.setText(sharedPref.getString("upcomingdate"+upcomingeventkey, ""));

            TextView upcomingTime = (TextView) findViewById(R.id.ttime);
            upcomingTime.setText(sharedPref.getString("upcomingtime"+upcomingeventkey, ""));

            TextView upcomingCreator = (TextView) findViewById(R.id.ccreator);
            upcomingCreator.setText(sharedPref.getString("upcomingcreator"+upcomingeventkey, ""));

            TextView upcomingAttendants = (TextView) findViewById(R.id.aattendants);
            upcomingAttendants.setText(sharedPref.getString("upcomingattendants"+upcomingeventkey, ""));

            TextView upcomingVenue = (TextView) findViewById(R.id.vvenue);
            upcomingVenue.setText(sharedPref.getString("upcomingvenue"+upcomingeventkey, ""));

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("keyforindividual", upcomingeventkey);
            editor.commit();
        }
        Button btnCheckIn = (Button) findViewById(R.id.btncheckin);
        TextView timey = findViewById(R.id.texxtvieww);
        if(timeb == 0 && timeb0 != 0)
        {
            timey.setVisibility(View.INVISIBLE);
            if(pasteventkeyplusone != -1 && pasteventkeyplusone != 0){
                btnCheckIn.setVisibility(View.INVISIBLE);
                //show checkedIn details

            }
            else{
                btnCheckIn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(getApplicationContext(), myCurrentLat+", "+myCurrentLng, Toast.LENGTH_LONG).show();

                        Intent startmaps2 = new Intent(ViewIndividualEventActivity.this, MapsActivity2.class);
                        startmaps2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(startmaps2);
                    }
                });
            }

        }
        else if(timeb != 0 || timeb0 == 0)
        {
            SharedPreferences.Editor editor = sharedPref.edit();

            if(timeb != 0){
                String evntid = sharedPref.getString("eventIdForViewIndiv", "zer0");
                editor.putInt("byMinutesevnt"+evntid, timeb);
                editor.remove("eventIdForViewIndiv");
                editor.remove("timeb");
                editor.remove("noofattendants");
                editor.commit();
                if(pasteventkeyplusone != -1 && pasteventkeyplusone != 0){
                    editor.remove("pasteventkeyplusone");
                    editor.commit();
                }
                else if(currenteventkeyplusone != -1 && currenteventkeyplusone != 0){
                    editor.remove("currenteventkeyplusone");
                    editor.commit();
                }
                else if(upcomingeventkeyplusone != -1 && upcomingeventkeyplusone != 0){
                    editor.remove("upcomingeventkeyplusone");
                    editor.commit();
                }
                timey.setText("time by "+timeb);
            }
            else if(timeb0 == 0){
                String evntid = sharedPref.getString("eventIdForViewIndiv", "zer0");
                editor.putInt("byMinutesevnt"+evntid, timeb0);
                editor.remove("eventIdForViewIndiv");
                editor.remove("timeb");
                editor.remove("noofattendants");
                editor.commit();
                if(pasteventkeyplusone != -1 && pasteventkeyplusone != 0){
                    editor.remove("pasteventkeyplusone");
                    editor.commit();
                }
                else if(currenteventkeyplusone != -1 && currenteventkeyplusone != 0){
                    editor.remove("currenteventkeyplusone");
                    editor.commit();
                }
                else if(upcomingeventkeyplusone != -1 && upcomingeventkeyplusone != 0){
                    editor.remove("upcomingeventkeyplusone");
                    editor.commit();
                }
                timey.setText("time by "+timeb0);
            }
            btnCheckIn.setVisibility(View.INVISIBLE);

        }


    }
    public void onBackPressed(){
        SharedPreferences sharedPref = getSharedPreferences("usernamepref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("pasteventkeyplusone");
        editor.remove("currenteventkeyplusone");
        editor.remove("upcomingeventkeyplusone");
        editor.remove("noofattendants");
        editor.commit();
        finish();
    }
    private GoogleMap mMap;

    /*@Override
    public void onMapReady(GoogleMap googleMap){
        mMap = googleMap;
        final double myCurrentLat = mMap.getMyLocation().getLatitude();
        final double myCurrentLng = mMap.getMyLocation().getLongitude();


    }*/




			/*String[] eventsArrayforPastTab = jsonArray.toString().replace("[", "").replace("]", "").replace("\"", "").replace(",", ",-").split(",");

			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putString("arraytopasttabkey", eventsArrayforPastTab.toString());
			editor.commit();*/
    //textViewForEvents.setText(eventsArray[3]);


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_individual_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
