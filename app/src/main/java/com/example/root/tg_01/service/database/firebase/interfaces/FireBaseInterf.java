package com.example.root.tg_01.service.database.firebase.interfaces;

import com.example.root.tg_01.models.Coordenate;
import com.example.root.tg_01.models.Pedido;
import com.example.root.tg_01.models.Usuario;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public interface FireBaseInterf {

    FirebaseAuth getFirebaseAuth();

    void saveUsuarioData(Usuario usuario);

    void setPedidoDataListener();

    Pedido getPedidoData(Pedido pedido);

    void saveCoordenateData(Coordenate coordenate);

    void buildConfiguration();

    void setAllCoordenateData();

    List getAllCoordenateData();

    void setListenerToCoordenateDataBase();

    void  setDataBaseRefCoordenate();
    void  setDataBaseRefPedidos();
    void  setDataBaseRefUsuarios();
    void  setFireBaseAuth();
}
