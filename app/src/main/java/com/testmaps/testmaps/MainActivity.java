package com.testmaps.testmaps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etUsername = (EditText)findViewById(R.id.username);
        etPassword = (EditText)findViewById(R.id.password);

        TextView text = (TextView)findViewById(R.id.testtext);
    }

    public void login(View v) {
        String username = etUsername.getText().toString();
        String passWord = etPassword.getText().toString();


        Toast.makeText(this, "Logging In...", Toast.LENGTH_SHORT).show();
        //new SignupActivity(this);
        if(username.isEmpty() == false && passWord.isEmpty() == false)
        {
            new LogInActivity(getApplicationContext()).execute(username, passWord);

            //this.moveTaskToBack(true);
            //finish();
            //@Override
            	/*public void onResume()
        		{
        			super.onResume();
                	Intent starthomepage = new Intent(getApplicationContext(), Home.class);
                	starthomepage.putExtra("usernameforhome", username);
                	starthomepage.putExtra("passwordforhome", passWord);
                	startActivity(starthomepage);
        		}*/

            //Toast.makeText(this, username +"-"+passWord , Toast.LENGTH_LONG).show();



        }
        else
        {
            Toast.makeText(this, "Fill all details", Toast.LENGTH_SHORT).show();
        }


    }

    public void gotosignup(View v)
    {
        Intent startsignuppage = new Intent(getApplicationContext(), SignUpPage.class);
        startActivity(startsignuppage);
        finish();
    }


}
