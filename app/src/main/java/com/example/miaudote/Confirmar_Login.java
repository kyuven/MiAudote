package com.example.miaudote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

public class Confirmar_Login extends AppCompatActivity {

    EditText inputCodigo01, inputCodigo02, inputCodigo03, inputCodigo04, inputCodigo05, inputCodigo06;

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

        // Confirma o cadastro do usuário e envia para a página principal
        ImageButton btnCadastrar = (ImageButton) findViewById(R.id.btn_final_cad);
        btnCadastrar.setOnClickListener(v -> {
            Intent intent = new Intent(Confirmar_Login.this, Main_Page.class);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            startActivity(intent);
            finish();
        });
    }

    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}