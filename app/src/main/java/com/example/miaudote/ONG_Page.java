package com.example.miaudote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.os.Bundle;
import android.widget.TextView;

public class ONG_Page extends AppCompatActivity {

    AppCompatImageView imgHeaderONG, imgLogoONG;
    TextView txtCidadeONG, txtUFONG, txtNomeONG, txtDescricaoONG, txtLocalizaoONG, txtWebsiteONG, txtTelefoneONG, txtInstagramONG, txtEmailONG, txtTwitterONG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ong_page);

        imgHeaderONG = findViewById(R.id.imgOngPage_header);
        imgLogoONG = findViewById(R.id.imgOngPage_icon);

        txtCidadeONG = findViewById(R.id.txtOngPage_CdOng);
        txtUFONG = findViewById(R.id.txtOngPage_UfOng);
        txtNomeONG = findViewById(R.id.txtOngPage_ongName);
        txtDescricaoONG = findViewById(R.id.txtOngPage_ongDescricao);
        txtLocalizaoONG = findViewById(R.id.txtOngPage_location);
        txtWebsiteONG = findViewById(R.id.txtOngPage_webSite);
        txtTelefoneONG = findViewById(R.id.txtOngPage_telefone);
        txtInstagramONG = findViewById(R.id.txtOngPage_instagram);
        txtEmailONG = findViewById(R.id.txtOngPage_gmail);
        txtTwitterONG = findViewById(R.id.txtOngPage_twitter);

    }
}