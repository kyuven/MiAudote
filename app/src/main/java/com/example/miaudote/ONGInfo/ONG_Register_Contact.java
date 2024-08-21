package com.example.miaudote.ONGInfo;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;

import com.example.miaudote.Fragments.Main_Page;
import com.example.miaudote.Models.OngModel;
import com.example.miaudote.R;
import com.example.miaudote.UserData.LoginIn_Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class ONG_Register_Contact extends AppCompatActivity {

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
                        progressBarOng.setVisibility(View.INVISIBLE);
                        View alertCustomDialog = LayoutInflater.from(ONG_Register_Contact.this).inflate(R.layout.ong_warning, null);
                        AlertDialog.Builder confirmarOng = new AlertDialog.Builder(ONG_Register_Contact.this);

                        confirmarOng.setView(alertCustomDialog);
                        closeDialog = findViewById(R.id.btnCloseDialog);

                        final AlertDialog dialogDel = confirmarOng.create();
                        dialogDel.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialogDel.show();

                        closeDialog.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogDel.cancel();
                                Intent i = new Intent(ONG_Register_Contact.this, Main_Page.class);
                                startActivity(i);
                                finish();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ONG_Register_Contact.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}