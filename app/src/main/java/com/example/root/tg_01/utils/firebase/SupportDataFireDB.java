package com.example.root.tg_01.utils.firebase;

import com.example.root.tg_01.models.Coordenate;
import com.example.root.tg_01.utils.interfaces.SupportData;

public class SupportDataFireDB implements SupportData{

    public String getDatabaseNameCoordenate() {
        return "coordenates";
    }

    public String getDatabaseNamePedidos() {
        return "pedidos";
    }

    public String getApiKey() {
        return null;
    }

    public String getBaseUrl() {
        return "https://tcc01-6e0dd.firebaseio.com/" + getDatabaseNameCoordenate() + ".json";
    }

    public String apiKeyUrl() {
        return "?apiKey=" + getApiKey();
    }

    public String collectionName() {
        return "coordenates";
    }

    public String buildContactsSaveURL() {
        return getBaseUrl();
    }

    public String buildContactsFetchURL() {
        return getBaseUrl() + collectionName() + apiKeyUrl();
    }

    public String createCoordenate(Coordenate coordenate) {
        return String
                .format("{\"latitude\": \"%s\", "
                                + "\"longitude\": \"%s\", " + "\"tipo\": \"%s\"}",
                        coordenate.getLatitude(), coordenate.getLongitude(), coordenate.getTipo());
    }

}
