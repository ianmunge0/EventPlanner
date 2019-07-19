package com.testmaps.testmaps;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;

public class TokenActivity extends Activity {
    ContactsCompletionView completionView;
    Person[] people;
    ArrayAdapter<Person> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token_activity);

        /*people = new Person[]{
                new Person("Marshall Weir", "marshall@example.com"),
                new Person("Margaret Smith", "margaret@example.com"),
                new Person("Max Jordan", "max@example.com"),
                new Person("Meg Peterson", "meg@example.com"),
                new Person("Amanda Johnson", "amanda@example.com"),
                new Person("Terry Anderson", "terry@example.com"),
                new Person("Terry7 Anderson", "terry7@example.com"),
                new Person("Terry8 Anderson", "terry8@example.com"),
                new Person("Terry9 Anderson", "terry9@example.com"),
                new Person("Terry10 Anderson", "terry10@example.com")
        };*/
        SharedPreferences sharedPref = getSharedPreferences("usernamepref", Context.MODE_PRIVATE);
        String jsonAttendants = sharedPref.getString("prefkeyforattendants", "");
        try{
            JSONArray jsonArray = new JSONArray(jsonAttendants);
            String[] attendantsArray = jsonArray.toString().replace("[", "").replace("]", "").replace("\"", "").split(",");
            people = new Person[attendantsArray.length];
            for(int i = 0; i < attendantsArray.length; i++){
                people[i] = new Person(attendantsArray[i], attendantsArray[i]+"@example.com");
            }
        }catch (JSONException e){

        }


        adapter = new ArrayAdapter<Person>(this, android.R.layout.simple_list_item_1, people);

        completionView = (ContactsCompletionView)findViewById(R.id.searchView);
        completionView.setAdapter(adapter);
    }
    public void doneAttendants(View view){
        SharedPreferences sharedPref = getSharedPreferences("usernamepref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("prefkeyforselectedattendants", completionView.getObjects().toString());
        editor.commit();
        finish();
    }
}