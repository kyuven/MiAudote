package com.example.miaudote.ONGInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.miaudote.R;
import com.squareup.picasso.Picasso;

public class ONG_Page extends AppCompatActivity {

    // WIDGETS
    ImageView imgHeaderONG, imgLogoONG;
    TextView txtCidadeONG, txtUFONG, txtNomeONG, txtDescricaoONG, txtLocalizaoONG, txtWebsiteONG, txtTelefoneONG, txtInstagramONG, txtEmailONG, txtTwitterONG;
    String bairroOngStr, lograOngStr, concatEndOng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ong_page);

        // INICIALIZA OS WIDGETS
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

        // PEGA AS INFORMAÇÕES DO CARD E COLOCA NOS RESPECTIVOS CAMPOS
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            Picasso.get()
                    .load(bundle.getString("Imagem ONG"))
                    .resize(132, 132)
                    .centerCrop()
                    .into(imgLogoONG);

            txtNomeONG.setText(bundle.getString("Nome ONG"));
            txtDescricaoONG.setText(bundle.getString("Descrição ONG"));

            txtCidadeONG.setText(bundle.getString("Cidade ONG"));
            txtUFONG.setText(bundle.getString("UF ONG"));
            bairroOngStr = bundle.getString("Bairro ONG");
            lograOngStr = bundle.getString("Logradouro ONG");
            concatEndOng = lograOngStr + ", " + bairroOngStr;
            txtLocalizaoONG.setText(concatEndOng);

            txtWebsiteONG.setText(bundle.getString("Website ONG"));
            txtTelefoneONG.setText(bundle.getString("Telefone ONG"));
            txtInstagramONG.setText(bundle.getString("Instagram ONG"));
            txtEmailONG.setText(bundle.getString("Email ONG"));
            txtTwitterONG.setText(bundle.getString("Twitter ONG"));

        }

    }
}