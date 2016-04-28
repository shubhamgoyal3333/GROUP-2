package com.example.shubhamgoyal.final_final;


/**
 * Created by SHUBHAM GOYAL on 01-3-2016.
 */
import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Add your initialization code here
        Parse.initialize(this, "0QJ5HPOgxOvJoxOqpADq5oakXLCJi4z8SaXBTBNA", "FS0AnBkNWIO0kxdnvVWAKkU2D7fuzV9ZBitcjE5q");

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        // If you would like all objects to be private by default, remove this
        // line.
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);
    }

}