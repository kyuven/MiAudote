package com.example.miaudote.UserData;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.miaudote.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register_Activity extends AppCompatActivity {

    // Firebase
    FirebaseAuth auth;

    // Input que recebe as informações de cadastro do usuário
    TextInputEditText edtNomeCad, edtEmailCad, edtSenhaCad, edtConfirmarSenha;

    // Botão que volta para a página anterior e vai para a próxima página, respectivamente
    ImageButton btnBackLogin, btnConfirmarCad;

    //CheckBox para mostrar senha e dos termos
    CheckBox ckbMostrarSenha, ckbTermos;

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

        // CheckBox dos termos de utilização
        // ? Separar em três telas o registro ?

        // Envia para a página de login (volta a página)
        btnBackLogin = findViewById(R.id.fab_back);
        btnBackLogin.setOnClickListener(v -> {
            Intent intent = new Intent(Register_Activity.this, LoginIn_Activity.class);
            onBackPressed();
            startActivity(intent);
            finish();
        });

        // Envia para a página de confirmação de cadastro (código OTP)
        btnConfirmarCad = findViewById(R.id.fab_next_confirmar);
        btnConfirmarCad.setOnClickListener(v -> {

            // REGISTRO: NOME, EMAIL, SENHA, CONFIRMAÇÃO DE SENHA

            UserModel userModel = new UserModel();

            userModel.setNome(edtNomeCad.getText().toString().trim());
            userModel.setEmail(edtEmailCad.getText().toString().trim());
            userModel.setSenha(edtSenhaCad.getText().toString().trim());

            if(!TextUtils.isEmpty(userModel.getNome()) || !TextUtils.isEmpty(userModel.getEmail()) || !TextUtils.isEmpty(userModel.getSenha())){

                auth.createUserWithEmailAndPassword(userModel.getEmail(), userModel.getSenha())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            userModel.setId(auth.getUid()); // pega o ID de usuário e seta no ID
                            userModel.salvar(); // SALVA OS DADOS
                            startActivity(new Intent(Register_Activity.this, Cellphone_Activity.class));
                        }else {
                            String error = task.getException().getMessage();
                            Toast.makeText(Register_Activity.this, ""+error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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

    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}