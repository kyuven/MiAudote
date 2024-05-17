package com.example.miaudote;

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

public class Register_Activity extends AppCompatActivity {

    private FirebaseAuth auth;

    // Input que recebe as informações de cadastro do usuário
    TextInputEditText edtNomeCad, edtEmailCad, edtTelefoneCad,edtSenhaCad, edtConfirmarSenha;

    // Botão que volta para a página anterior e vai para a próxima página, respectivamente
    ImageButton btnBackLogin, btnConfirmarCad;

    //CheckBox para mostrar senha e dos termos
    CheckBox ckbMostrarSenha, ckbTermos;

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

            // FALTA A PARTE DE NOME, TELEFONE, E CONFIRMAÇÃO DE SENHA!!!

            String usuario = edtEmailCad.getText().toString().trim();
            String senha = edtSenhaCad.getText().toString().trim();

            if (usuario.isEmpty()){
                edtEmailCad.setError("O campo e-mail não pode estar vazio");
            }
            if (senha.isEmpty()){
                edtSenhaCad.setError("O campo senha não pode estar vazio");
            } else{
                auth.createUserWithEmailAndPassword((usuario, senha) .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Register_Activity.this, "Cadastro concluído", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}