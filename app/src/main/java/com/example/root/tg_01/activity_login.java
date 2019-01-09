package com.example.root.tg_01;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.root.tg_01.models.Usuario;
import com.example.root.tg_01.service.database.firebase.FireBaseService;
import com.example.root.tg_01.service.database.firebase.interfaces.FireBaseInterf;
import com.example.root.tg_01.service.database.firebase.usuario.UsuarioService;
import com.example.root.tg_01.utils.Usuario_logado;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class activity_login extends AppCompatActivity {

    protected AutoCompleteTextView email;
    protected EditText password;

    protected Button login;

    protected FirebaseAuth firebaseAuth;

    private FireBaseInterf fireBase = FireBaseService.getInstance();

    private Usuario_logado usuario_logado;

    protected UsuarioService usuarioService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = fireBase.getFirebaseAuth();

        usuario_logado = Usuario_logado.getInstance();

        usuarioService = UsuarioService.getInstance();

        login = findViewById(R.id.btn_Login);
        email = (AutoCompleteTextView) findViewById(R.id.edt_Email_Login);
        password = (EditText) findViewById(R.id.edt_Senha_Login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!email.getText().toString().equals("") && !password.getText().toString().equals("")){
//                    firebaseAuth = fireBase.getFirebaseAuth();

                    firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                Usuario usuario = usuarioService.getUsuarioEspecifico(firebaseUser.getUid());
                                usuario_logado.setUsuario(usuario);
                                System.out.println("UID : "+firebaseUser.getUid().toString());
                                Toast.makeText(activity_login.this,"Logado",Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(activity_login.this,"Deu erro",Toast.LENGTH_LONG).show();
                            }


                        }
                    });
                }
            }
        });



    }
}
