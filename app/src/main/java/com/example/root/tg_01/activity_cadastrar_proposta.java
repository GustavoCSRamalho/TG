package com.example.root.tg_01;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.root.tg_01.models.Empresa;
import com.example.root.tg_01.service.database.firebase.empresa.EmpresaService;

public class activity_cadastrar_proposta extends Activity {

    protected Button enviar;

    protected EditText nome, descricao, destino;

    protected EmpresaService empresaService;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_proposta);

        empresaService = EmpresaService.getInstance();
        System.out.println("Aqui 1");
        empresaService.checkListaDeEmpresa();

        Empresa empresa = empresaService.setOne(getApplicationContext());

        System.out.println("Antes");
        empresaService.saveEmpresa(empresa,getApplicationContext());
        System.out.println("Depois");

        nome = findViewById(R.id.nome);
        descricao = findViewById(R.id.descricao);
        destino = findViewById(R.id.destino);
        enviar = findViewById(R.id.enviar_dados_proposta);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



    }
}
