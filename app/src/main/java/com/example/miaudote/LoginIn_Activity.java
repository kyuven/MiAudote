package com.example.miaudote;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.contentcapture.DataShareRequest;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

public class LoginIn_Activity extends AppCompatActivity {

    FirebaseAuth auth;
    GoogleSignInClient googleSignInClient;

    TextInputEditText edtEmailLogin, edtSenhaLogin;
    ImageButton imgBtnGoogle, imgBtnFacebook;
    Button btnCadastrar, btnEntrar;

    // Login com Google
    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            if(result.getResultCode() == RESULT_OK) {
                Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                try {
                    GoogleSignInAccount signInAccount = accountTask.getResult(ApiException.class);
                    AuthCredential authCredential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
                    auth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                auth = FirebaseAuth.getInstance();
                                Glide.with(LoginIn_Activity.this).load(Objects.requireNonNull(auth.getCurrentUser()).getPhotoUrl()).into(imagemPerfil);
                                nomePerfil.setText(auth.getCurrentUser().getDisplayName());
                                emailPerfil.setText(auth.getCurrentUser().getEmail());
                                Toast.makeText(LoginIn_Activity.this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginIn_Activity.this, "Falha no login: " + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (ApiException e){
                    e.printStackTrace();
                }
            }
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_in);

        FirebaseApp.initializeApp(this);
        imagemPerfil = findViewById(R.id.imgPerfil_user);
        nomePerfil = findViewById(R.id.txtPerfil_nomeUser);
        emailPerfil = findViewById(R.id.txtPerfil_emailUser);

        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(LoginIn_Activity.this, options);

        auth = FirebaseAuth.getInstance();

        SignInButton signInButton = findViewById(R.id.signIn);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = googleSignInClient.getSignInIntent();
                activityResultLauncher.launch(intent);
            }
        });


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
            Intent intent = new Intent(LoginIn_Activity.this, Main_Page.class);
            startActivity(intent);
            finish();
        });
    }
}