package com.example.miaudote.UserData;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.miaudote.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Cellphone_Activity extends AppCompatActivity {

    // FIREBASE
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    DatabaseReference reference;

    // WIDGETS
    TextInputEditText edtTefone;
    String strTelefone;
    ImageButton btnConfirmarCad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cellphone);

        // FIREBASE CONEXÕES
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("usuarios");

        // TELEFONE
        edtTefone = findViewById(R.id.edtTelefone);

        // ENVIA PARA A PÁGINA DE VERIFICAÇÃO OTP
        btnConfirmarCad = findViewById(R.id.fab_next_tel);
        btnConfirmarCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SE O CAMPO TELEFONE FOR NULO, MOSTRA UMA MENSAGEM DE TEXTO
                if (edtTefone.getText().toString().trim().isEmpty()){
                    Toast.makeText(Cellphone_Activity.this, "Por favor, digite seu telefone", Toast.LENGTH_SHORT).show();
                }

                strTelefone = edtTefone.getText().toString(); // TRANSFORMA O TELEFONE EM STRING
                String userID = firebaseUser.getUid(); // PEGA O ID DE USUÁRIO
                reference.child(userID).child("telefone").setValue(strTelefone); // ADICIONA NO BANCO DE DADOS DO USUÁRIO

                Intent intent = new Intent(Cellphone_Activity.this, Confirmar_Login.class);
                intent.putExtra("telefone", strTelefone); // LEVA A INFORMAÇÃO PRA PRÓXIMA PÁGINA
                startActivity(intent);
            }
        });

    };
}