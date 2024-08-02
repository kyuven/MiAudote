package com.example.miaudote.PetInfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.miaudote.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ADD_MissingPet_Page extends AppCompatActivity {

    // FIREBASE
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    // WIDGTES
    TextInputEditText edtNomeAnimal, edtIdadeAnimal, edtDescAnimal, edtCep, edtCidade, edtBairro, edtLogradouro;
    RadioGroup radioGroup;

    String[] itemsEspecie = {"Cachorro", "Gato", "Outros"};
    String[] itemsUF = {"AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"};
    ArrayAdapter<String> adapterUF, adapterEspecie;
    AutoCompleteTextView autoCompleteEspecie, autoCompleteUf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_missing_pet_page);

        // FIREBASE
        mAuth = FirebaseAuth.getInstance();

        // WIDGETS
        edtNomeAnimal = findViewById(R.id.edtAddAnimal_nome);
        edtDescAnimal = findViewById(R.id.edtAddAnimal_desc);
        // EDT DO CEP
        edtCidade = findViewById(R.id.edtAddAnimal_cidade);
        edtBairro = findViewById(R.id.edtAddAnimal_bairro);
        edtLogradouro = findViewById(R.id.edtAddAnimal_logradouro);

        radioGroup = findViewById(R.id.radioGroup);

        AppCompatButton btnSalvar = findViewById(R.id.btnAddAnimal);
        btnSalvar.setOnClickListener(v -> {
            uploadData();
        });

    }

    public void uploadData() {

    }
}