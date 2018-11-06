package com.example.root.tg_01;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mongodb.client.MongoCollection;

import org.bson.Document;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private volatile GoogleMap mMap;

    private TextView textView;

    private volatile LocationManager locationManager;

    private volatile LocationListener locationListener;

    private volatile MongoCollection<Document> coll;

    private volatile Coordenate coordenate;

    private RadioButton botaoAssedio;

    private RadioButton botaoAmeaca;

    private RadioButton botaoDanomoral;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        textView = (TextView) findViewById(R.id.textView);

        botaoAssedio = (RadioButton) findViewById(R.id.assedio);
        botaoAmeaca = (RadioButton) findViewById(R.id.ameaca);
        botaoDanomoral = (RadioButton) findViewById(R.id.danomoral);

        botaoAssedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d()
                coordenate.setTipo("Assédio");

                MongoLabSaveCoordenate tsk = new MongoLabSaveCoordenate();
                tsk.execute(coordenate);
                Toast.makeText(MapsActivity.this, "Saved to MongoDB!! Assedio!"+coordenate.getLatitude()+": "
                        +coordenate.getLatitude(), Toast.LENGTH_SHORT).show();
            }
        });

        botaoAmeaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coordenate.setTipo("Ameaca");

                MongoLabSaveCoordenate tsk = new MongoLabSaveCoordenate();
                tsk.execute(coordenate);
                Toast.makeText(MapsActivity.this, "Saved to MongoDB!! Ameaca!", Toast.LENGTH_SHORT).show();
            }
        });

        botaoDanomoral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coordenate.setTipo("Danomoral");

                MongoLabSaveCoordenate tsk = new MongoLabSaveCoordenate();
                tsk.execute(coordenate);
                Toast.makeText(MapsActivity.this, "Saved to MongoDB!! Danomoral!", Toast.LENGTH_SHORT).show();
            }
        });

//        Toast.makeText(this, "Saved to MongoDB!!", Toast.LENGTH_SHORT).show();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE); 
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
//                textView.setText("Lat : "+location.getLatitude()+"\t Long : "+location.getLongitude());
                LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(sydney).title("Casa"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                coordenate = new Coordenate();
//                synchronized (coordenate){}
                coordenate.setLatitude(location.getLatitude());
                coordenate.setLongitude(location.getLongitude());
//                coordenate.setTipo("Assédio");
//
//                MongoLabSaveCoordenate tsk = new MongoLabSaveCoordenate();
//                tsk.execute(coordenate);
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) { }
            @Override
            public void onProviderEnabled(String provider) { }
            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 10);
            }
            return;
        } else {
            configureLocation();
        }
    }

    @SuppressLint("MissingPermission")
    private void configureLocation() {
        locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    configureLocation();
                    return;
                }
        }
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

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
