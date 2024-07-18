package com.example.miaudote.PetInfo;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.miaudote.R;
import com.google.android.material.textfield.TextInputEditText;

public class Actv_EdtDadosAnimal extends AppCompatActivity {

    TextInputEditText edtNovoNomeAnimal, edtNovaIdadeAnimal, edtNovaDescAnimal, edtNovoLogradouro, edtNovoBairro, edtNovoCidade;
    String[] itemsEditarEspecie = {"Cachorro", "Gato", "Outros"};
    String[] itemsEditarUF = {"AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"};
    ArrayAdapter<String> adapterEditarUF, adapterEditarEspecie;
    AutoCompleteTextView autoCompleteEditarEspecie, autoEditarCompleteUf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actv_edt_dados_animal);

    }
}