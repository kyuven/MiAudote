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
import android.widget.Toast;

import com.example.miaudote.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ADD_MissingPet_Page extends AppCompatActivity {

    // FIREBASE
    FirebaseAuth mAuth;

    // WIDGTES
    TextInputEditText edtNomeAnimal, edtIdadeAnimal, edtDescAnimal, edtCep, edtCidade, edtBairro, edtLogradouro;

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
        edtNomeAnimal = findViewById(R.id.edtAddAnimal_nomeAnimal);
        edtIdadeAnimal = findViewById(R.id.edtAddAnimalIdade);
        edtDescAnimal = findViewById(R.id.edtAddAnimal_desc);
        // EDT DO CEP
        edtCidade = findViewById(R.id.edtAddAnimalCidade);
        edtBairro = findViewById(R.id.edtAddAnimalBairro);
        edtLogradouro = findViewById(R.id.edtAddAnimalLogradouro);

        AppCompatButton btnSalvar = findViewById(R.id.btnAddAnimal);
        btnSalvar.setOnClickListener(v -> {
            uploadData();
        });

    }

    public void uploadData() {

        // REGISTRO DE ANIMAIS PERDIDOS, PARA ADOÇÃO E ENCONTRADO/RESGATADO
        String nomeAnimal = edtNomeAnimal.getText().toString().trim();
        String idadeAnimal = edtIdadeAnimal.getText().toString().trim();
        String descAnimal = edtDescAnimal.getText().toString().trim();
        AnimalModel animalModel = new AnimalModel(nomeAnimal, idadeAnimal, descAnimal);

        FirebaseDatabase.getInstance().getReference("Animais").child(nomeAnimal)
                .setValue(animalModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(ADD_MissingPet_Page.this, "Salvo", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ADD_MissingPet_Page.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }
}