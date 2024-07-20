package com.example.miaudote.UserData;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.miaudote.Fragments.Perfil_Fragment;
import com.example.miaudote.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Actv_EdtSenha extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    TextInputEditText edtSenhaAntiga, edtNovaSenha;
    String strSenhaAntiga, strSenhaNova;
    AppCompatButton btnAttSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actv_edt_senha);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        edtSenhaAntiga = findViewById(R.id.edt_senhaAntiga);
        edtNovaSenha = findViewById(R.id.edt_novaSenha);

        AppCompatImageButton btnBack = findViewById(R.id.btnEdtSenha_back);
        btnBack.setOnClickListener(v -> {
            Intent i = new Intent(Actv_EdtSenha.this, Perfil_Fragment.class);
            startActivity(i);
            finish();
        });

        if (firebaseUser.equals("")) {
            Toast.makeText(Actv_EdtSenha.this, "Algo deu errado.", Toast.LENGTH_LONG);
        } else {
            reAuthenticated(firebaseUser);
        }

    }

    private void reAuthenticated(FirebaseUser firebaseUser) {
        btnAttSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strSenhaAntiga = edtSenhaAntiga.getText().toString();

                if(TextUtils.isEmpty(strSenhaAntiga)) {
                    Toast.makeText(Actv_EdtSenha.this, "Insira sua senha para continuar.", Toast.LENGTH_LONG);
                    edtSenhaAntiga.requestFocus();
                } else {
                    AuthCredential authCredential = EmailAuthProvider.getCredential(firebaseUser.getEmail(), strSenhaAntiga);
                    firebaseUser.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                strSenhaNova = edtNovaSenha.getText().toString();
                                if(TextUtils.isEmpty(strSenhaNova)) {
                                    Toast.makeText(Actv_EdtSenha.this, "Insira uma senha válida.", Toast.LENGTH_SHORT).show();
                                    edtNovaSenha.requestFocus();
                                } else if (strSenhaAntiga.matches(strSenhaNova)){
                                    Toast.makeText(Actv_EdtSenha.this, "A nova senha não pode ser igual ao anterior.", Toast.LENGTH_SHORT).show();
                                } else {
                                    mudarSenha(firebaseUser);
                                }
                            } else {
                                try {
                                    throw task.getException();
                                } catch (Exception e) {
                                    Toast.makeText(Actv_EdtSenha.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    private void mudarSenha(FirebaseUser firebaseUser) {
        String  strSenhaNova = edtNovaSenha.getText().toString();
        firebaseUser.updatePassword(strSenhaNova).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Actv_EdtSenha.this, "A senha foi alterada com sucesso!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Actv_EdtSenha.this, LoginIn_Activity.class);
                    startActivity(i);
                    finish();
                } else {
                    try {
                        throw task.getException();
                    } catch (Exception e) {
                        Toast.makeText(Actv_EdtSenha.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}