package com.example.miaudote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class ADD_MissingPet_Page extends AppCompatActivity {

    TextInputEditText edtNomeAnimal, edtIdadeAnimal, edtDescAnimal, edtLogradouro, edtBairro, edtCidade;
    String[] itemsAnimalTipo = {"Visto", "Resgatado"};
    String[] itemsEspecie = {"Cachorro", "Gato", "Outros"};
    String[] itemsUF = {"AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"};
    ArrayAdapter<String> adapterTipoAnimal, adapterUF;
    AutoCompleteTextView autoCompleteAnimalTipo, autoCompleteEspecie, autoCompleteUf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_missing_pet_page);

        ImageView imgFotoAnimal = (ImageView) findViewById(R.id.imgAddMissingPet);
        FloatingActionButton fabAddFoto = (FloatingActionButton) findViewById(R.id.fabMissingAnimal_photo);

        AppCompatButton btnSalvarAnimalPerdido = (AppCompatButton) findViewById(R.id.btnMissingAnimal_salvar);
        AppCompatImageButton btn_back = (AppCompatImageButton) findViewById(R.id.btnAddAdpt_back);

        edtNomeAnimal = findViewById(R.id.edtMissingAnimal_nomeAnimal);
        edtIdadeAnimal = findViewById(R.id.edtMissingAnimal_idade);
        edtDescAnimal = findViewById(R.id.edtMissingAnimal_desc);
        edtLogradouro = findViewById(R.id.edtMissingAnimal_logradouro);
        edtBairro = findViewById(R.id.edtMissingAnimal_bairro);
        edtCidade = findViewById(R.id.edtMissingAnimal_cidade);

        autoCompleteEspecie = findViewById(R.id.edtAddMissingPet_filtrar);
        autoCompleteAnimalTipo = findViewById(R.id.edtMissingAnimal_especieAutoComplete);
        autoCompleteUf = findViewById(R.id.edtMissingAnimal_ufAutoComplete);

        adapterUF = new ArrayAdapter<String>(this, R.layout.list_item, itemsUF);
        autoCompleteUf.setAdapter(adapterUF);
        autoCompleteUf.setOnItemClickListener((parent, view, position, id) -> {
            String item = parent.getItemAtPosition(position).toString();

            // Lógica para salvar no banco de dados ao invés do toast
            Toast.makeText(getApplicationContext(), "Item:" + item, Toast.LENGTH_SHORT).show();
        });

        btnSalvarAnimalPerdido.setOnClickListener(v -> {
            // Lógica para salvar no firebase os dados
            Intent intent = new Intent(ADD_MissingPet_Page.this, Main_Page.class);
            startActivity(intent);
            finish();
        });

        btn_back.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}