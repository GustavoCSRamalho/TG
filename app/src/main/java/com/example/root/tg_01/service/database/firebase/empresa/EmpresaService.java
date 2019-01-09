package com.example.root.tg_01.service.database.firebase.empresa;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.root.tg_01.models.Empresa;
import com.example.root.tg_01.models.Pedido;
import com.example.root.tg_01.models.Usuario;
import com.example.root.tg_01.service.database.firebase.FireBaseService;
import com.example.root.tg_01.service.database.firebase.interfaces.FireBaseInterf;
import com.example.root.tg_01.utils.Usuario_logado;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmpresaService {

    private static EmpresaService empresaService;

    protected FireBaseInterf fireBase;
    protected DatabaseReference databaseReference;
    protected List<Empresa> listaDeEmpresa;

    protected EmpresaService() {
        fireBase = FireBaseService.getInstance();
    }

    public static EmpresaService getInstance() {
        if (empresaService == null) {
            empresaService = new EmpresaService();
        }
        return empresaService;
    }

    public void setDatabaseReference() {
        FirebaseDatabase firebaseDatabase = empresaService.fireBase.getFirebaseDatabase();
        empresaService.databaseReference = firebaseDatabase.getReference("empresa");
    }

    public DatabaseReference getDatabaseReference() {
        if (empresaService.databaseReference == null) {
            empresaService.setDatabaseReference();
        }
        return empresaService.databaseReference;
    }

    public Empresa setOne(final Context context) {
        List<Pedido> pedidos = new ArrayList<>();
        Pedido pedido = new Pedido();
        pedido.setDescricao("Descricao");
        pedido.setEmpresa("Eldourado");
        pedido.setDestino("Jacarei");
        pedido.setNome("Nome");


        List<Usuario> usuarios = new ArrayList<>();


        Usuario usuario2 = new Usuario();
        usuario2.setEmail("Gustavo@hotmail");
        usuario2.setUid("1");
        usuario2.setName("Gustavo");
        usuario2.setPassword("123");
        usuario2.setType(0);

        usuarios.add(usuario2);

        usuario2 = new Usuario();
        usuario2.setEmail("Teste123@hotmail");
        usuario2.setUid("2");
        usuario2.setName("Gustavo123");
        usuario2.setPassword("123456");
        usuario2.setType(0);
//        usuarios2.add(usuario2);

        usuarios.add(usuario2);


        pedido.setCaminhoneiros(usuarios);

        pedidos.add(pedido);


        Pedido pedido2 = new Pedido();
        pedido2.setDescricao("Descricao");
        pedido2.setEmpresa("Eldourado");
        pedido2.setDestino("Jacarei");
        pedido2.setNome("Nome2");


        List<Usuario> usuarios2 = new ArrayList<>();

        usuario2 = new Usuario();
        usuario2.setEmail("Gustavo@hotmail");
        usuario2.setUid("1");
        usuario2.setName("Gustavo");
        usuario2.setPassword("123");
        usuario2.setType(0);
        usuarios2.add(usuario2);
        usuario2 = new Usuario();
        usuario2.setEmail("Teste123@hotmail");
        usuario2.setUid("2");
        usuario2.setName("Gustavo123");
        usuario2.setPassword("123456");
        usuario2.setType(0);
        usuarios2.add(usuario2);


        pedido2.setCaminhoneiros(usuarios2);

        pedidos.add(pedido2);


        Empresa empresa = new Empresa();
        empresa.setNome("Eldourado");
        empresa.setListaDePedidos(pedidos);

        return empresa;

//        DatabaseReference reference = getDatabaseReference();
//        reference.push().setValue(empresa).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    Toast.makeText(context, "Deu certo", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(context, "Deu errado", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
    }


    public void checkListaDeEmpresa() {
        if (empresaService.listaDeEmpresa == null) {
            empresaService.listaDeEmpresa = new ArrayList<>();
            DatabaseReference reference = empresaService.getDatabaseReference();
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();

                    if (map != null) {
                        System.out.println(map.values());

                        for(Object value : map.values()){
                            System.out.println(value);
                            Map<String,Object> map1 = (Map<String, Object>) value;
                            for(Object obj1: map1.values()){
                                Map<String,Object> map2 = (Map<String, Object>) obj1;
                                for(Object object : ((Map<String, Object>) obj1).values()){
//                                    Empresa empresa = (Empresa) object;
                                    System.out.println(object.toString());
                                    Empresa usuario = new Gson().fromJson(object.toString(), Empresa.class);
                                    System.out.println("Nome : "+usuario.getNome());
                                    System.out.println("Pronto ok");
                                }
//                                Empresa empresa = (Empresa) object;
                            }
                            System.out.println(map1.values());

                            System.out.println("^");
                        }
//                        List<Object> map2 = (List<Object>) map.values();

                        System.out.println("Até aqui ok");
//                        System.out.println(map2);
//                        for (Object dsp : map.values()) {
//                            Empresa empresa = (Empresa) dsp;
//                            System.out.println("Empresa");
//                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
//
//                        Empresa empresa = new Gson().fromJson(String.valueOf(dsp.getValue()), Empresa.class);
//                        System.out.println("Empresa : " + empresa.getNome());
//                        System.out.println("Pedidos: ");
//                        for (Pedido p : empresa.getListaDePedidos()) {
//                            System.out.println("Descricao : " + p.getDescricao());
//                        }
//                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }


    public void setEmpresaListener() {
        checkListaDeEmpresa();
        DatabaseReference reference = empresaService.getDatabaseReference();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                System.out.println("Achei ");
//                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
//
//                    Empresa empresa = new Gson().fromJson(String.valueOf(dsp.getValue()), Empresa.class);
//                    System.out.println("Empresa : " + empresa.getNome());
//                    System.out.println("Pedidos: ");
//                    for (Pedido p : empresa.getListaDePedidos()) {
//                        System.out.println("Descricao : " + p.getDescricao());
//                    }
//                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public Empresa getEmpresaEspecifica(String nome) {
        Empresa empresaEncontrada = null;
        for (Empresa empresa : listaDeEmpresa) {
            if (empresa.getNome().equals(nome)) {
                empresaEncontrada = empresa;
            }
        }

        return empresaEncontrada;
    }

    public void saveEmpresa(Empresa empresa,final Context context){
        Usuario_logado usuario_logado = Usuario_logado.getInstance();
        Map<String,Empresa> map = new HashMap<>();
//        map.put(usuario_logado.getUsuario().getName(),empresa);
        map.put("eldourado",empresa);

        DatabaseReference reference = empresaService.getDatabaseReference();
        reference.child("eldourado").push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    System.out.println("Deu certo");
                    Toast.makeText(context, "Deu certo", Toast.LENGTH_LONG).show();
                } else {
                    System.out.println("Deu errado");
                    Toast.makeText(context, "Deu errado", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
