package com.example.miaudote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class Confirmar_Login extends AppCompatActivity {

    EditText inputCodigo01, inputCodigo02, inputCodigo03, inputCodigo04, inputCodigo05, inputCodigo06;
    Button btn_reenviar;

    private boolean reenviarEnabled = false;

    private int reenviarTime = 60;

    private int selectedInputCodigo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_login);

        inputCodigo01 = findViewById(R.id.inputCodigo01);
        inputCodigo02 = findViewById(R.id.inputCodigo02);
        inputCodigo03 = findViewById(R.id.inputCodigo03);
        inputCodigo04 = findViewById(R.id.inputCodigo04);
        inputCodigo05 = findViewById(R.id.inputCodigo05);
        inputCodigo06 = findViewById(R.id.inputCodigo06);
        btn_reenviar = findViewById(R.id.btn_reenviar);

        final String telefone = getIntent().getStringExtra("telefone");

        inputCodigo01.addTextChangedListener(textWatcher);
        inputCodigo02.addTextChangedListener(textWatcher);
        inputCodigo03.addTextChangedListener(textWatcher);
        inputCodigo04.addTextChangedListener(textWatcher);
        inputCodigo05.addTextChangedListener(textWatcher);
        inputCodigo06.addTextChangedListener(textWatcher);

        // Abrir o teclado para digitar no código 01
        showKeybord(inputCodigo01);

        // Contagem para reenviar o código
        startCountDownTime();

        btn_reenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(reenviarEnabled){

                    // Reiniciar a contagem para reenvio do código
                    startCountDownTime();
                }

            }
        });



        // Confirma o cadastro do usuário e envia para a página principal
        ImageButton btnCadastrar = (ImageButton) findViewById(R.id.btn_final_cad);
        btnCadastrar.setOnClickListener(v -> {

            final String generateOtp = inputCodigo01.getText().toString()+inputCodigo02.getText().toString()+inputCodigo03.getText().toString()+
                    inputCodigo04.getText().toString()+inputCodigo05.getText().toString()+inputCodigo06.getText().toString();

            if (generateOtp.length() == 4){

            }

            Intent intent = new Intent(Confirmar_Login.this, Main_Page.class);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            startActivity(intent);
            finish();
        });
    }


    private void showKeybord(EditText inputCodigo){

        inputCodigo.requestFocus();

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(inputCodigo, InputMethodManager.SHOW_IMPLICIT);
    }

    private void startCountDownTime(){
        reenviarEnabled = false;
        btn_reenviar.setTextColor(Color.parseColor("#99000000"));

        new CountDownTimer(reenviarTime * 1000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                btn_reenviar.setText("Reenviar Código ("+(millisUntilFinished /1000) +")");
            }

            @Override
            public void onFinish() {
                reenviarEnabled = true;
                btn_reenviar.setText("Reenviar código");
                btn_reenviar.setTextColor(getResources().getColor(R.color.blue_link));
            }
        }.start();
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            if(s.length() > 0){
                if(selectedInputCodigo == 0){

                    selectedInputCodigo = 1;
                    showKeybord(inputCodigo02);
                }

                else if (selectedInputCodigo == 1){

                    selectedInputCodigo = 2;
                    showKeybord(inputCodigo03);
                }

                else if (selectedInputCodigo == 2){

                    selectedInputCodigo = 3;
                    showKeybord(inputCodigo04);
                }

                else if (selectedInputCodigo == 3){

                    selectedInputCodigo = 4;
                    showKeybord(inputCodigo05);
                }

                else if (selectedInputCodigo == 4){

                    selectedInputCodigo = 5;
                    showKeybord(inputCodigo06);
                }
            }
        }
    };

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL){

            if (selectedInputCodigo == 5){
                selectedInputCodigo = 4;

                showKeybord(inputCodigo05);
            }

            else if (selectedInputCodigo == 4) {
                selectedInputCodigo = 3;

                showKeybord(inputCodigo04);
            }

            else if(selectedInputCodigo == 3 ){
                selectedInputCodigo = 2;

                showKeybord(inputCodigo03);
            }

            else if (selectedInputCodigo == 2) {
                selectedInputCodigo = 1;

                showKeybord(inputCodigo02);
            }

            else if (selectedInputCodigo == 1) {
                 selectedInputCodigo = 0;

                 showKeybord(inputCodigo01);
            }

            return true;

        } else {
            return super.onKeyUp(keyCode, event);
        }
    }

    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}