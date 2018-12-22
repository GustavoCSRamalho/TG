package com.example.root.tg_01.service.database.firebase.interfaces;

import com.example.root.tg_01.models.Coordenate;
import com.example.root.tg_01.models.Pedido;

import java.util.List;

public interface FireBaseInterf {

    void setPedidoDataListener();

    Pedido getPedidoData(Pedido pedido);

    void savecCoordenateData(Coordenate coordenate);

    void buildConfiguration();

    void setAllCoordenateData();

    List getAllCoordenateData();

    void setListenerToCoordenateDataBase();
}
