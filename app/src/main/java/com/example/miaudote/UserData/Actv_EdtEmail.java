package com.example.miaudote.UserData;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;

import com.example.miaudote.Fragments.Perfil_Fragment;
import com.example.miaudote.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Actv_EdtEmail extends AppCompatActivity {

    // FIREBASE
    FirebaseAuth mAtuh;
    FirebaseUser firebaseUser;
    DatabaseReference reference;

    // WIDGETS
    EditText edtSenha, edtNovoEmail;
    String strNovoEmail, strEmailAntigo, strEmail, strSenha;
    AppCompatButton btnAttEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actv_edt_email);

        // FIREBASE
        mAtuh = FirebaseAuth.getInstance();
        firebaseUser = mAtuh.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("usuarios");

        // WIDGETS
        TextView txtEmailAntigo = findViewById(R.id.edt_emailAntigo);
        edtNovoEmail = findViewById(R.id.edt_novoEmail);
        edtSenha = findViewById(R.id.edt_emailSenha);

        // PUXA O E-MAIL DO ANTIGO USUÁRIO
        strEmailAntigo = firebaseUser.getEmail();
        txtEmailAntigo.setText(strEmailAntigo);

        btnAttEmail = findViewById(R.id.btnAttEmail);
        AppCompatImageButton btnBack = findViewById(R.id.btnEdtEmail_back);

        // VOLTA PRA PÁGINA ANTERIOR
        btnBack.setOnClickListener(v -> {
            finish();
        });

        if (firebaseUser.equals("")) {
            Toast.makeText(Actv_EdtEmail.this, "Algo deu errado.", Toast.LENGTH_LONG);
        } else {
            reAuthenticated(firebaseUser);
        }

    }

    private void reAuthenticated(FirebaseUser firebaseUser) {
        btnAttEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strSenha = edtSenha.getText().toString();

                // VERIFICA SE O CAMPO DE SENHA É VAZIA
                if(TextUtils.isEmpty(strSenha)) {
                    Toast.makeText(Actv_EdtEmail.this, "Insira sua senha para continuar.", Toast.LENGTH_LONG);
                    edtSenha.requestFocus();
                } else {
                    // PEGA O EMAIL E SENHA DO USUÁRIO DO AUTHENTICATION
                    AuthCredential authCredential = EmailAuthProvider.getCredential(strEmailAntigo, strSenha);
                    firebaseUser.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                strNovoEmail = edtNovoEmail.getText().toString();

                                if(TextUtils.isEmpty(strNovoEmail)) { // VERIFICA SE O NOVO E-MAIL É VAZIO
                                    Toast.makeText(Actv_EdtEmail.this, "Insira um E-Mail válido.", Toast.LENGTH_SHORT).show();
                                    edtNovoEmail.requestFocus(); // FOCA NO CAMPO DE TEXTO DA SENHA ANTIGA
                                } else if(!Patterns.EMAIL_ADDRESS.matcher(strNovoEmail).matches()) {
                                    Toast.makeText(Actv_EdtEmail.this, "O novo email não pode ser igual ao anterior.", Toast.LENGTH_SHORT).show();
                                } else if(strEmailAntigo.matches(strNovoEmail)) {
                                    Toast.makeText(Actv_EdtEmail.this, "O novo email não pode ser igual ao anterior.", Toast.LENGTH_SHORT).show();
                                } else {
                                    updateEmail(firebaseUser); // CHAMA O MÉTODO PARA MUDAR O E-MAIL
                                    String userID = firebaseUser.getUid(); // PEGA O ID DO USUÁRIO
                                    reference.child(userID).child("email").setValue(strNovoEmail);
                                }
                            } else {
                                try {
                                    throw task.getException(); // TRATAMENTO DE ERRO
                                } catch (Exception e) {
                                    Toast.makeText(Actv_EdtEmail.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    // MÉTODO PARA MUDAR O E-MAIL
    private void updateEmail(FirebaseUser firebaseUser) {
        firebaseUser.verifyBeforeUpdateEmail(strNovoEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isComplete()) {
                    firebaseUser.sendEmailVerification(); // MANDA E-MAIL PARA VERIFICAÇÃO (AUTENTICAÇÃO)
                    Toast.makeText(Actv_EdtEmail.this, "O email de verificação já foi enviado!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    try {
                        throw task.getException(); // TRATAMENTO DE ERRO
                    } catch (Exception e) {
                        Toast.makeText(Actv_EdtEmail.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}




































































































































// vai se foder leonardo - easter egg 1