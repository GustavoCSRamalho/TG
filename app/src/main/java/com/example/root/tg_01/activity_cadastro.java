package com.example.root.tg_01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.root.tg_01.models.Usuario;
import com.example.root.tg_01.service.database.firebase.FireBaseService;
import com.example.root.tg_01.service.database.firebase.empresa.EmpresaService;
import com.example.root.tg_01.service.database.firebase.interfaces.FireBaseInterf;
import com.example.root.tg_01.utils.Base64Custom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class activity_cadastro extends Activity {


    protected EmpresaService empresaService;


    protected FirebaseAuth firebaseAuth;
    private Usuario usuario;
    private Button FabCadastrar;
    private static FireBaseInterf fireBase = FireBaseService.getInstance();

    protected EditText edt_Nome_Cadastro, edt_Email_Cadastro, edt_Senha_Cadastro;
    protected Button gravar;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        edt_Nome_Cadastro = findViewById(R.id.edt_Nome_Cadastro);
        edt_Email_Cadastro = findViewById(R.id.edt_Email_Cadastro);
        edt_Senha_Cadastro = findViewById(R.id.edt_Senha_Cadastro);
        gravar = findViewById(R.id.fab_enviarDados_Cadastro);
        fireBase = FireBaseService.getInstance();

        empresaService = EmpresaService.getInstance();

//        empresaService.setOne(getApplicationContext());

        empresaService.setEmpresaListener();

        firebaseAuth = fireBase.getFirebaseAuth();


        gravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    final Usuario usuario = new Usuario();
                    usuario.setName(edt_Nome_Cadastro.getText().toString());
                    usuario.setEmail(edt_Email_Cadastro.getText().toString());
                    usuario.setPassword(edt_Senha_Cadastro.getText().toString());
                    usuario.setType(0);

                    firebaseAuth.createUserWithEmailAndPassword(usuario.getEmail(),usuario.getPassword()).
                            addOnCompleteListener(activity_cadastro.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(activity_cadastro.this,"Usuario cadastrado com sucesso!",Toast.LENGTH_SHORT);
                                        String identificadorUsuario = Base64Custom.codificadorBase64(usuario.getEmail());

                                        FirebaseUser usuarioFireBase = task.getResult().getUser();

                                        usuario.setUid(usuarioFireBase.getUid());
                                        System.out.println("UID : "+usuarioFireBase.getUid());
//                                        fireBase.buildConfiguration();
                                        fireBase.saveUsuarioData(usuario);
//                                        usuario.salvar();
                                        Intent intent = new Intent(activity_cadastro.this,activity_login.class);
                                        startActivity(intent);

                                    }else{
                                        Toast.makeText(activity_cadastro.this,"Erro ao cadastrar!",Toast.LENGTH_SHORT);
                                    }
                                }
                            });
            }
        });

    }
}