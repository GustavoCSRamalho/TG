package com.example.root.tg_01.service.button;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.root.tg_01.models.Coordenate;
import com.example.root.tg_01.service.button.interfaces.ButtonActionInterf;
import com.example.root.tg_01.service.coordenate.CoordenateListener;
import com.example.root.tg_01.service.coordenate.interfaces.CoordenateListenerInterf;
import com.example.root.tg_01.service.database.MongoLabSaveCoordenate;

public class ButtonAction implements ButtonActionInterf {

    public static ButtonAction buttonAction;
    private CoordenateListenerInterf coordenateListener;
    private Coordenate coordenate;

    protected ButtonAction() {
        coordenateListener = CoordenateListener.getInstance();
    }

    public static ButtonAction getInstance() {
        if (buttonAction == null) {
            buttonAction = new ButtonAction();
        }
        return buttonAction;
    }


    @Override
    public void botaoAssedioAction(Button botaoAssedio, final Context context) {
        botaoAssedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coordenate = coordenateListener.getCoordenate();
                coordenate.setTipo("Assédio");

                MongoLabSaveCoordenate tsk = new MongoLabSaveCoordenate();
                tsk.execute(coordenate);
                Toast.makeText(context, "Saved to MongoDB!! Assedio!" + coordenate.getLatitude() + ": "
                        + coordenate.getLongitude(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void botaoAmeaca(Button botaoAmeaca, final Context context) {
        botaoAmeaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coordenate = coordenateListener.getCoordenate();
                coordenate.setTipo("Ameaca");

                MongoLabSaveCoordenate tsk = new MongoLabSaveCoordenate();
                tsk.execute(coordenate);
                Toast.makeText(context, "Saved to MongoDB!! Ameaca!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void botaoDanomoral(Button botaoDanomoral, final Context context) {
        botaoDanomoral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coordenate = coordenateListener.getCoordenate();
                coordenate.setTipo("Danomoral");

                MongoLabSaveCoordenate tsk = new MongoLabSaveCoordenate();
                tsk.execute(coordenate);
                Toast.makeText(context, "Saved to MongoDB!! Danomoral!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}