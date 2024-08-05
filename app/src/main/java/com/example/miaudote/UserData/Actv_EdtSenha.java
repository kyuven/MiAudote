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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Actv_EdtSenha extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    TextInputEditText edtSenhaAntiga, edtNovaSenha;
    String strSenhaAntiga, strSenhaNova;
    AppCompatButton btnAttSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actv_edt_senha);

        reference = FirebaseDatabase.getInstance().getReference("usuarios");
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        btnAttSenha = findViewById(R.id.btnAttSenha);
        edtSenhaAntiga = findViewById(R.id.edt_senhaAntiga);
        edtNovaSenha = findViewById(R.id.edt_novaSenha);

        if (firebaseUser.equals("")){
            Toast.makeText(Actv_EdtSenha.this, "Alguma coisa deu errado!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Actv_EdtSenha.this, Perfil_Fragment.class);
            startActivity(intent);
            finish();
        } else {
            reAuthenticate(firebaseUser);
        }

        AppCompatImageButton btnBack = findViewById(R.id.btnEdtSenha_back);
        btnBack.setOnClickListener(v -> {
            Intent i = new Intent(Actv_EdtSenha.this, Perfil_Fragment.class);
            startActivity(i);
            finish();
        });
    }

    private void reAuthenticate(FirebaseUser firebaseUser) {
        btnAttSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strSenhaAntiga = edtSenhaAntiga.getText().toString();
                if(TextUtils.isEmpty(strSenhaAntiga)){
                    Toast.makeText(Actv_EdtSenha.this, "É necessário digitar sua senha", Toast.LENGTH_SHORT).show();
                    edtSenhaAntiga.setError("Por favor digite sua senha atual");
                    edtSenhaAntiga.requestFocus();
                } else {
                    AuthCredential credential = EmailAuthProvider.getCredential(firebaseUser.getEmail(), strSenhaAntiga);
                    firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                mudarSenha(firebaseUser);
                            } else {
                                try {
                                    throw task.getException();
                                } catch (Exception e){
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
        strSenhaNova = edtNovaSenha.getText().toString();
        strSenhaAntiga = edtSenhaAntiga.getText().toString();

        if (TextUtils.isEmpty(strSenhaNova)){
            Toast.makeText(Actv_EdtSenha.this, "É necessário digitar uma nova senha", Toast.LENGTH_SHORT).show();
            edtNovaSenha.setError("Por favor, digite sua nova senha!");
            edtNovaSenha.requestFocus();
        } else if (strSenhaAntiga.matches(strSenhaNova)) {
            Toast.makeText(Actv_EdtSenha.this, "A nova senha não pode ser igual a anterior.", Toast.LENGTH_SHORT).show();
            edtNovaSenha.setError("Por favor, digite uma senha diferente que a anterior!");
            edtNovaSenha.requestFocus();
        } else {
            firebaseUser.updatePassword(strSenhaNova).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        String userID = firebaseUser.getUid();
                        reference.child(userID).child("senha").setValue(strSenhaNova);
                        Toast.makeText(Actv_EdtSenha.this, "A senha foi atualizada com sucesso!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Actv_EdtSenha.this, Perfil_Fragment.class);
                        startActivity(intent);
                        finish();
                    } else {
                        try {
                            throw task.getException();
                        } catch (Exception e){
                            Toast.makeText(Actv_EdtSenha.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }
}