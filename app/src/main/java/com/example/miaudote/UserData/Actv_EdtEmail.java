package com.example.miaudote.UserData;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
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

public class Actv_EdtEmail extends AppCompatActivity {

    FirebaseAuth mAtuh;
    FirebaseUser firebaseUser;
    EditText edtSenha, edtNovoEmail;
    String strNovoEmail, strEmailAntigo, strSenha;
    AppCompatButton btnAttEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actv_edt_email);

        mAtuh = FirebaseAuth.getInstance();
        firebaseUser = mAtuh.getCurrentUser();
        strEmailAntigo = firebaseUser.getEmail();

        TextView txtEmailAntigo = findViewById(R.id.edt_emailAntigo);
        edtNovoEmail = findViewById(R.id.edt_novoEmail);
        edtSenha = findViewById(R.id.edt_emailSenha);

        btnAttEmail = findViewById(R.id.btnAttEmail);
        AppCompatImageButton btnBack = findViewById(R.id.btnEdtEmail_back);

        btnBack.setOnClickListener(v -> {
            Intent i = new Intent(Actv_EdtEmail.this, Perfil_Fragment.class);
            startActivity(i);
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

                if(TextUtils.isEmpty(strSenha)) {
                    Toast.makeText(Actv_EdtEmail.this, "Insira sua senha para continuar.", Toast.LENGTH_LONG);
                    edtSenha.requestFocus();
                } else {
                    AuthCredential authCredential = EmailAuthProvider.getCredential(strEmailAntigo, strSenha);
                    firebaseUser.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                strNovoEmail = edtNovoEmail.getText().toString();
                                if(TextUtils.isEmpty(strNovoEmail)) {
                                    Toast.makeText(Actv_EdtEmail.this, "Insira um E-Mail válido.", Toast.LENGTH_SHORT).show();
                                    edtNovoEmail.requestFocus();
                                } else if(!Patterns.EMAIL_ADDRESS.matcher(strNovoEmail).matches()) {
                                    Toast.makeText(Actv_EdtEmail.this, "Caralho nao pode ser o mesmo email", Toast.LENGTH_SHORT).show();
                                } else if(strEmailAntigo.matches(strNovoEmail)) {
                                    Toast.makeText(Actv_EdtEmail.this, "NÃO PODE SER IGUAL BUCETA", Toast.LENGTH_SHORT).show();
                                } else {
                                    updateEmail(firebaseUser);
                                }
                            } else {
                                try {
                                    throw task.getException();
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

    private void updateEmail(FirebaseUser firebaseUser) {
        firebaseUser.verifyBeforeUpdateEmail(strNovoEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isComplete()) {
                    firebaseUser.sendEmailVerification();
                    Toast.makeText(Actv_EdtEmail.this, "Já no email po", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Actv_EdtEmail.this, LoginIn_Activity.class);
                    startActivity(i);
                    finish();
                } else {
                    try {
                        throw task.getException();
                    } catch (Exception e) {
                        Toast.makeText(Actv_EdtEmail.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}




































































































































// vai se foder leonardo - easter egg 1