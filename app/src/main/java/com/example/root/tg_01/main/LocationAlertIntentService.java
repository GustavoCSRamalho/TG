package com.example.root.tg_01.main;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.root.tg_01.R;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocationAlertIntentService extends IntentService {
    private static final String IDENTIFIER = "LocationAlertIS";

//    private Intent intent;

    public LocationAlertIntentService() {
        super(IDENTIFIER);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
//        this.intent = intent;
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
//
        if (geofencingEvent.hasError()) {
            Log.e(IDENTIFIER, "Error Location" + getErrorString(geofencingEvent.getErrorCode()));
            return;
        }
//
        Log.i(IDENTIFIER,"Geofencing : "+ geofencingEvent.toString());
//
        int geofenceTransition = geofencingEvent.getGeofenceTransition();
        System.out.println(""+geofenceTransition);
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geofenceTransition == Geofence.GEOFENCE_TRANSITION_DWELL) {
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
            String transitionDetails = getGeofenceTransitionInfo(
                    triggeringGeofences);
            String transitionType = getTransitionString(geofenceTransition);

            notifyLocationAlert(transitionType, transitionDetails);
        }



        Intent myIntent = new Intent(this, service_information.class);
        myIntent.putExtra("empresa","Eldourado");
        myIntent.putExtra("destino","Jacarei");
        myIntent.putExtra("descricao","Carga muito pesada");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        String CHANNEL_ID = "Zoftino";
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                        .setContentIntent(pendingIntent)
                        .setContentTitle(intent.getStringExtra("empresa"))
                        .setContentText(intent.getStringExtra("destino"))
                ;

        builder.setAutoCancel(true);


        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(0, builder.build());

        System.out.println("Euuuuuu");
    }

    private String getGeofenceTransitionInfo(List<Geofence> triggeringGeofences) {
        ArrayList<String> locationNames = new ArrayList<>();
        for (Geofence geofence : triggeringGeofences) {
            locationNames.add(getLocationName(geofence.getRequestId()));
        }
        String triggeringLocationsString = TextUtils.join(", ", locationNames);

        return triggeringLocationsString;
    }

    private String getLocationName(String key) {
        String[] strs = key.split("-");

        String locationName = null;
        if (strs != null && strs.length == 2) {
            double lat = Double.parseDouble(strs[0]);
            double lng = Double.parseDouble(strs[1]);

            locationName = getLocationNameGeocoder(lat, lng);
        }
        if (locationName != null) {
            return locationName;
        } else {
            return key;
        }
    }

    private String getLocationNameGeocoder(double lat, double lng) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocation(lat, lng, 1);
        } catch (Exception ioException) {
            Log.e("", "Error in getting location name for the location");
        }

        if (addresses == null || addresses.size() == 0) {
            Log.d("", "no location name");
            return null;
        } else {
            Address address = addresses.get(0);
            ArrayList<String> addressInfo = new ArrayList<>();
            for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                addressInfo.add(address.getAddressLine(i));
            }

            return TextUtils.join(System.getProperty("line.separator"), addressInfo);
        }
    }

    private String getErrorString(int errorCode) {
        switch (errorCode) {
            case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE:
                return "Geofence not available";
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
                return "geofence too many_geofences";
            case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
                return "geofence too many pending_intents";
            default:
                return "geofence error";
        }
    }

    private String getTransitionString(int transitionType) {
        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                return "location entered";
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                return "location exited";
            case Geofence.GEOFENCE_TRANSITION_DWELL:
                return "dwell at location";
            default:
                return "location transition";
        }
    }

    private void notifyLocationAlert(String locTransitionType, String locationDetails) {

        System.out.println("Type : "+locTransitionType);
        System.out.println("Details : "+locationDetails);
//        Toast.makeText(this,
//                "Location alter could not be added",
//                Toast.LENGTH_SHORT).show();

        Log.d("Testando","Foiiii saporaa : "+locationDetails.toString());

        String CHANNEL_ID = "Zoftino";
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                        .setContentTitle(locTransitionType)
                        .setContentText(locationDetails);

        builder.setAutoCancel(true);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(0, builder.build());
    }

}
