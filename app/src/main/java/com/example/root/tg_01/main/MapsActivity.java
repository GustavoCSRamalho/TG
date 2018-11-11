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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.tg_01.models.Coordenate;
import com.example.root.tg_01.R;
import com.example.root.tg_01.service.button.ButtonAction;
import com.example.root.tg_01.service.button.interfaces.ButtonActionInterf;
import com.example.root.tg_01.service.coordenate.CoordenateListener;
import com.example.root.tg_01.service.coordenate.interfaces.CoordenateListenerInterf;
import com.example.root.tg_01.service.database.GetCoordenatesAsyncTask;
import com.example.root.tg_01.service.maps.MapsAction;
import com.example.root.tg_01.service.maps.interfaces.MapsActionInterf;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.mongodb.client.MongoCollection;

import org.bson.Document;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    TextView txtLatitude, txtLongitude;
    private volatile GoogleMap mMap;
    private TextView textView;
    private volatile LocationManager locationManager;

    private volatile LocationListener locationListener;

    private volatile MongoCollection<Document> coll;

    private volatile Coordenate coordenate;

    private Button botaoAssedio;

    private Button botaoAmeaca;

    private Button botaoDanomoral;

    private ArrayList<Coordenate> returnValues = new ArrayList<Coordenate>();

    private ButtonActionInterf buttonAction;

    private CoordenateListenerInterf coordenateListener;

    private MapsActionInterf mapsAction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        coordenateListener = CoordenateListener.getInstance();
        mapsAction = MapsAction.getInstance();
        buttonAction = ButtonAction.getInstance();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        txtLatitude = (TextView) findViewById(R.id.txtLatitude);
        txtLongitude = (TextView) findViewById(R.id.txtLongitude);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        textView = (TextView) findViewById(R.id.textView);

        botaoAssedio = (Button) findViewById(R.id.assedio);
        botaoAmeaca = (Button) findViewById(R.id.ameaca);
        botaoDanomoral = (Button) findViewById(R.id.danomoral);

        buttonAction.botaoAmeaca(botaoAmeaca, MapsActivity.this);
        buttonAction.botaoAssedioAction(botaoAssedio,MapsActivity.this);
        buttonAction.botaoDanomoral(botaoDanomoral,MapsActivity.this);
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
                    LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
//                    mMap.addMarker(new MarkerOptions().position(sydney).title("Casa"));
//                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                    coordenate = new Coordenate();
                    coordenate.setLatitude(location.getLatitude());
                    coordenate.setLongitude(location.getLongitude());
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

        pedirPermissoes();


        GetCoordenatesAsyncTask getTask = new GetCoordenatesAsyncTask();

        try {
            returnValues = getTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("saida : " + returnValues.size());
        for (Coordenate coordenate : returnValues) {
            LatLng sydney = new LatLng(coordenate.getLatitude(), coordenate.getLongitude());
            mapsAction.addMarker(sydney,coordenate);
//            mMap.addMarker(new MarkerOptions().position(sydney).title(coordenate.getTipo()));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
