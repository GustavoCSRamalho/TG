package com.example.root.tg_01.service.database.firebase.interfaces;

import com.example.root.tg_01.models.Coordenate;

import java.util.List;

public interface FireBaseInterf {

    void saveData(Coordenate coordenate);

    void buildConfiguration();

    void setAllData();

    List getAllData();

    void setListenerToDataBase();
}
