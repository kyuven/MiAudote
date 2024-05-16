package com.example.miaudote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MissingPet_Info extends AppCompatActivity {

    TextView txtNomeAnimal, txtEspecieAnimal, txtIdadeAnimal, txtDescAnimal, txtEmail, txtTelefone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missing_pet_info);

        AppCompatImageButton btnBack = (AppCompatImageButton) findViewById(R.id.btnMissingPet_back);
        ImageView imgAnimalPerdido = (ImageView) findViewById(R.id.imgMissingPet);

        txtNomeAnimal = findViewById(R.id.txtMissingPet_nomeAnimal);
        txtEspecieAnimal = findViewById(R.id.txtMissingPet_especieAnimal);
        txtIdadeAnimal = findViewById(R.id.txtMissingPet_idadeAnimal);
        txtDescAnimal = findViewById(R.id.txtMissingPet_descAnimal);
        txtEmail = findViewById(R.id.txtMissingPet_email);
        txtTelefone = findViewById(R.id.txtMissingPet_telefone);

        btnBack.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}