package com.example.miaudote.ONGInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.miaudote.R;

public class ONG_Page extends AppCompatActivity {

    ImageView imgHeaderONG, imgLogoONG;
    TextView txtCidadeONG, txtUFONG, txtNomeONG, txtDescricaoONG, txtLocalizaoONG, txtWebsiteONG, txtTelefoneONG, txtInstagramONG, txtEmailONG, txtTwitterONG;
    String bairroOngStr, lograOngStr, concatEndOng;

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

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            Glide.with(this).load(bundle.getString("Imagem ONG")).override(94, 94).into(imgLogoONG);

            txtNomeONG.setText(bundle.getString("Nome ONG"));
            txtDescricaoONG.setText(bundle.getString("Descrição ONG"));
            bairroOngStr = bundle.getString("Bairro ONG");
            lograOngStr = bundle.getString("Logradouro ONG");
            concatEndOng = bairroOngStr + ' ' + lograOngStr;
            txtLocalizaoONG.setText(concatEndOng);
            txtWebsiteONG.setText(bundle.getString("Website ONG"));
            txtTelefoneONG.setText(bundle.getString("Telefone ONG"));
            txtInstagramONG.setText(bundle.getString("Instagram ONG"));
            txtEmailONG.setText(bundle.getString("Email ONG"));
            txtTwitterONG.setText(bundle.getString("Twitter ONG"));

        }

    }
}