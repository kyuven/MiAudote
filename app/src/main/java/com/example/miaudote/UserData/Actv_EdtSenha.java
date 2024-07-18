package com.example.miaudote.UserData;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.miaudote.Fragments.Perfil_Fragment;
import com.example.miaudote.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class Actv_EdtSenha extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextInputEditText edtSenhaAntiga, edtNovaSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actv_edt_senha);

        edtSenhaAntiga = findViewById(R.id.edt_senhaAntiga);
        edtNovaSenha = findViewById(R.id.edt_novaSenha);

        AppCompatImageButton btnBack = findViewById(R.id.btnEdtSenha_back);
        btnBack.setOnClickListener(v -> {
            Intent i = new Intent(Actv_EdtSenha.this, Perfil_Fragment.class);
            startActivity(i);
            finish();
        });
    }
}