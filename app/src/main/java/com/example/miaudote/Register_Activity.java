package com.example.miaudote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageButton;

import com.google.android.material.textfield.TextInputEditText;

public class Register_Activity extends AppCompatActivity {

    // Input que recebe as informações de cadastro do usuário
    TextInputEditText edtNomeCad, edtEmailCad, edtTelefoneCad,edtSenhaCad, edtConfirmarSenha;

    // Botão que volta para a página anterior e vai para a próxima página, respectivamente
    ImageButton btnBackLogin, btnConfirmarCad;

    //CheckBox para mostrar senha e dos termos
    CheckBox ckbMostrarSenha, ckbTermos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtNomeCad = findViewById(R.id.cadastro_nome);
        edtEmailCad = findViewById(R.id.cadastro_email);
        edtTelefoneCad = findViewById(R.id.cadastro_telefone);
        edtSenhaCad = findViewById(R.id.senha_cad);
        edtConfirmarSenha = findViewById(R.id.confirmarSenha_cad);

        ckbMostrarSenha = findViewById(R.id.ckb_showSenha_Cad);
        // CheckBox dos termos de utilização
        // ? Separar em três telas o registro ?


        // Envia para a página de login (volta a página)
        btnBackLogin = findViewById(R.id.fab_back_login);
        btnBackLogin.setOnClickListener(v -> {
            Intent intent = new Intent(Register_Activity.this, LoginIn_Activity.class);
            startActivity(intent);
            finish();
        });

        // Envia para a página de confirmação de cadastro (código OTP)
        btnConfirmarCad = findViewById(R.id.fab_next_confirmar);
        btnConfirmarCad.setOnClickListener(v -> {
            Intent intent = new Intent(Register_Activity.this, Confirmar_Login.class);
            startActivity(intent);
            finish();
        });
    }
}