package com.example.root.tg_01.utils;

import com.example.root.tg_01.models.Coordenate;

public class SupportData {

    public String getDatabaseName() {
        return "coordenates";
    }

    public String getApiKey() {
        return "jJXag8fCX2q0HsF9QDmS0i3MpWtsZlHa";
    }

    public String getBaseUrl()
    {
        return "https://tcc01-6e0dd.firebaseio.com/"+getDatabaseName()+"/dados.json";
    }
// public String getBaseUrl()
//    {
//        return "https://api.mlab.com/api/1/databases/"+getDatabaseName()+"/collections/";
//    }

    public String apiKeyUrl()
    {
        return "?apiKey="+getApiKey();
    }

    public String collectionName()
    {
        return "coordenates";
    }

    public String buildContactsSaveURL()
    {
        return getBaseUrl();
    }
//    public String buildContactsSaveURL()
//    {
//        return getBaseUrl()+collectionName()+apiKeyUrl();
//    }

    public String buildContactsFetchURL()
    {
        return getBaseUrl()+collectionName()+apiKeyUrl();
    }

    public String createCoordenate(Coordenate coordenate) {
        return String
                .format("{\"latitude\": \"%s\", "
                                + "\"longitude\": \"%s\", " + "\"tipo\": \"%s\"}",
                        coordenate.getLatitude(), coordenate.getLongitude(), coordenate.getTipo());
    }

}
