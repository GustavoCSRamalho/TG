package com.example.root.tg_01.service.button;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.root.tg_01.main.InformationActivity;
import com.example.root.tg_01.models.Coordenate;
import com.example.root.tg_01.service.button.interfaces.ButtonActionInterf;
import com.example.root.tg_01.service.coordenate.CoordenateListener;
import com.example.root.tg_01.service.coordenate.interfaces.CoordenateListenerInterf;
import com.example.root.tg_01.service.database.firebase.FireBaseService;
import com.example.root.tg_01.service.database.firebase.interfaces.FireBaseInterf;

public class ButtonAction implements ButtonActionInterf {

    public static ButtonAction buttonAction;
    private CoordenateListenerInterf coordenateListener;
    private Coordenate coordenate;
    private FireBaseInterf fireBaseInterf;

    protected ButtonAction() {
        coordenateListener = CoordenateListener.getInstance();
        fireBaseInterf = FireBaseService.getInstance();
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
                if (coordenate == null) {
                    Toast.makeText(context, "Aguarde um momento!", Toast.LENGTH_SHORT).show();
                } else {
                    coordenate.setTipo("Assedio");
                    fireBaseInterf.saveData(coordenate);
                    Toast.makeText(context, "Saved to MongoDB!! Assedio!" + coordenate.getLatitude() + ": "
                            + coordenate.getLongitude(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void botaoAmeaca(Button botaoAmeaca, final Context context) {
        botaoAmeaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coordenate = coordenateListener.getCoordenate();
                if (coordenate == null) {
                    Toast.makeText(context, "Aguarde um momento!", Toast.LENGTH_SHORT).show();
                } else {
                    coordenate.setTipo("Ameaca");
                    fireBaseInterf.saveData(coordenate);
                    Toast.makeText(context, "Saved to MongoDB!! Ameaca!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void botaoDanomoral(Button botaoDanomoral, final Context context) {
        botaoDanomoral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coordenate = coordenateListener.getCoordenate();
                if (coordenate == null) {
                    Toast.makeText(context, "Aguarde um momento!", Toast.LENGTH_SHORT).show();
                } else {
                    coordenate.setTipo("Danomoral");
                    fireBaseInterf.saveData(coordenate);
                    Toast.makeText(context, "Saved to MongoDB!! Danomoral!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void botaoInformacao(final Button botaoInformacao, final Context context) {
        botaoInformacao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println("Botao : "+botaoInformacao);
                Intent intent = new Intent(context, InformationActivity.class);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public void buildButoes(Button[] butoes, Context context) {
        botaoAssedioAction(butoes[0], context);
        botaoAmeaca(butoes[1], context);
        botaoDanomoral(butoes[2], context);
        botaoInformacao(butoes[3],context);
    }
}
