package com.example.shubhamgoyal.login_signup;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;


public class Product_check extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_check);
        Button b=(Button)findViewById(R.id.check_button);
        final TextView res=(TextView)findViewById(R.id.result);
        final EditText sid=(EditText)findViewById(R.id.PID);
        //String s=sid.getText().toString();
        //res.setText(s);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseQuery<ParseObject> q=ParseQuery.getQuery("Products");
                String s=sid.getText().toString();
                q.whereEqualTo("Id",s);
                q.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> list, ParseException e) {
                        if (e == null) {
                            int size = list.size();
                            if (size == 0) {
                                //res.setVisibility(1);
                                res.setText("unregistered Product");
                            } else {
                                //res.setVisibility(1);
                                res.setText("registered Product");
                            }
                        } else {

                            Toast.makeText(
                                    getApplicationContext(),
                                    e.getMessage(),
                                    Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_check, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
