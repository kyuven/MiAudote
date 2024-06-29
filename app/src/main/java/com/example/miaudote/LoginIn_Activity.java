package com.example.miaudote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



public class LoginIn_Activity extends AppCompatActivity {

    private FirebaseAuth auth;
    private static final String EMAIL = "email";
    TextInputEditText edtEmailLogin, edtSenhaLogin;
    LoginButton btnFacebook;
    Button btnCadastrar, btnEntrar, btnGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_in);

        auth = FirebaseAuth.getInstance();

        edtEmailLogin = findViewById(R.id.LogIn_email);
        edtSenhaLogin = findViewById(R.id.LogIn_senha);

        btnGoogle = findViewById(R.id.btnLogin_google);
        btnFacebook = findViewById(R.id.btnLogin_facebook);

        CheckBox ckbLoginMostrarSenha = (CheckBox) findViewById(R.id.ckbLogin_mostrarSenha);

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
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        startActivity(new Intent(LoginIn_Activity.this, Main_Page.class));
                                    }else {
                                        String error = task.getException().getMessage();
                                        Toast.makeText(LoginIn_Activity.this, ""+error, Toast.LENGTH_SHORT).show();
                                    }
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

        // Mostrar Senha - Checkbox
        ckbLoginMostrarSenha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    edtSenhaLogin.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    edtSenhaLogin.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }
}