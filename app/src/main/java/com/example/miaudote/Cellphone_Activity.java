package com.example.miaudote;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class Cellphone_Activity extends AppCompatActivity {

    // Firebase
    private FirebaseAuth auth;

    // Botão que para a próxima página, respectivamente
    ImageButton btnConfirmarCad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cellphone);



        // Envia para a página de confirmação de cadastro (código OTP)
        btnConfirmarCad = findViewById(R.id.fab_next_tel);
        btnConfirmarCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Cellphone_Activity.this, Confirmar_Login.class));

            }
        });

    };
}