package com.example.root.tg_01.utils.interfaces;

import com.example.root.tg_01.models.Coordenate;

public interface SupportData {

    String getDatabaseNameCoordenate();

    String getDatabaseNamePedidos();

    String getApiKey();

    String getBaseUrl();

    String apiKeyUrl();

    String collectionName();

    String buildContactsSaveURL();

    String buildContactsFetchURL();

    String createCoordenate(Coordenate coordenate);
}
