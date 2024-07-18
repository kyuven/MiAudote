package com.example.miaudote.UserData;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

public class Actv_EdtEmail extends AppCompatActivity {

    FirebaseAuth mAtuh;
    TextInputEditText edtEmailAntigo, edtSenha, edtNovoEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actv_edt_email);

        mAtuh = FirebaseAuth.getInstance();

        edtEmailAntigo = findViewById(R.id.edt_emailAntigo);
        edtSenha = findViewById(R.id.edt_emailSenha);
        edtNovoEmail = findViewById(R.id.edt_novoEmail);

        AppCompatButton btnAttEmail = findViewById(R.id.btnAttEmail);
        AppCompatImageButton btnBack = findViewById(R.id.btnEdtEmail_back);

        btnBack.setOnClickListener(v -> {
            Intent i = new Intent(Actv_EdtEmail.this, Perfil_Fragment.class);
            startActivity(i);
            finish();
        });

    }
}