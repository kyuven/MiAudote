package com.example.miaudote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ADD_AdoptionPet_Page extends AppCompatActivity {

    TextInputEditText edtNomeAnimal, edtIdadeAnimal, edtDescAnimal;
    String[] itemsDropdown = {"Cachorro", "Gato", "Outros"};
    String[] itemsUF = {"AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"};
    ArrayAdapter<String> adapterItems, adapterUF, adapterCidade;
    AutoCompleteTextView autoCompleteDropdown, autoCompleteUf, autoCompleteCidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_adoption_pet_page);

        ImageView imgFotoAnimal = (ImageView) findViewById(R.id.imgAddAdpt_fotoAnimal);
        FloatingActionButton fabAddFoto = (FloatingActionButton) findViewById(R.id.fabAddAdpt_uploadImg);

        AppCompatButton btnSalvarAnimalAdocao = (AppCompatButton) findViewById(R.id.btnAddAdpt_salvar);
        AppCompatImageButton btnAddAdpt_back = (AppCompatImageButton) findViewById(R.id.btnAddAdpt_back);

        autoCompleteDropdown = findViewById(R.id.edtAddAdpt_especieAutoComplete);
        autoCompleteUf = findViewById(R.id.edtAddAdpt_ufAutoComplete);
        autoCompleteCidade = findViewById(R.id.edtAddAdpt_cidadeAutoComplete);

        edtNomeAnimal = findViewById(R.id.edtAddAdpt_nomeAnimal);
        edtIdadeAnimal = findViewById(R.id.edtAddAdpt_idade);
        edtDescAnimal = findViewById(R.id.edtAddAdpt_desc);

        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item, itemsDropdown);
        autoCompleteDropdown.setAdapter(adapterItems);
        autoCompleteDropdown.setOnItemClickListener((parent, view, position, id) -> {

            String item = parent.getItemAtPosition(position).toString();

            // Lógica para salvar no banco de dados ao invés do toast
            Toast.makeText(getApplicationContext(), "Item:" + item, Toast.LENGTH_SHORT).show();
        });

        adapterUF = new ArrayAdapter<String>(this, R.layout.list_item, itemsUF);
        autoCompleteUf.setAdapter(adapterUF);
        autoCompleteUf.setOnItemClickListener((parent, view, position, id) -> {
            String item = parent.getItemAtPosition(position).toString();


            // Lógica para salvar no banco de dados ao invés do toast
            Toast.makeText(getApplicationContext(), "Item:" + item, Toast.LENGTH_SHORT).show();
        });

        btnSalvarAnimalAdocao.setOnClickListener(v -> {
            // Lógica para salvar no firebase os dados
            Intent intent = new Intent(ADD_AdoptionPet_Page.this, Main_Page.class);
            startActivity(intent);
            finish();
        });

        btnAddAdpt_back.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}