package com.example.miaudote.UserData;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.miaudote.Models.UserModel;
import com.example.miaudote.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register_Activity extends AppCompatActivity {

    // Firebase
    FirebaseAuth auth;

    TextInputEditText edtNomeCad, edtEmailCad, edtSenhaCad, edtConfirmarSenha;
    ImageButton btnBackLogin, btnConfirmarCad;
    CheckBox ckbMostrarSenha, ckbTermos;
    AppCompatButton btnTermos;
    String strNome, strSenha, strEmail, strConfirmarSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        edtNomeCad = findViewById(R.id.cadastro_nome);
        edtEmailCad = findViewById(R.id.cadastro_email);
        edtSenhaCad = findViewById(R.id.cadastro_senha);
        edtConfirmarSenha = findViewById(R.id.cadastro_confirmarSenha);

        ckbMostrarSenha = findViewById(R.id.ckbCad_mostrarSenha);
        ckbTermos = findViewById(R.id.ckbTermos);
        btnTermos = findViewById(R.id.btnTermos);

        // Envia para a página de login (volta a página)
        btnBackLogin = findViewById(R.id.fab_back);
        btnBackLogin.setOnClickListener(v -> {
            finish();
        });

        btnTermos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Register_Activity.this, Termos_Activity.class);
                startActivity(i);
            }
        });

        // Envia para a página de confirmação de cadastro (código OTP)
        btnConfirmarCad = findViewById(R.id.fab_next_confirmar);
        btnConfirmarCad.setOnClickListener(v -> {
            if (ckbTermos.isChecked()) {
                strNome = edtNomeCad.getText().toString();
                strEmail = edtEmailCad.getText().toString();
                strSenha = edtSenhaCad.getText().toString();
                strConfirmarSenha = edtConfirmarSenha.getText().toString();
                if(!strSenha.matches(strConfirmarSenha)){
                    Toast.makeText(this, "As senhas não coincidem.", Toast.LENGTH_SHORT).show();
                } else if(!isValidPassword(strSenha)) {
                    Toast.makeText(this, "A senha deve conter ao menos 9 carateres, tendo um símbolo e uma letra maiúscula.", Toast.LENGTH_SHORT).show();
                } else {
                    // REGISTRO: NOME, EMAIL, SENHA, CONFIRMAÇÃO DE SENHA
                    UserModel userModel = new UserModel();
                    userModel.setNome(strNome);
                    userModel.setEmail(strEmail);
                    userModel.setSenha(strSenha);

                    if(!TextUtils.isEmpty(userModel.getNome()) || !TextUtils.isEmpty(userModel.getEmail()) || !TextUtils.isEmpty(userModel.getSenha())){

                        auth.createUserWithEmailAndPassword(userModel.getEmail(), userModel.getSenha())
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()){
                                            userModel.setId(auth.getUid()); // pega o ID de usuário e seta no ID
                                            userModel.salvar(); // SALVA OS DADOS
                                            Intent i = new Intent(Register_Activity.this, Cellphone_Activity.class);
                                            startActivity(i);
                                        }
                                    }
                                });
                    }
                }
            } else {
                Toast.makeText(this, "Aceite os termos para prosseguir.", Toast.LENGTH_SHORT).show();
            }
        });

        // Mostrar Senha - Checkbox
        ckbMostrarSenha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    edtSenhaCad.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edtConfirmarSenha.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    edtSenhaCad.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edtConfirmarSenha.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

    }

    private boolean isValidPassword(String password) {
        if (password.length() < 9) {
            return false;
        }

        boolean hasUppercase = false;
        boolean hasSymbol = false;
        String symbols = "!@#$%^&*()-_=+[{]}|;:'\",.<>?/";

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            }
            if (symbols.indexOf(c) >= 0) {
                hasSymbol = true;
            }
        }

        return hasUppercase && hasSymbol;
    }
}