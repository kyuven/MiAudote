package com.example.miaudote.ONGInfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;

import com.example.miaudote.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ONG_Register_General extends AppCompatActivity {

    // FIREBASE

    // WIDGETS
    TextInputEditText edtNomeOng, edtDescOng, edtCidadeOng, edtBairroOng, edtLograOng;
    AppCompatImageButton btnCttOng, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ong_register_general);

        edtNomeOng = findViewById(R.id.edtNomeONG);
        edtDescOng = findViewById(R.id.edtDescONG);
        edtCidadeOng = findViewById(R.id.edtOngCidade);
        edtBairroOng = findViewById(R.id.edtOngBairro);
        edtLograOng = findViewById(R.id.edtOngLogradouro);

        btnCttOng = findViewById(R.id.fab_nextCtt_confirmar);
        btnBack = findViewById(R.id.btnOngGeneralBack);

        btnCttOng.setOnClickListener(v -> {
            saveDataOng();
        });

        btnBack.setOnClickListener(v -> {
            finish();
        });

    }

    private void saveDataOng() {
        Map<String, String> map = new HashMap<>();
        map.put("nomeOng", edtNomeOng.getText().toString());
        map.put("descOng", edtDescOng.getText().toString());
        map.put("cidadeOng", edtCidadeOng.getText().toString());
        map.put("bairroOng", edtBairroOng.getText().toString());
        map.put("lograOng", edtLograOng.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("ONG para adicionar").push()
                .setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent i = new Intent(ONG_Register_General.this, ONG_Register_Contact.class);
                        startActivity(i);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ONG_Register_General.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}