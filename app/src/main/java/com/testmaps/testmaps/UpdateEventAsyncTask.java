package com.testmaps.testmaps;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
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
import java.util.Arrays;
import java.util.List;


public class UpdateEventAsyncTask extends AsyncTask<String, Void, String> {

    private Context context;

    public UpdateEventAsyncTask(Context context1) {
        this.context = context1;
    }

    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... params) {

        String newusername = params[0];
        String createevenifsametime = params[1];
        /*SharedPreferences sharedPref = context.getSharedPreferences("usernamepref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("prefkeyforusername", newusername);
        editor.commit();*/

        String link;


        try {


            link = "https://ianmunge.co.ke/tukioeventhandlers/updateevent.php";
            URL url = new URL(link);

            HttpURLConnection httpurlconnection = (HttpURLConnection)url.openConnection();
            httpurlconnection.setRequestMethod("POST");
            httpurlconnection.setDoOutput(true);
            httpurlconnection.setDoInput(true);
            OutputStream outputstream = httpurlconnection.getOutputStream();
            BufferedWriter bufferedwriter = new BufferedWriter(new OutputStreamWriter(outputstream, "UTF-8"));
            String posteventdata = URLEncoder.encode("usernameforupdate", "UTF-8")+"="+URLEncoder.encode(newusername, "UTF-8")+"&"+
                    URLEncoder.encode("createevenifsametime", "UTF-8")+"="+URLEncoder.encode(createevenifsametime, "UTF-8");

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
    String[] query_result;
    JSONObject[] jsonObj;

    @Override
    protected void onPostExecute(String result) {
        String jsonStr1 = result;

        if (jsonStr1 != null)
        {
            try
            {
                JSONObject jsonObj = new JSONObject(jsonStr1);
                query_result1 = jsonObj.getString("query_result");
                if(query_result1.equals("NOTCREATED"))
                {
                    Toast.makeText(context, "Event not created", Toast.LENGTH_LONG).show();
                }


            }
            catch(JSONException e)
            {
                try
                {
                    JSONArray jsonArray = new JSONArray(jsonStr1);
                    int endloop = jsonArray.length()-1;
                    String[] eventsArray = new String[jsonArray.length()];// = jsonArray.toString().replace("[", "").replace("]", "").replace("\"", "").split(",");
                    final String[] eventsArray1 = new String[jsonArray.length()];
                    query_result = new String[jsonArray.length()];
                    jsonObj = new JSONObject[jsonArray.length()];
                    final String[] eventsArray1date, eventsArray1time;
                    eventsArray1date = new String[jsonArray.length()];
                    eventsArray1time = new String[jsonArray.length()];

                    for(int r=0; r<=endloop; r++){
                        query_result[r] = jsonArray.getString(r);
                        jsonObj[r] = new JSONObject(query_result[r]);
                        eventsArray[r] = jsonObj[r].getString("title");
                        eventsArray1[r] = jsonObj[r].getString("datecreated")+" "+jsonObj[r].getString("timecreated");
                        eventsArray1date[r] = jsonObj[r].getString("datecreated");
                        eventsArray1time[r] = jsonObj[r].getString("timecreated");

                    }

                    //JSONArray jsonArray = new JSONArray(jsonStr1);

                    final List<String> listCustomObject = new ArrayList<String>(Arrays.asList(eventsArray));
                    final List<String> listCustomObject1 = new ArrayList<String>(Arrays.asList(eventsArray1));
                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_2, android.R.id.text1, listCustomObject){
                        @Override
                        public View getView(int position,
                                            View convertView, ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);

                            TextView text1 = view.findViewById(android.R.id.text1);
                            TextView text2 = view.findViewById(android.R.id.text2);

                            text1.setText(listCustomObject.get(position));

                            text2.setText("created on date(Y-M-D):"+eventsArray1date[position]+" time:"+eventsArray1time[position]);

                            return view;
                        }
                    };

                    final AlertDialog.Builder x = new AlertDialog.Builder(context);
                    //x.setMessage("Choose the first event to attend");
                    x.setCancelable(false);
                    x.setTitle("Choose the first event you will attend");
                    x.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //final String firstevent = arrayAdapter.getItem(which);
                            final String firstevent = eventsArray1[which];
                            AlertDialog.Builder builderInner = new AlertDialog.Builder(context);
                            builderInner.setMessage(arrayAdapter.getItem(which));
                            builderInner.setCancelable(false);
                            builderInner.setTitle("Confirm...");
                            builderInner.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,int which) {
                                    //Toast.makeText(context, firstevent, Toast.LENGTH_LONG).show();
                                    new FirstEventAsyncTask(context).execute(firstevent);
                                    dialog.dismiss();
                                }
                            });
                            builderInner.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,int which) {
                                    x.show();
                                }
                            });
                            builderInner.show();
                        }
                    });


                    x.create().show();

                }
                catch(JSONException f)
                {

                }


            }
        }
        else
        {
            Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
        }
    }



}

