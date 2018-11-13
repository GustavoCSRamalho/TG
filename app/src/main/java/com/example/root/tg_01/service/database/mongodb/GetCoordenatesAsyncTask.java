package com.example.root.tg_01.service.database.mongodb;

import android.os.AsyncTask;

import com.example.root.tg_01.models.Coordenate;
import com.example.root.tg_01.utils.interfaces.SupportData;
import com.example.root.tg_01.utils.mongodb.SupportDataMongoDB;
import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GetCoordenatesAsyncTask extends AsyncTask<Coordenate, Void, ArrayList<Coordenate>> {

    static String server_output = null;
    static String temp_output = null;

    @Override
    protected ArrayList<Coordenate> doInBackground(Coordenate... arg0) {

        ArrayList<Coordenate> coordenateArrayList = new ArrayList<Coordenate>();
        try {
            SupportData sd = new SupportDataMongoDB();
            URL url = new URL(sd.buildContactsFetchURL());
            HttpURLConnection conn = (HttpURLConnection) url
                    .openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            while ((temp_output = br.readLine()) != null) {
                server_output = temp_output;
            }

            String mongoarray = "{ DB_output: " + server_output + "}";
            System.out.println(mongoarray);
            Object o = com.mongodb.util.JSON.parse(mongoarray);

            DBObject dbObj = (DBObject) o;
            BasicDBList contacts = (BasicDBList) dbObj.get("DB_output");
            for (Object obj : contacts) {
                DBObject userObj = (DBObject) obj;

                Coordenate temp = new Coordenate();
                temp.setLongitude(Double.parseDouble(userObj.get("longitude").toString()));
                temp.setLatitude(Double.parseDouble(userObj.get("latitude").toString()));
                temp.setTipo(userObj.get("tipo").toString());
                coordenateArrayList.add(temp);
                System.out.println("ok");

            }

        } catch (Exception e) {
            e.getMessage();
        }

        return coordenateArrayList;
    }
}
