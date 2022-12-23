package com.example.sfwether;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.pm.PackageManager;

import android.location.LocationManager;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.INTERNET;

import static androidx.core.location.LocationManagerCompat.getCurrentLocation;

import com.google.android.material.snackbar.Snackbar;

import java.util.EventListener;


public class MainActivity extends AppCompatActivity implements EventListener {
    EditText city;
    private final String url = "http://api.openweathermap.org/data/2.5/weather";
    private final String id = "a4fa0cbecd1f9a05057fcd2ea25f86a5";
    protected LocationManager locationManager;
    protected Context context;
    TextView txtLat;
    String lat;
    String provider;
    protected String latitude, longitude;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private View view;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void setLocationWindow(View view){
        setContentView(R.layout.add_location_tab);
    }

    public void home(View view){
        setContentView(R.layout.activity_main);
    }


    //-----------location permission Start -------------------
    public void permissionCheck(View view){
        if(!checkPermission()) {
            requestPermission();
        }
        else{
            Toast.makeText(getApplicationContext(),"Location permission Granted",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);

        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION }, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                if (locationAccepted)
                    Snackbar.make(view, "Permission Granted, Now you can access location data and camera.", Snackbar.LENGTH_LONG).show();
                else {

                    Snackbar.make(view, "Permission Denied, You cannot access location data and camera.", Snackbar.LENGTH_LONG).show();

                    if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        requestPermissions(new String[]{ACCESS_FINE_LOCATION},
                                                PERMISSION_REQUEST_CODE);
                                    }
                                };
                    }

                }
            }
        }
    }

    /*-----------location permission end -------------------*/

    /*------------internet permission ---------------------*/
    private void internetCheck(){
        if(!checkInternet()) {
            turnOnInternet();
        }
    }

    private boolean checkInternet() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);

        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void turnOnInternet() {

        ActivityCompat.requestPermissions(this, new String[]{INTERNET}, PERMISSION_REQUEST_CODE);

    }


    /*------------internet permission end---------------------*/




}