package com.example.shubhamgoyal.final_final;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class Product_check extends Activity {
    double mLatitude,mLongitude;
    String loc;
    String s="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_check);
        Button b=(Button)findViewById(R.id.check_button);
        //new line added
        final Button c=(Button)findViewById(R.id.add_button);
        c.setVisibility(View.GONE);


        final TextView res=(TextView)findViewById(R.id.result);
        final TextView sid=(TextView)findViewById(R.id.PID);
        c.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ParseObject new_obj = new ParseObject("Products");
                new_obj.put("Id", s);
                new_obj.saveInBackground();
                Toast.makeText(getApplicationContext(),
                        "Product added successfully",
                        Toast.LENGTH_SHORT).show();
                c.setVisibility(View.GONE);
            }
        });


        Intent myIntent = getIntent(); // gets the previously created intent
        String firstKeyName = myIntent.getStringExtra("s"); // will return "FirstKeyValue"
        //String s=sid.getText().toString();
        //res.setText(s);
        sid.setText(firstKeyName);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> q=ParseQuery.getQuery("Products");
                s="";
                s=sid.getText().toString();
                q.whereEqualTo("Id",s);
                q.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> list, ParseException e) {
                        if (e == null) {
                            int size = list.size();
                            if (size == 0) {
                                res.setText("UNREGISTERED PRODUCT");

                                //new lines added
                                c.setVisibility(View.VISIBLE);
                                c.setText("click to add the above product");

                                //


                            } else {

                                res.setText("REGISTERED PRODUCT");
                            }
                            setUpMap();
                            loc=getLocationAddress();
                            ParseObject obj=new ParseObject("locations");
                            obj.put("latitude",mLatitude);
                            obj.put("longitude",mLongitude);
                            obj.put("address",loc);
                            obj.saveInBackground();

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
    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */



    private void setUpMap() {
        // mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker").snippet("Snippet"));

        // Enable MyLocation Layer of Google Map
        // mMap.setMyLocationEnabled(true);

        // Get LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Get the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Get Current Location
        Location myLocation = locationManager.getLastKnownLocation(provider);

        // set map type
        //  mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Get latitude of the current location
        double latitude = myLocation.getLatitude();
        mLatitude=latitude;

        // Get longitude of the current location
        double longitude = myLocation.getLongitude();
        mLongitude=longitude;

        // Create a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        // Show the current location in Google Map
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        String s23=getLocationAddress();

        // Zoom in the Google Map
        // mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
        //mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(s23).snippet("Your Last Known Location"));
    }
    public String getLocationAddress() {

        if (true) {

            Context mContext=Product_check.this;

            Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
            // Get the current location from the input parameter list
            // Create a list to contain the result address
            List<Address> addresses = null;
            try {
				/*
				 * Return 1 address.
				 */
                addresses = geocoder.getFromLocation(mLatitude, mLongitude, 1);
            } catch (IOException e1) {
                e1.printStackTrace();
                return ("IO Exception trying to get address:" + e1);
            } catch (IllegalArgumentException e2) {
                // Error message to post in the log
                String errorString = "Illegal arguments "
                        + Double.toString(mLatitude) + " , "
                        + Double.toString(mLongitude)
                        + " passed to address service";
                e2.printStackTrace();
                return errorString;
            }
            // If the reverse geocode returned an address
            if (addresses != null && addresses.size() > 0) {
                // Get the first address
                Address address = addresses.get(0);
				/*
				 * Format the first line of address (if available), city, and
				 * country name.
				 */
                String addressText = String.format(
                        "%s, %s, %s",
                        // If there's a street address, add it
                        address.getMaxAddressLineIndex() > 0 ? address
                                .getAddressLine(0) : "",
                        // Locality is usually a city
                        address.getLocality(),
                        // The country of the address
                        address.getCountryName());
                // Return the text
                return addressText;
            } else {
                return "No address found by the service: Note to the developers, If no address is found by google itself, there is nothing you can do about it. :(";
            }
        } else {
            return "Location Not available";
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
