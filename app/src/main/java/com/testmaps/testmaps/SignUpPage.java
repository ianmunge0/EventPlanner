package com.testmaps.testmaps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class SignUpPage extends Activity
{
    private EditText newusername, newemail, newpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        newusername = (EditText)findViewById(R.id.newusername);
        newemail = (EditText)findViewById(R.id.newemail);
        newpassword = (EditText)findViewById(R.id.newpassword);

    }

    public void signup(View v) {
        String signupusername = newusername.getText().toString();
        String signupemail = newemail.getText().toString();
        String signuppassword = newpassword.getText().toString();
        Matcher m = Pattern.compile("\\W").matcher(signupusername);
        boolean ifmatches = m.find();

        Matcher emailMatcher = Pattern.compile("\\w+\\B@{1}\\w+.\\w+").matcher(signupemail);
        //String regex = "\\w+\\B@{1}\\w+.\\w+";

        boolean ifEmailMatches = emailMatcher.find();
        //boolean ifEmailMatches = Pattern.matches(regex, signupemail);

        //String regex = "[\\W]";
        //boolean match = Pattern.matches(regex, signupusername);

        Toast.makeText(this, "Signing up...", Toast.LENGTH_SHORT).show();
        //new SignupActivity(this);
        if(signupusername.isEmpty() == false && signupemail.isEmpty() == false && signuppassword.isEmpty() == false )
        {
            if(ifmatches == false && isEmailValid(signupemail) == true)
            {
                new SignUpAsyncTask(this).execute(signupusername, signupemail, signuppassword);
            }

            if(ifmatches == true)
            {
                Toast.makeText(this, "Username cannot contain special characters", Toast.LENGTH_LONG).show();
            }
            else if(isEmailValid(signupemail) == false)
            {
                Toast.makeText(this, "Enter a valid email address", Toast.LENGTH_LONG).show();
            }
        }

        if(signupusername.isEmpty() == true || signupemail.isEmpty() == true || signuppassword.isEmpty() == true)
        {
            Toast.makeText(this, "Fill all details", Toast.LENGTH_SHORT).show();
        }


    }

    public static boolean isEmailValid(String email) {
        final Pattern EMAIL_REGEX = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", Pattern.CASE_INSENSITIVE);
        return EMAIL_REGEX.matcher(email).matches();
    }

    public void gotologin(View v)
    {
        Intent starthomepage = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(starthomepage);
    }
}
