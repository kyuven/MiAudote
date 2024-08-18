package com.example.miaudote.ONGInfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import com.example.miaudote.Fragments.Main_Page;
import com.example.miaudote.Models.OngModel;
import com.example.miaudote.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;

public class ONG_Register_Contact extends AppCompatActivity {

    TextInputEditText edtTelOng, edtInstaOng, edtEmailOng, edtTwitterOng, edtWebsiteOng;
    String imgOng, nomeOng, descOng, ufOng, cidadeOng, bairroOng, logradouroOng,
            teleOng, instaOng, emailong, twitterOng, websiteOng;
    ProgressBar progressBarOng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ong_register_contact);

        edtTelOng = findViewById(R.id.edtOngTel);
        edtInstaOng = findViewById(R.id.edtOngInstagram);
        edtEmailOng = findViewById(R.id.edtOngEmail);
        edtTwitterOng = findViewById(R.id.edtOngTwitter);
        edtWebsiteOng = findViewById(R.id.edtOngWebsite);

        progressBarOng = findViewById(R.id.progressBarOng);
        progressBarOng.setVisibility(View.INVISIBLE);

        // Pega informações da página anterior
        Bundle extras = getIntent().getExtras();
        imgOng = extras.getString("imgOngE");
        nomeOng = extras.getString("nomeOngE");
        descOng = extras.getString("descOngE");
        ufOng = extras.getString("ufOngE");
        cidadeOng = extras.getString("cidadeOngE");
        bairroOng = extras.getString("bairroOngE");
        logradouroOng = extras.getString("logradouroOngE");

        AppCompatButton btnSalvar = findViewById(R.id.btnAddOng);
        btnSalvar.setOnClickListener(v -> saveDataContato());
    }

    public void saveDataContato() {

        progressBarOng.setVisibility(View.VISIBLE);

        teleOng = edtTelOng.getText().toString();
        instaOng = edtInstaOng.getText().toString();
        emailong = edtEmailOng.getText().toString();
        twitterOng = edtTwitterOng.getText().toString();
        websiteOng = edtWebsiteOng.getText().toString();

        OngModel modelOng = new OngModel(imgOng, nomeOng, descOng, ufOng, bairroOng, cidadeOng, logradouroOng,
                                        teleOng, instaOng, emailong, twitterOng, websiteOng);

        FirebaseDatabase.getInstance().getReference().child("ong aprovadas").child(nomeOng)
                .setValue(modelOng).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // TROCAR PARA UM AVISO DE QUE FOI ADICIONADO E DEMORARÁ 72 HORAS PRA SER APROVADO
                        Toast.makeText(ONG_Register_Contact.this, "Adicionado com sucesso", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ONG_Register_Contact.this, Main_Page.class);
                        startActivity(i);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ONG_Register_Contact.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}