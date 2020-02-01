package com.testmaps.testmaps;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tokenautocomplete.TokenCompleteTextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;
import java.util.Random;

public class TokenActivity extends AppCompatActivity implements TokenCompleteTextView.TokenListener<Person> {
    ContactsCompletionView completionView;
    Person[] people;
    ArrayAdapter<Person> adapter;
    String attendants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token_activity);

        //Set up the contacts example views
        SharedPreferences sharedPref = getSharedPreferences("usernamepref", Context.MODE_PRIVATE);
        String jsonAttendants = sharedPref.getString("prefkeyforattendants", "");
        try{
            JSONArray jsonArray = new JSONArray(jsonAttendants);
            String[] attendantsArray = jsonArray.toString().replace("[", "").replace("]", "").replace("\"", "").split(",");
            people = new Person[attendantsArray.length];
            for(int i = 0; i < attendantsArray.length; i++){
                people[i] = new Person(attendantsArray[i], attendantsArray[i]+"@example.com");//replace @example.com with actual email addresses
            }
        }catch (JSONException e){

        }
        adapter = new PersonAdapter(this, R.layout.person_layout, people);

        completionView = (ContactsCompletionView)findViewById(R.id.searchView);
        completionView.setAdapter(adapter);
        completionView.setThreshold(1);
        completionView.setTokenListener(this);
        completionView.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Select);
        completionView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                attendants = editable.toString();
            }
        });


        if (savedInstanceState == null) {
            completionView.setPrefix("Attendants: ", Color.parseColor("blue"));
        }

        Button continue_ = findViewById(R.id.continueButton);
        continue_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder x = new AlertDialog.Builder(TokenActivity.this);
                x.setTitle("Attendants Confirmation");
                x.setMessage("Please confirm that these are the attendants to your event: "+ attendants);
                x.setCancelable(false);
                x.setCancelable(false);
                x.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        SharedPreferences sharedPref = getSharedPreferences("usernamepref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("prefkeyforselectedattendants", completionView.getObjects().toString());
                        editor.commit();
                        Toast.makeText(getApplicationContext(), "Attendants set", Toast.LENGTH_SHORT).show();
                        finish();

                    }
                });
                x.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                x.create().show();

            }
        });

    }

    private void updateTokenConfirmation() {
        StringBuilder sb = new StringBuilder("Current attendants:\n");
        for (Object token: completionView.getObjects()) {
            sb.append(token.toString());
            sb.append("\n");
        }

    }


    @Override
    public void onTokenAdded(Person token) {
        updateTokenConfirmation();
    }

    @Override
    public void onTokenRemoved(Person token) {
        updateTokenConfirmation();
    }

    @Override
    public void onTokenIgnored(Person token) {
        updateTokenConfirmation();
    }
}
