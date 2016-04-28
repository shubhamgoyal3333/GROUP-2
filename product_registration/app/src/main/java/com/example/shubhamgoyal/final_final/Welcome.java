package com.example.shubhamgoyal.final_final;


/**
 * Created by SHUBHAM GOYAL on 06-12-2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseUser;

public class Welcome extends Activity {

    // Declare Variable
    Button logout;
    Button conti;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from singleitemview.xml
        setContentView(R.layout.welcome);

        // Retrieve current user from Parse.com
        ParseUser currentUser = ParseUser.getCurrentUser();

        // Convert currentUser into String
        String struser = currentUser.getUsername().toString();

        // Locate TextView in welcome.xml
        TextView txtuser = (TextView) findViewById(R.id.txtuser);

        // Set the currentUser String into TextView
        txtuser.setText("You are logged in as " + struser);

        // Locate Button in welcome.xml
        logout = (Button) findViewById(R.id.logout);
        conti=(Button) findViewById(R.id.cont);
        //intent
        /*Intent i = new Intent(
                Welcome.this, Product_check.class);
        startActivity(i);*/

        // Logout Button Click Listener
        logout.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // Logout current user
                //ParseUser.logOut();
                Intent i = new Intent(
                        Welcome.this, LoginSignupActivity.class);
                startActivity(i);
                finish();
            }
        });
        conti.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // Logout current user
                //ParseUser.logOut();
                Intent i = new Intent(
                        Welcome.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}