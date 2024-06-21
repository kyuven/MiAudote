package com.example.miaudote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.auth.FirebaseUser;


public class LoginIn_Activity extends AppCompatActivity {

    private FirebaseAuth auth, mAuth;

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

        // Login com Facebook
        FacebookSdk.sdkInitialize(LoginIn_Activity.this);

        // Inicialização Facebook Login botão
        CallbackManager mCallbackManager = CallbackManager.Factory.create();
        imgBtnFacebook.setReadPermissions("email", "public_profile");
        imgBtnFacebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
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

    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginIn_Activity.this, "Autenticação falhou.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null){
            Intent intent = new Intent(LoginIn_Activity.this, Main_Page.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Por favor, registre-se para continuar!", Toast.LENGTH_SHORT);
        }
    };
}