package com.example.miaudote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class LoginIn_Activity extends AppCompatActivity {

    FirebaseAuth auth;
    TextInputEditText edtEmailLogin, edtSenhaLogin;
    ImageButton imgBtnGoogle, imgBtnFacebook;
    Button btnCadastrar, btnEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_in);

        edtEmailLogin = findViewById(R.id.LogIn_email);
        edtSenhaLogin = findViewById(R.id.LogIn_senha);

        imgBtnGoogle = findViewById(R.id.btnLogin_google);
        imgBtnFacebook = findViewById(R.id.btnLogin_facebook);

        public Boolean validateEmail(){
            String val = edtEmailLogin.getText().toString();
            if (val.isEmpty()){
                edtEmailLogin.setError("Email não pode estar vazio!");
                return false;
            }else {
                edtEmailLogin.setError(null);
                return true;
            }

            // https://www.youtube.com/watch?v=M3gYcPF51QY MINUTO 17:52 (CONTINUAR TUDO)
        }

        CheckBox ckbMostrarSenha = (CheckBox) findViewById(R.id.ckbLogin_mostrarSenha);

        // Usuário não cadastrado
        btnCadastrar = findViewById(R.id.btn_semCadastro);

        btnCadastrar.setOnClickListener(v -> {
            Intent intent = new Intent(LoginIn_Activity.this, Register_Activity.class);
            startActivity(intent);
            finish();
        });

        // Entrada de usuário já cadastrado
        btnEntrar = findViewById(R.id.btn_entrar);

        btnEntrar.setOnClickListener(v -> {
            Intent intent = new Intent(LoginIn_Activity.this, Main_Page.class);
            startActivity(intent);
            finish();
        });
    }
}