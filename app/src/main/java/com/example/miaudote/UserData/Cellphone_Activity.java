package com.example.miaudote.UserData;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.miaudote.Models.UserModel;
import com.example.miaudote.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Cellphone_Activity extends AppCompatActivity {

    // Firebase
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    TextInputEditText edtTefone;
    String strTelefone;

    // Botão que para a próxima página, respectivamente
    ImageButton btnConfirmarCad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cellphone);

        // Firebase conexões
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("usuarios");

        //Telefone
        edtTefone = findViewById(R.id.edtTelefone);

        // Envia para a página de confirmação de cadastro (código OTP)
        btnConfirmarCad = findViewById(R.id.fab_next_tel);
        btnConfirmarCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtTefone.getText().toString().trim().isEmpty()){
                    Toast.makeText(Cellphone_Activity.this, "Por favor, digite seu telefone", Toast.LENGTH_SHORT).show();
                }

                strTelefone = edtTefone.getText().toString();
                String userID = firebaseUser.getUid();
                reference.child(userID).child("telefone").setValue(strTelefone);
                Intent intent = new Intent(Cellphone_Activity.this, Confirmar_Login.class);
                intent.putExtra("telefone", strTelefone);
                startActivity(intent);


            }
        });

    };
}