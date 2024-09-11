package com.example.miaudote.UserData;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

import com.example.miaudote.Fragments.Main_Page;
import com.example.miaudote.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class LoginIn_Activity extends AppCompatActivity {

    // FIREBASE
    private FirebaseAuth auth;

    // WIDGETS
    TextInputEditText edtEmailLogin, edtSenhaLogin;
    Button btnCadastrar, btnEntrar;

    @Override
    protected void onStart() {
        super.onStart();

        // VERIFICA SE O USUÁRIO JÁ ESTÁ LOGADO
        // SE SIM, VAI PRA PÁGINA PRINCIPAL
        FirebaseUser userAlrdLogin = FirebaseAuth.getInstance().getCurrentUser();
        if(userAlrdLogin != null) {
            Intent i = new Intent(LoginIn_Activity.this, Main_Page.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_in);

        // REFERENCIA E INICIALIZA O BANCO DE DADOS
        auth = FirebaseAuth.getInstance();
        FirebaseApp.initializeApp(this);

        // REFERENCIA OS TEXTINPUTEDITTEXT
        edtEmailLogin = findViewById(R.id.LogIn_email);
        edtSenhaLogin = findViewById(R.id.LogIn_senha);

        CheckBox ckbLoginMostrarSenha = (CheckBox) findViewById(R.id.ckbLogin_mostrarSenha);

        // CASO O USUÁRIO NÃO POSSUA CADASTRO
        // É ENVIADO PARA A PÁGINA DE CADASTRO
        btnCadastrar = findViewById(R.id.btn_semCadastro);
        btnCadastrar.setOnClickListener(v -> {
            Intent i = new Intent(LoginIn_Activity.this, Register_Activity.class);
            startActivity(i);
        });

        // ENTRADA DE USUÁRIO JÁ CADASTRADO
        btnEntrar = findViewById(R.id.btn_entrar);
        btnEntrar.setOnClickListener(v -> {

            // PEGA OS VALORES DO TEXTINPUTTEXT E TRANSFORMA EM STRING
            String email = edtEmailLogin.getText().toString();
            String senha = edtSenhaLogin.getText().toString();

            // VERIFICA SE OS VALORES INSERIDOS NO E-MAIL SÃO DIFERENTES
            // DE NULO E TAMBÉM DE É IGUAL A ALGUM JÁ CADASTRADO
            if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                if (!senha.isEmpty()){
                    // SE O EMAIL FOR IGUAL E A SENHA
                    // DIFERENTE DE NULO, CHAMA O MÉTODO
                    auth.signInWithEmailAndPassword(email, senha)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        // SE FOR BEM SUCEDIDO, ENVIA PARA A ATIVIDADE PRINCIPAL
                                        startActivity(new Intent(LoginIn_Activity.this, Main_Page.class));
                                    }else {
                                        // SE NÃO FOR BEM SUCEDIDO REALIZA TRATAMENTO DE ERRO
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

        // CHECKBOX - MOSTRAR SENHA
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