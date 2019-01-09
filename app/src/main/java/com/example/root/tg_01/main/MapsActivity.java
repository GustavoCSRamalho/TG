package com.example.root.tg_01.main;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.tg_01.R;
import com.example.root.tg_01.models.Coordenate;
import com.example.root.tg_01.service.coordenate.CoordenateListener;
import com.example.root.tg_01.service.coordenate.interfaces.CoordenateListenerInterf;
import com.example.root.tg_01.service.database.firebase.FireBaseService;
import com.example.root.tg_01.service.database.firebase.interfaces.FireBaseInterf;
import com.example.root.tg_01.service.geofence.GeofenceService;
import com.example.root.tg_01.service.geofence.interfaces.GeofenceInterf;
import com.example.root.tg_01.service.maps.MapsAction;
import com.example.root.tg_01.service.maps.interfaces.MapsActionInterf;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    private TextView txtLatitude, txtLongitude;

    private FireBaseInterf fireBaseInterf;

    private volatile GoogleMap mMap;

    private volatile Coordenate coordenate;

    private CoordenateListenerInterf coordenateListener;

    private MapsActionInterf mapsAction;

    private GeofenceInterf geofenceInterf;

    private SupportMapFragment mapFragment;


    private void buildLayoutsReference() {
        setContentView(R.layout.activity_maps);
        txtLatitude = (TextView) findViewById(R.id.txtLatitude);
        txtLongitude = (TextView) findViewById(R.id.txtLongitude);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        coordenateListener = CoordenateListener.getInstance();
        mapsAction = MapsAction.getInstance();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        geofenceInterf = new GeofenceService();
        geofenceInterf.build(this);
        buildLayoutsReference();
        mapFragment.getMapAsync(this);


    }

    public void atualizar(Location location) {
        Double latPoint = location.getLatitude();
        Double lngPoint = location.getLongitude();

        txtLatitude.setText(latPoint.toString());
        txtLongitude.setText(lngPoint.toString());
    }

    public void configurarServico() {
        try {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            mMap.setMyLocationEnabled(true);
            LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    coordenate = new Coordenate(location.getLatitude(), location.getLongitude());
                    coordenateListener.setCoordenate(coordenate);
                    atualizar(location);
                }

                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                public void onProviderEnabled(String provider) {
                }

                public void onProviderDisabled(String provider) {
                }
            };
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
        } catch (SecurityException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void pedirPermissoes() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else
            configurarServico();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    configurarServico();
                } else {
                    Toast.makeText(this, "Não vai funcionar!!!", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mapsAction.setGoogleMap(mMap);
        fireBaseInterf = FireBaseService.getInstance();
        fireBaseInterf.buildConfiguration();
        pedirPermissoes();
        geofenceInterf.addLocationAlert(-23.311251666666667, -46.006620000000005);
    }
}
