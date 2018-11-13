package com.example.root.tg_01.utils.mongodb;

import com.example.root.tg_01.models.Coordenate;
import com.example.root.tg_01.utils.interfaces.SupportData;

public class SupportDataMongoDB implements SupportData{

    public String getDatabaseName() {
        return "coordenates";
    }

    public String getApiKey() {
        return "jJXag8fCX2q0HsF9QDmS0i3MpWtsZlHa";
    }


    public String getBaseUrl() {
        return "https://api.mlab.com/api/1/databases/" + getDatabaseName() + "/collections/";
    }

    public String apiKeyUrl() {
        return "?apiKey=" + getApiKey();
    }

    public String collectionName() {
        return "coordenates";
    }

    public String buildContactsSaveURL() {
        return getBaseUrl() + collectionName() + apiKeyUrl();
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
