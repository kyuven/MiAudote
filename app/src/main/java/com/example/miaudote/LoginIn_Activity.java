package com.example.miaudote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class LoginIn_Activity extends AppCompatActivity {

    Button btnCadastrar, btnEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_in);

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