package com.example.root.tg_01.main;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.root.tg_01.R;
import com.example.root.tg_01.models.Coordenate;
import com.example.root.tg_01.service.database.firebase.FireBaseService;
import com.example.root.tg_01.service.database.firebase.interfaces.FireBaseInterf;

import java.util.List;

public class InformationActivity extends Activity {

    private TextView ameaca, assedio, danomoral;

    private FireBaseInterf fireBaseInterf;

    private List<Coordenate> allData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information);
        fireBaseInterf = FireBaseService.getInstance();
        ameaca =  findViewById(R.id.ameacaText);
        assedio = findViewById(R.id.assedioText);
        danomoral = findViewById(R.id.danomoralText);
        System.out.println("botao : "+danomoral);


        allData = fireBaseInterf.getAllData();
        int danomoralCount = 0;
        int assedioCount = 0;
        int ameacaCount = 0;
        for (Coordenate coordenate : allData) {
            if (coordenate.getTipo().equalsIgnoreCase("danomoral")) {
                danomoralCount++;
            } else if (coordenate.getTipo().equalsIgnoreCase("ameaca")) {
                ameacaCount++;
            }else if(coordenate.getTipo().equalsIgnoreCase("assedio")){
                assedioCount++;
            }
        }

        danomoral.setText(String.valueOf(danomoralCount));
        assedio.setText(String.valueOf(assedioCount));
        ameaca.setText(String.valueOf(ameacaCount));

    }
}
