package com.example.miaudote.UserData;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.miaudote.Fragments.Perfil_Fragment;
import com.example.miaudote.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class Actv_EdtDados extends AppCompatActivity {

    FirebaseAuth mAuth;
    AppCompatButton btnUploadFoto, btnAtualizarDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actv_edt_dados);

        mAuth = FirebaseAuth.getInstance();

        TextInputEditText edtNovoNome = findViewById(R.id.edt_novoNome);
        ImageView fotoPerfil = findViewById(R.id.edt_fotoPerfil);

        btnUploadFoto = findViewById(R.id.btnAttFotoPerfil);
        btnAtualizarDados = findViewById(R.id.btnAttDadosUser);

        AppCompatImageButton btnBack = findViewById(R.id.btnEdtUser_back);
        btnBack.setOnClickListener(v -> {
            Intent i = new Intent(Actv_EdtDados.this, Perfil_Fragment.class);
            startActivity(i);
            finish();
        });

    }
}