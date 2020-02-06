package com.testmaps.testmaps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Home extends Activity {
    //String usernameforhomme = getIntent().getExtras().getString("usernameforhome");
    //String passwordforhomme = getIntent().getExtras().getString("passwordforhome");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Intent intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //String newString;
		/*if(savedInstanceState == null)
		{
			Bundle extras = getIntent().getExtras();
			if(extras == null)
			{
				newString = null;
			}
			else
			{
				newString = extras.getString("stringineed");
				Toast.makeText(this, newString, Toast.LENGTH_SHORT).show();
			}
		}
		else
		{
			newString = (String) savedInstanceState.getSerializable("stringineed");
			Toast.makeText(this, newString, Toast.LENGTH_SHORT).show();
		}*/

        Button createeventbtn = (Button)findViewById(R.id.btncreateevent);
        createeventbtn.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String vieweventss = "past";
                try
                {
                    new FetchAttendantsAsyncTask(getApplicationContext()).execute(vieweventss);

                    /*Intent startcreateeventpage = new Intent(getApplicationContext(), CreateEvent.class);
                    //startviewpage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startcreateeventpage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(startcreateeventpage);*/
                }
                catch(Exception e)
                {
                    Toast.makeText(getApplicationContext(), "catch block: "+e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            }

			/*@Override
			public void onClick(View v) {
			startActivity(new Intent(Home.this, CreateEvent.class));
			}*/
        });

        Button vieweventbtn = (Button)findViewById(R.id.btnviewevents);
        vieweventbtn.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v) {
                //Intent startviewpage = new Intent(Home.this, ViewEvents.class);
                String vieweventsfromhomepage = "past";
                try
                {

                    //startviewpage.putExtra("usernameforview", usernameforhomme);
                    //startviewpage.putExtra("passwordforview", passwordforhomme);
                    //startviewpage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //startActivity(startviewpage);

                    Intent startviewpage = new Intent(Home.this, EventsTab.class);
                    startviewpage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(startviewpage);

                    //new ViewPastEventsAsyncTask(getApplicationContext()).execute(vieweventsfromhomepage);
                }
                catch(Exception e)
                {
                    Toast.makeText(getApplicationContext(), "catch block"+e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }


            }
        });

        Button deleteeventbtn = (Button)findViewById(R.id.btndeleteevents);
        deleteeventbtn.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String deleteeventss = "delete";
                new ListForDeleteEventsAsyncTask(getApplicationContext()).execute(deleteeventss);
            }
        });

    }
    public void onBackPressed()
    {
        finish();
    }
}
