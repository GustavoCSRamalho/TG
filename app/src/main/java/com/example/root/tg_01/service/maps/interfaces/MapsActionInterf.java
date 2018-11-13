package com.example.root.tg_01.service.maps.interfaces;

import com.example.root.tg_01.models.Coordenate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public interface MapsActionInterf {

    void setGoogleMap(GoogleMap map);

    void addMarker(LatLng latLng, Coordenate coordenate);

    void clear();
}
