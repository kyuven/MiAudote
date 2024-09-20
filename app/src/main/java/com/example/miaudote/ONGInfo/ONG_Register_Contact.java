package com.example.miaudote.ONGInfo;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;

import com.example.miaudote.Fragments.Main_Page;
import com.example.miaudote.Models.OngModel;
import com.example.miaudote.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;

public class ONG_Register_Contact extends AppCompatActivity {

    // WIDGETS
    TextInputEditText edtTelOng, edtInstaOng, edtEmailOng, edtTwitterOng, edtWebsiteOng;
    String imgOng, nomeOng, descOng, ufOng, cidadeOng, bairroOng, logradouroOng,
            teleOng, instaOng, emailong, twitterOng, websiteOng;
    ProgressBar progressBarOng;
    AppCompatImageButton closeDialog;

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
        hideLoading();

        // PEGA AS INFORMAÇÕES DA PÁGINA ANTERIOR
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

    // SALVA OS DADOS DA ONG NO FIREBASE
    public void saveDataContato() {

        showLoading();

        teleOng = edtTelOng.getText().toString();
        instaOng = edtInstaOng.getText().toString();
        emailong = edtEmailOng.getText().toString();
        twitterOng = edtTwitterOng.getText().toString();
        websiteOng = edtWebsiteOng.getText().toString();

        if (TextUtils.isEmpty(teleOng) || TextUtils.isEmpty(emailong)) {
            progressBarOng.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Telefone e e-mail são obrigatórios.", Toast.LENGTH_SHORT).show();
            return; // Não prossegue com a gravação
        }

        OngModel modelOng = new OngModel(imgOng, nomeOng, descOng, ufOng, cidadeOng, bairroOng, logradouroOng,
                                        teleOng, instaOng, emailong, twitterOng, websiteOng);

        // ADICIONA NO BANCO DE DADOS AS INFORMAÇÕES DA ONG
        FirebaseDatabase.getInstance().getReference().child("ong em análise").child(nomeOng)
                .setValue(modelOng).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            hideLoading();
                            View alertCustomDialog = LayoutInflater.from(ONG_Register_Contact.this).inflate(R.layout.ong_warning, null);
                            AlertDialog.Builder confirmarOng = new AlertDialog.Builder(ONG_Register_Contact.this);

                            confirmarOng.setView(alertCustomDialog);
                            closeDialog = alertCustomDialog.findViewById(R.id.btnCloseDialog);

                            final AlertDialog dialog = confirmarOng.create();
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.show();

                            closeDialog.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent(ONG_Register_Contact.this, Main_Page.class);
                                    startActivity(i);
                                    dialog.dismiss();
                                }
                            });
                        } else {
                            Toast.makeText(ONG_Register_Contact.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ONG_Register_Contact.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showLoading() {
        progressBarOng.setVisibility(View.VISIBLE);
        // DESABILITA A INTERAÇÃO DO USÁRIO COM A ATIVIDADE
        findViewById(R.id.root_layoutOng).setEnabled(false);
    }

    private void hideLoading() {
        progressBarOng.setVisibility(View.GONE);
        // ATIVA A INTERAÇÃO DO USÁRIO COM A ATIVIDADE
        findViewById(R.id.root_layoutOng).setEnabled(true);
    }

}