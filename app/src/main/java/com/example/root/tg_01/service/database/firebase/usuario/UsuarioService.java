package com.example.root.tg_01.service.database.firebase.usuario;

import android.support.annotation.NonNull;

import com.example.root.tg_01.models.Usuario;
import com.example.root.tg_01.service.database.firebase.FireBaseService;
import com.example.root.tg_01.service.database.firebase.interfaces.FireBaseInterf;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class UsuarioService {

    protected static UsuarioService usuarioService;

    protected FireBaseInterf fireBase;
    protected DatabaseReference databaseReference;

    protected List<Usuario> usuarios;

    protected UsuarioService() {
        fireBase = FireBaseService.getInstance();
    }

    public static UsuarioService getInstance() {
        if (usuarioService == null) {
            usuarioService = new UsuarioService();
        }
        return usuarioService;
    }

    public void setDatabaseReference() {
        FirebaseDatabase firebaseDatabase = usuarioService.fireBase.getFirebaseDatabase();
        usuarioService.databaseReference = firebaseDatabase.getReference("usuarios");
    }

    public DatabaseReference getDatabaseReference() {
        if (usuarioService.databaseReference == null) {
            usuarioService.setDatabaseReference();
        }
        return usuarioService.databaseReference;
    }

    public void checkListaDeUsuarios() {
        if (usuarioService.usuarios == null) {
            usuarioService.usuarios = new ArrayList<>();
            DatabaseReference reference = usuarioService.getDatabaseReference();
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {

                        Usuario usuario = new Gson().fromJson(String.valueOf(dsp.getValue()), Usuario.class);
                        usuarios.add(usuario);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    public void setEmpresaListener() {
        checkListaDeUsuarios();
        DatabaseReference reference = usuarioService.getDatabaseReference();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("Achei ");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public Usuario getUsuarioEspecifico(String id){
        Usuario usuarioFound = null;
        for(Usuario usuario : usuarios){
            if(usuario.getUid().equals(id)){
                usuarioFound = usuario;
            }
        }
        return usuarioFound;
    }

}
