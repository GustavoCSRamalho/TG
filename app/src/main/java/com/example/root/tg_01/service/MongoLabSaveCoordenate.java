package com.example.root.tg_01.service;

import android.os.AsyncTask;
import android.util.Log;

import com.example.root.tg_01.Coordenate;
import com.example.root.tg_01.SupportData;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MongoLabSaveCoordenate extends AsyncTask<Object, Void, Boolean> {

    @Override
    protected Boolean doInBackground(Object... params) {
        Coordenate contact = (Coordenate) params[0];
        Log.d("contact", ""+contact);

        try {


            SupportData sd = new SupportData();
            URL url = new URL(sd.buildContactsSaveURL());

            Log.d("url", ""+url);

            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type",
                    "application/json");
            connection.setRequestProperty("Accept", "application/json");

            OutputStreamWriter osw = new OutputStreamWriter(
                    connection.getOutputStream());

            osw.write(sd.createCoordenate(contact));
            osw.flush();
            osw.close();

            Log.d("Response code", ""+(connection.getResponseCode()));
            if(connection.getResponseCode() <205)
            {

                return true;
            }
            else
            {
                return false;

            }

        } catch (Exception e) {
            e.getMessage();
            Log.d("Got error", e.getMessage());
            return false;

        }

    }

}
