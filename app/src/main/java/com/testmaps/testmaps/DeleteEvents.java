package com.testmaps.testmaps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class DeleteEvents extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_events);


        final ListView lv = (ListView)findViewById(R.id.listViewdeleteevents);

        SharedPreferences sharedPref = getSharedPreferences("usernamepref", Context.MODE_PRIVATE);
        String jsonEventstitles = sharedPref.getString("prefkeyfordeleteevents", "");

        try
        {
            JSONArray jsonArray = new JSONArray(jsonEventstitles);
            String[] eventsArray = jsonArray.toString().replace("[", "").replace("]", "").replace("\"", "").split(",");

            //textViewForEvents.setText(eventsArray[3]);


            final List<String> listCustomObject = new ArrayList<String>(Arrays.asList(eventsArray));

            ArrayAdapter<String> adapter = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_2, android.R.id.text1, listCustomObject) {

                @Override
                public View getView(int position,
                                    View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);

                    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                    TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                    text1.setText(listCustomObject.get(position));

                    //text2.setText( "my text");

                    return view;
                }




            };
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new OnItemClickListener()
            {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3)
                {
                    AlertDialog.Builder x = new AlertDialog.Builder(DeleteEvents.this);
                    x.setMessage("Are you sure you want to delete this event ?");
                    x.setCancelable(false);
                    x.setCancelable(false);
                    x.setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            String vieweventss = "past";
                            new DeleteEventsAsyncTask(getApplicationContext()).execute(vieweventss, listCustomObject.get(position));

                        }
                    });
                    x.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    x.create().show();
                    // TODO Auto-generated method stub

                }
            });

        }
        catch(JSONException e)
        {

        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.delete_events, menu);
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
