package com.example.miaudote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginIn_Activity extends AppCompatActivity {

    FirebaseAuth auth;
    TextInputEditText edtEmailLogin, edtSenhaLogin;
    ImageButton imgBtnGoogle, imgBtnFacebook;
    Button btnCadastrar, btnEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_in);

        auth = FirebaseAuth.getInstance();

        edtEmailLogin = findViewById(R.id.LogIn_email);
        edtSenhaLogin = findViewById(R.id.LogIn_senha);

        imgBtnGoogle = findViewById(R.id.btnLogin_google);
        imgBtnFacebook = findViewById(R.id.btnLogin_facebook);

        CheckBox ckbMostrarSenha = (CheckBox) findViewById(R.id.ckbLogin_mostrarSenha);

        // Usuário não cadastrado
        btnCadastrar = findViewById(R.id.btn_semCadastro);

        btnCadastrar.setOnClickListener(v -> {
            Intent intent = new Intent(LoginIn_Activity.this, Register_Activity.class);
            startActivity(intent);
            finish();
        });

        // Entrada de usuário já cadastrado
        btnEntrar = findViewById(R.id.btn_entrar);

        btnEntrar.setOnClickListener(v -> {

            String email = edtEmailLogin.getText().toString();
            String senha = edtSenhaLogin.getText().toString();

            if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                if (!senha.isEmpty()){
                    auth.signInWithEmailAndPassword(email, senha)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Toast.makeText(LoginIn_Activity.this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginIn_Activity.this, Main_Page.class));
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(LoginIn_Activity.this, "Login falhou!", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    edtSenhaLogin.setError("Senha não pode estar vazio");
                }
            } else if (email.isEmpty()){
                edtEmailLogin.setError("Email não pode estar vazio");
            } else {
                edtEmailLogin.setError("Por favor, insira um email válido");
            }
        });
    }
}