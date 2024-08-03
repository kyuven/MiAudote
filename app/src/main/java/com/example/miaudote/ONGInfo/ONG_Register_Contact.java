package com.example.miaudote.ONGInfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.miaudote.Fragments.Main_Page;
import com.example.miaudote.Models.OngModel;
import com.example.miaudote.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ONG_Register_Contact extends AppCompatActivity {

    DatabaseReference databaseReference, rootItem;
    TextInputEditText edtTelOng, edtInstaOng, edtEmailOng, edtTwitterOng, edtFaceOng;
    Bundle extras = getIntent().getExtras();
    String nomeOng, descOng, cidadeOng, bairroOng, logradouroOng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ong_register_contact);

        edtTelOng = findViewById(R.id.edtOngTel);
        edtInstaOng = findViewById(R.id.edtOngInstagram);
        edtEmailOng = findViewById(R.id.edtOngEmail);
        edtTwitterOng = findViewById(R.id.edtOngTwitter);
        edtFaceOng = findViewById(R.id.edtOngFacebook);


        AppCompatButton btnSalvar = findViewById(R.id.btnAddOng);
        btnSalvar.setOnClickListener(v -> saveDataContato());
    }

    public void saveDataContato() {

        // nomeOng = extras.getString("nomeOng");
        // descOng = extras.getString("descOng");
        // cidadeOng = extras.getString("cidadeOng");
        // bairroOng = extras.getString("bairroOng");
        // logradouroOng = extras.getString("logradouroOng");

        HashMap<String, String> map = new HashMap<>();
        // map.put("nomeOng", nomeOng);
        // map.put("descOng", descOng);
        // map.put("cidadeOng", cidadeOng);
        // map.put("bairroOng", bairroOng);
        // map.put("lograOng", logradouroOng);
        map.put("telOng", edtTelOng.getText().toString());
        map.put("instaOng", edtInstaOng.getText().toString());
        map.put("emailOng", edtEmailOng.getText().toString());
        map.put("twitterOng", edtTwitterOng.getText().toString());
        map.put("faceOng", edtFaceOng.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("ONG para adicionar").push()
                .setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
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