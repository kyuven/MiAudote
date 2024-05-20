package com.example.miaudote;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register_Activity extends AppCompatActivity {

    private FirebaseAuth auth;

    // Input que recebe as informações de cadastro do usuário
    TextInputEditText edtNomeCad, edtEmailCad, edtTelefoneCad,edtSenhaCad, edtConfirmarSenha;

    // Botão que volta para a página anterior e vai para a próxima página, respectivamente
    ImageButton btnBackLogin, btnConfirmarCad;

    //CheckBox para mostrar senha e dos termos
    CheckBox ckbMostrarSenha, ckbTermos;

    // Firebase e Database
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtNomeCad = findViewById(R.id.cadastro_nome);
        edtEmailCad = findViewById(R.id.cadastro_email);
        edtTelefoneCad = findViewById(R.id.cadastro_telefone);
        edtSenhaCad = findViewById(R.id.cadastro_senha);
        edtConfirmarSenha = findViewById(R.id.cadastro_confirmarSenha);

        ckbMostrarSenha = findViewById(R.id.ckbCad_mostrarSenha);

        // CheckBox dos termos de utilização
        // ? Separar em três telas o registro ?


        // Envia para a página de login (volta a página)
        btnBackLogin = findViewById(R.id.fab_back_login);
        btnBackLogin.setOnClickListener(v -> {
            Intent intent = new Intent(Register_Activity.this, LoginIn_Activity.class);
            onBackPressed();
            startActivity(intent);
            finish();
        });

        // Envia para a página de confirmação de cadastro (código OTP)
        btnConfirmarCad = findViewById(R.id.fab_next_confirmar);
        btnConfirmarCad.setOnClickListener(v -> {

            // REGISTRO: NOME, EMAIL, TELEFONE (FALTA), SENHA, CONFIRMAÇÃO DE SENHA
            database = FirebaseDatabase.getInstance();
            reference = database.getReference("users");

            String nome = edtNomeCad.getText().toString();
            String email = edtEmailCad.getText().toString();
            String senha= edtSenhaCad.getText().toString();

            HelperClass helperClass = new HelperClass(nome, email, senha);
            reference.child(nome).setValue(helperClass);

            Toast.makeText(Register_Activity.this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Register_Activity.this, LoginIn_Activity.class);
            startActivity(intent);

        });
    }

    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}