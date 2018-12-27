package com.example.root.tg_01.service.database.firebase;

import android.support.annotation.NonNull;

import com.example.root.tg_01.models.Coordenate;
import com.example.root.tg_01.models.Pedido;
import com.example.root.tg_01.models.Usuario;
import com.example.root.tg_01.service.database.firebase.interfaces.FireBaseInterf;
import com.example.root.tg_01.service.maps.MapsAction;
import com.example.root.tg_01.service.maps.interfaces.MapsActionInterf;
import com.example.root.tg_01.utils.firebase.SupportDataFireDB;
import com.example.root.tg_01.utils.interfaces.SupportData;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
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

    private DatabaseReference dataBaseRefCoordenate;

    private DatabaseReference dataBaseRefPedidos;

    private DatabaseReference dataBaseRefUsuarios;

    private FirebaseAuth firebaseAuth;

    private FirebaseDatabase database;

    private List<Coordenate> coordenatelist;

    private List<Pedido> pedidolist;

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


    public void saveCoordenateData(Coordenate coordenate) {

        fireBaseService.dataBaseRefCoordenate.push().setValue(coordenate);

    }

    public void buildConfiguration() {
        fireBaseService.supportData = new SupportDataFireDB();
        database = FirebaseDatabase.getInstance();

        setDataBaseRefCoordenate();
        setDataBaseRefPedidos();
        setDataBaseRefUsuarios();

        setFireBaseAuth();

        setAllCoordenateData();
        setListenerToCoordenateDataBase();
    }

    @Override
    public FirebaseAuth getFirebaseAuth() {
        if (firebaseAuth == null) {
            firebaseAuth = FirebaseAuth.getInstance();
        }
        return firebaseAuth;
    }

    @Override
    public void saveUsuarioData(Usuario usuario) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        fireBaseService.dataBaseRefUsuarios = database.getReference("usuarios");
        fireBaseService.dataBaseRefUsuarios.push().setValue(usuario);
    }

    public void setPedidoDataListener() {
        fireBaseService.dataBaseRefPedidos.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pedidolist = new ArrayList<>();
                // Result will be holded Here
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Pedido c = new Gson().fromJson(String.valueOf(dsp.getValue()), Pedido.class);
                    pedidolist.add(c); //add result into array list
                    System.out.println("Achei um pedido");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public Pedido getPedidoData(Pedido pedido) {
        return null; // TODO : Finalizar
    }

    public void setAllCoordenateData() {
        fireBaseService.dataBaseRefCoordenate.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mapsActivity.clear();
                coordenatelist = new ArrayList<>();
                // Result will be holded Here
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Coordenate c = new Gson().fromJson(String.valueOf(dsp.getValue()), Coordenate.class);
                    coordenatelist.add(c); //add result into array list
                    System.out.println(c.getTipo());

                }
                for (Coordenate coordenate : coordenatelist) {
                    mapsActivity.addMarker(new LatLng(coordenate.getLatitude(), coordenate.getLongitude()), coordenate);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public List getAllCoordenateData() {
        return coordenatelist;
    }

    public void setListenerToCoordenateDataBase() {

        fireBaseService.dataBaseRefCoordenate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mapsActivity.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Coordenate c = new Gson().fromJson(String.valueOf(dsp.getValue()), Coordenate.class);
                    coordenatelist.add(c); //add result into array list
                    System.out.println(c.getTipo());

                }
                for (Coordenate coordenate : coordenatelist) {
                    mapsActivity.addMarker(new LatLng(coordenate.getLatitude(), coordenate.getLongitude()), coordenate);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void setDataBaseRefCoordenate() {
        fireBaseService.dataBaseRefCoordenate = database.getReference(supportData.getDatabaseNameCoordenate());

    }

    @Override
    public void setDataBaseRefPedidos() {
        fireBaseService.dataBaseRefPedidos = database.getReference(supportData.getDatabaseNamePedidos());


    }

    @Override
    public void setDataBaseRefUsuarios() {
        fireBaseService.dataBaseRefUsuarios = database.getReference("usuarios");
    }

    @Override
    public void setFireBaseAuth() {
        firebaseAuth = FirebaseAuth.getInstance();
    }


}
