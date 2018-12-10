package com.example.root.tg_01.service.database.firebase;

import android.support.annotation.NonNull;

import com.example.root.tg_01.models.Coordenate;
import com.example.root.tg_01.service.database.firebase.interfaces.FireBaseInterf;
import com.example.root.tg_01.service.maps.MapsAction;
import com.example.root.tg_01.service.maps.interfaces.MapsActionInterf;
import com.example.root.tg_01.utils.firebase.SupportDataFireDB;
import com.example.root.tg_01.utils.interfaces.SupportData;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class FireBaseService implements FireBaseInterf {

    private static FireBaseService fireBaseService;

    private SupportData supportData;

    private DatabaseReference dataBaseRef;

    private List<Coordenate> coordenatelist;

    private MapsActionInterf mapsActivity;

    protected FireBaseService() {
        mapsActivity = MapsAction.getInstance();
    }

    public static FireBaseService getInstance() {
        if (fireBaseService == null) {
            fireBaseService = new FireBaseService();
        }
        return fireBaseService;
    }

    public void saveData(Coordenate coordenate) {
        fireBaseService.dataBaseRef.push().setValue(coordenate);
    }

    public void buildConfiguration() {
        fireBaseService.supportData = new SupportDataFireDB();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        fireBaseService.dataBaseRef = database.getReference(supportData.getDatabaseName());
        setAllData();
        setListenerToDataBase();
    }

    public void setAllData(){
        fireBaseService.dataBaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mapsActivity.clear();
                coordenatelist = new ArrayList<>();
                // Result will be holded Here
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Coordenate c = new Gson().fromJson(String.valueOf(dsp.getValue()),Coordenate.class);
                    coordenatelist.add(c); //add result into array list
                    System.out.println(c.getTipo());

                }
                for(Coordenate coordenate : coordenatelist){
                    mapsActivity.addMarker(new LatLng(coordenate.getLatitude(),coordenate.getLongitude()),coordenate);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public List getAllData() {
        return coordenatelist;
    }

    public void setListenerToDataBase(){

        fireBaseService.dataBaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mapsActivity.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Coordenate c = new Gson().fromJson(String.valueOf(dsp.getValue()),Coordenate.class);
                    coordenatelist.add(c); //add result into array list
                    System.out.println(c.getTipo());

                }
                for(Coordenate coordenate : coordenatelist){
                    mapsActivity.addMarker(new LatLng(coordenate.getLatitude(),coordenate.getLongitude()),coordenate);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
