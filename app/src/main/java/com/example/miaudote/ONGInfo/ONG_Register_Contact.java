package com.example.miaudote.ONGInfo;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.miaudote.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;

public class ONG_Register_Contact extends AppCompatActivity {

    TextInputEditText edtTelOng, edtInstaOng, edtEmailOng, edtTwitterOng, edtFaceOng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ong_register_contact);

        edtTelOng = findViewById(R.id.edtOngTel);
        edtInstaOng = findViewById(R.id.edtOngInstagram);
        edtEmailOng = findViewById(R.id.edtOngEmail);
        edtTwitterOng = findViewById(R.id.edtOngTwitter);
        edtFaceOng = findViewById(R.id.edtOngFacebook);

        AppCompatButton btnSalvar = findViewById(R.id.btnAddOng);
        btnSalvar.setOnClickListener(v -> saveDataContato());
    }

    public void saveDataContato() {
        HashMap<String, String> map = new HashMap<>();

        map.put("telOng", edtTelOng.getText().toString());
        map.put("instaOng", edtInstaOng.getText().toString());
        map.put("emailOng", edtEmailOng.getText().toString());
        map.put("twitterOng", edtTwitterOng.getText().toString());
        map.put("faceOng", edtFaceOng.getText().toString());
    }
}