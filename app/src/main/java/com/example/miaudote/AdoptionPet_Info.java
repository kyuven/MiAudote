package com.example.miaudote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AdoptionPet_Info extends AppCompatActivity {

    TextView txtNomeAnimal, txtEspecieAnimal, txtIdadeAnimal, txtDescAnimal, txtEmailDono, txtTelefoneDono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adoption_pet_info);

        AppCompatImageButton btnBackAnimalInfo = (AppCompatImageButton) findViewById(R.id.btnAdpt_infoAnimal);
        ImageView imgAnimalInfo = (ImageView) findViewById(R.id.imgAdptInfo_fotoAnimal);

        txtNomeAnimal = findViewById(R.id.txtAdptInfo_nomeAnimal);
        txtEspecieAnimal = findViewById(R.id.txtAdptInfo_especie);
        txtIdadeAnimal = findViewById(R.id.txtAdptInfo_idade);
        txtDescAnimal = findViewById(R.id.txtAdptInfo_desc);
        txtEmailDono = findViewById(R.id.txtAdptInfo_email);
        txtTelefoneDono = findViewById(R.id.txtAdptInfo_telefone);

        btnBackAnimalInfo.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}