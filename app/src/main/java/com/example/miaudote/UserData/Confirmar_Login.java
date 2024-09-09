package com.example.miaudote.UserData;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.miaudote.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Confirmar_Login extends AppCompatActivity {

    // FIREBASE
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    // WIDGETS
    EditText inputCodigo;
    Button btn_reenviar;
    ImageButton btn_finalcad;
    String StrTelefone, telefone, verificationCode;

    PhoneAuthProvider.ForceResendingToken resendingToken;
    Long timeoutSeconds = 60L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_login);

        // REFERENCIA OS WIDGETS
        inputCodigo = findViewById(R.id.inputCodigo);
        btn_finalcad = findViewById(R.id.btn_final_cad);
        btn_reenviar = findViewById(R.id.btn_reenviar);

        // PEGA A STRING TELEFONE E AS INFORMAÇÕES PASSADAS NA ATIVIDADE ANTERIOR (CELLPHONE_ACTIVITY)
        StrTelefone = getIntent().getExtras().getString("telefone");
        telefone = "+55" + StrTelefone; // PASSA O DDI DO PAÍS

        sendOTP(telefone, false);

        // AO CLICAR NO BOTÃO, CHAMA O MÉTODO DE VERIFICAÇÃO DE OTP
        btn_finalcad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredOTP = inputCodigo.getText().toString();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, enteredOTP);
                signIn(credential);
            }
        });

        // REENVIA O CÓDIGO DE OTP
        btn_reenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOTP(telefone, true);
            }
        });
    }

    // MÉTODO PARA REENVIAR O CÓDIGO OTP
    void sendOTP(String telefone, boolean isResend){
        startResendTimer();
        PhoneAuthOptions.Builder builder =
                PhoneAuthOptions.newBuilder(mAuth) // ACESSA O AUTHENTICATION (TELEFONE) DO FIREBASE
                        .setPhoneNumber(telefone) // TELEFONE
                        .setTimeout(timeoutSeconds, TimeUnit.SECONDS) // DEFINE UM TEMPO EM SEGUNDOS
                        .setActivity(this) // NESSA ATIVIDADE
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(Confirmar_Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                verificationCode = s;
                                resendingToken = forceResendingToken;
                                Toast.makeText(Confirmar_Login.this, "Verificação OTP foi realizada com sucesso!", Toast.LENGTH_SHORT).show();
                            }
                        });
        if(isResend){
            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(resendingToken).build());
        }else {
            PhoneAuthProvider.verifyPhoneNumber(builder.build());
        }
    }

    // AUTENTICA O TELEFONE DO USUÁRIO
    void signIn(PhoneAuthCredential phoneAuthCredential){
        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    // SE BEM SUCEDIDO A ATUENTICAÇÃO, ENVIA PARA A ATIVIDADE DE LOGIN
                    Intent intent = new Intent(Confirmar_Login.this, LoginIn_Activity.class);
                    intent.putExtra("telefone", telefone);
                    startActivity(intent);
                } else {
                    Toast.makeText(Confirmar_Login.this, "Verificação OTP falhou!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // MÉTODO PARA RECOMEÇAR O CONTADOR DE TEMPO PARA REENVIAR O CÓDIGO OTP
    void startResendTimer(){
        btn_reenviar.setEnabled(false);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeoutSeconds--;
                btn_reenviar.setText("Reenviar OTP em "+timeoutSeconds +" segundos");
                if (timeoutSeconds<=0){
                    timeoutSeconds = 60L;
                    timer.cancel();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btn_reenviar.setEnabled(true);
                        }
                    });
                }
            }
        }, 0, 1000);
    }

}