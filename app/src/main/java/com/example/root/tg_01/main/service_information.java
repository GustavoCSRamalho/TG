package com.example.root.tg_01.main;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.root.tg_01.R;

public class service_information extends Activity {

    private TextView empresa, destino, descricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_information);

        empresa = (TextView) findViewById(R.id.empresa);
        destino = (TextView) findViewById(R.id.destino);
        descricao = (TextView) findViewById(R.id.descricao);

        Bundle extra = getIntent().getExtras();

        destino.setText(extra.getString("destino"));
        descricao.setText(extra.getString("descricao"));

        empresa.setText(extra.getString("Empresa"));
    }
}
