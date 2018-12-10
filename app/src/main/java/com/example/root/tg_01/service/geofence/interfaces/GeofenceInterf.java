package com.example.root.tg_01.service.geofence.interfaces;

import android.app.PendingIntent;
import android.content.Context;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;

public interface GeofenceInterf {

    Geofence getGeofence(double lat, double lang, String key);

    GeofencingRequest getGeofencingRequest(Geofence geofence);

    PendingIntent getGeofencePendingIntent();

    boolean isLocationAccessPermitted();

    void requestLocationAccessPermission();

    void build(Context context);

    void addLocationAlert(double lat, double lng);
}
