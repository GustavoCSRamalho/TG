package com.example.root.tg_01.models;

public class Coordenate {

    public Coordenate(){}

    public Coordenate(double latitude,double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private double longitude;

    private double latitude;

    private String tipo;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
