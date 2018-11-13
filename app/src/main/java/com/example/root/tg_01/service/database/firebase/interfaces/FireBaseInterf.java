package com.example.root.tg_01.service.database.firebase.interfaces;

import com.example.root.tg_01.models.Coordenate;

public interface FireBaseInterf {

    void saveData(Coordenate coordenate);

    void buildConfiguration();

    void setAllData();

    void setListenerToDataBase();
}
