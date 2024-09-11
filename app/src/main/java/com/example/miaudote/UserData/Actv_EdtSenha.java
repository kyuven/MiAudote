package com.example.miaudote.UserData;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;

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

    // FIREBASE
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    DatabaseReference reference;

    // WIDGETS
    TextInputEditText edtSenhaAntiga, edtNovaSenha;
    String strSenhaAntiga, strSenhaNova;
    AppCompatButton btnAttSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actv_edt_senha);

        // FIREBASE
        reference = FirebaseDatabase.getInstance().getReference("usuarios");
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        // WIDGETS REFERÊNCIAS
        btnAttSenha = findViewById(R.id.btnAttSenha);
        edtSenhaAntiga = findViewById(R.id.edt_senhaAntiga);
        edtNovaSenha = findViewById(R.id.edt_novaSenha);

        if (firebaseUser.equals("")){
            Toast.makeText(Actv_EdtSenha.this, "Alguma coisa deu errado!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            reAuthenticate(firebaseUser);
        }

        // VOLTAR PARA A PÁGINA ANTERIOR
        AppCompatImageButton btnBack = findViewById(R.id.btnEdtSenha_back);
        btnBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void reAuthenticate(FirebaseUser firebaseUser) {
        btnAttSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strSenhaAntiga = edtSenhaAntiga.getText().toString(); // PEGA O VALOR INSERIDO E TRANSFORMA EM STRING
                if(TextUtils.isEmpty(strSenhaAntiga)){ // VERIFICA SE A SENHA ANTIGA É VAZIO
                    Toast.makeText(Actv_EdtSenha.this, "É necessário digitar sua senha", Toast.LENGTH_SHORT).show(); // MENSAGEM CASO SEJA VAZIO
                    edtSenhaAntiga.setError("Por favor digite sua senha atual");
                    edtSenhaAntiga.requestFocus(); // FOCA NO CAMPO DE TEXTO DA SENHA ANTIGA
                } else {
                    // ACESSA O AUTHENTICATION DO FIREBASE E PEGA O EMAIL E SENHA ANTIGA
                    AuthCredential credential = EmailAuthProvider.getCredential(firebaseUser.getEmail(), strSenhaAntiga);
                    firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                mudarSenha(firebaseUser); // CHAMA O MÉTODO MUDAR SENHA
                            } else {
                                try {
                                    throw task.getException(); // TRATAMENTO DE ERRO
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

    // BLOCO DE CÓDIGO RESPONSÁVEL POR MUDAR A SENHA
    private void mudarSenha(FirebaseUser firebaseUser) {
        strSenhaNova = edtNovaSenha.getText().toString(); // PEGA O QUE FOI INSERIDO NO CAMPO E TRANSFORMA EM STRING
        strSenhaAntiga = edtSenhaAntiga.getText().toString(); // PEGA O QUE FOI INSERIDO NO CAMPO E TRANSFORMA EM STRING

        if (TextUtils.isEmpty(strSenhaNova)){ // VERIFICA SE O CAMPO SENHA NOVA É VAZIO
            Toast.makeText(Actv_EdtSenha.this, "É necessário digitar uma nova senha", Toast.LENGTH_SHORT).show(); // MENSAGEM CASO SEJA VAZIO
            edtNovaSenha.setError("Por favor, digite sua nova senha!");
            edtNovaSenha.requestFocus(); // FOCA NO CAMPO DE TEXTO DA SENHA ANTIGA
        } else if (strSenhaAntiga.matches(strSenhaNova)) { // VERIFICA SE AS SENHAS SÃO IGUAIS
            Toast.makeText(Actv_EdtSenha.this, "A nova senha não pode ser igual a anterior.", Toast.LENGTH_SHORT).show(); // MENSAGEM CASO SENHAS FOREM IGUAIS
            edtNovaSenha.setError("Por favor, digite uma senha diferente que a anterior!");
            edtNovaSenha.requestFocus(); // FOCA NO CAMPO DE TEXTO DA SENHA ANTIGA
        } else { // CHAMA O MÉTODO PARA MUDAR A SENHA
            firebaseUser.updatePassword(strSenhaNova).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        String userID = firebaseUser.getUid(); // PEGA ID DO USUÁRIO
                        reference.child(userID).child("senha").setValue(strSenhaNova); // MUDA NO FIREBASE
                        Toast.makeText(Actv_EdtSenha.this, "A senha foi atualizada com sucesso!", Toast.LENGTH_SHORT).show();
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