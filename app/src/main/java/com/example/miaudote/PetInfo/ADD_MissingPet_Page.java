package com.example.miaudote.PetInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.miaudote.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class ADD_MissingPet_Page extends AppCompatActivity {

    TextInputEditText edtNomeAnimal, edtIdadeAnimal, edtDescAnimal, edtLogradouro, edtBairro, edtCidade;
    String[] itemsEspecie = {"Cachorro", "Gato", "Outros"};
    String[] itemsUF = {"AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"};
    ArrayAdapter<String> adapterUF, adapterEspecie;
    AutoCompleteTextView autoCompleteEspecie, autoCompleteUf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_missing_pet_page);

    }
}