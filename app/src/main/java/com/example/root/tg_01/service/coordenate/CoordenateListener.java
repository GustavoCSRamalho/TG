package com.example.root.tg_01.service.coordenate;

import com.example.root.tg_01.models.Coordenate;
import com.example.root.tg_01.service.coordenate.interfaces.CoordenateListenerInterf;

public class CoordenateListener implements CoordenateListenerInterf {

    private static CoordenateListener coordenateListener;

    private  Coordenate coordenate;

    protected CoordenateListener() {
    }

    public static CoordenateListener getInstance() {
        if (coordenateListener == null) {
            coordenateListener = new CoordenateListener();
        }
        return coordenateListener;
    }

    @Override
    public Coordenate getCoordenate() {
        if (coordenateListener != null) {
//            synchronized (coordenateListener.coordenate){
                return coordenateListener.coordenate;
//            }
        }
        return null;
    }

    @Override
    public void setCoordenate(Coordenate coordenate) {
        if (coordenateListener != null) {
//            synchronized (coordenateListener.coordenate){
                coordenateListener.coordenate = coordenate;
//            }
        }
    }
}
