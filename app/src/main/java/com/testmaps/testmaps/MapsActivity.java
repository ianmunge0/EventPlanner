package com.testmaps.testmaps;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMapOptions;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener, OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        GoogleMapOptions mapOption = new GoogleMapOptions();
        mapOption.tiltGesturesEnabled(true);
        mapOption.rotateGesturesEnabled(true);
        mapOption.scrollGesturesEnabled(true);
        mapOption.compassEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        enableMyLocation();
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);




        // Add a marker in Nairobi and move the camera
        LatLng nairobi = new LatLng(-1.2921, 36.8219);
        mMap.addMarker(new MarkerOptions().position(nairobi).draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(nairobi));
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(final Marker marker) {
                LatLng venue = marker.getPosition(); //LatLng prefered in onMarkerDrag
                double lLatitude = venue.latitude;
                double lLongitude = venue.longitude;
                //long longlLatitude = Double.doubleToRawLongBits(lLatitude);
                //long longlLongitude = Double.doubleToRawLongBits(lLongitude);
                String strlLatitude = Double.toString(lLatitude);
                String strlLongitude = Double.toString(lLongitude);
                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("usernamepref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("strrlatitude", strlLatitude);
                editor.putString("strrlongitude", strlLongitude);
                editor.commit();
                //double backtolLatitude = Double.longBitsToDouble(a);
                //double backtolLongitude = Double.longBitsToDouble(b);


                /*AlertDialog.Builder x = new AlertDialog.Builder(getBaseContext());
                x.setMessage("Set Location");
                x.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("usernamepref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        LatLng venue = marker.getPosition();
                        double lLatitude = venue.latitude;
                        double lLongitude = venue.longitude;
                        long a = Double.doubleToRawLongBits(lLatitude);
                        long b = Double.doubleToRawLongBits(lLongitude);
                        Toast.makeText(getApplicationContext(), a+", "+b, Toast.LENGTH_LONG).show();
                    }
                });
                x.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                x.create().show();*/
            }
        });

    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location.getLatitude()+", "+location.getLongitude(), Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onMyLocationButtonClick() {
        //Toast.makeText(this, "locatiON ButtON", Toast.LENGTH_LONG).show();
        if(isLocationEnabled(this) == false)
        {
            alertDialogg();
        }
        else{
            Toast.makeText(this, "location click", Toast.LENGTH_LONG).show();

        }
        //Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }


    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(new AppCompatActivity(), LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);

        }
    }
    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    private void alertDialogg(){
        AlertDialog.Builder x = new AlertDialog.Builder(MapsActivity.this);
        x.setMessage("Enable Location ?");
        //x.setCancelable(false);
        x.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                // TODO Auto-generated method stub
                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                Toast.makeText(getBaseContext(), "click location button", Toast.LENGTH_LONG).show();
            }
        });
        x.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        x.create().show();
    }


}
