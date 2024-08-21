package com.example.miaudote.PetInfo;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.miaudote.Fragments.Main_Page;
import com.example.miaudote.Models.AnimalModel;
import com.example.miaudote.ONGInfo.ONG_Register_General;
import com.example.miaudote.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ADD_MissingPet_Page extends AppCompatActivity {

    // FIREBASE
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;

    // LOCALIZAÇÃO
    private final int FINE_PERMISSION_CODE = 1;
    private GoogleMap googleMap;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;

    // WIDGTES
    TextInputEditText edtNomeAnimal, edtDescAnimal, edtCep, edtCidade, edtBairro, edtLogradouro;
    RadioGroup radioGroup;
    RadioButton radioButton;
    ImageView imgAddAnimal;
    FloatingActionButton fabAddFotoAnimal;

    String radioButtonStr, imgAnimalStr, nomeAnimal, descAnimal, ufAnimal, cidadeAnimal, bairroAnimal, logradouroAnimal, endConcat, latAnimal, lngAnimal, animalId, userID;
    String[] itemsUFAnimal = {"AC", "AL", "AP", "AM", "BA", "CE", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB",
                        "PR", "PE", "PI", "RJ", "RN", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO", "DF"};
    ArrayAdapter<String> adapterUF;
    AutoCompleteTextView autoCompleteUf;

    Uri uriImageAnimal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_missing_pet_page);

        // FIREBASE
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        // WIDGETS
        edtNomeAnimal = findViewById(R.id.edtAddAnimal_nome);
        edtDescAnimal = findViewById(R.id.edtAddAnimal_desc);
        // EDT DO CEP
        edtCidade = findViewById(R.id.edtAddAnimal_cidade);
        edtBairro = findViewById(R.id.edtAddAnimal_bairro);
        edtLogradouro = findViewById(R.id.edtAddAnimal_logradouro);

        autoCompleteUf = findViewById(R.id.edtAddAnimalUfAutoComplete);
        adapterUF = new ArrayAdapter<String>(this, R.layout.list_item, itemsUFAnimal);
        autoCompleteUf.setAdapter(adapterUF);

        fabAddFotoAnimal = findViewById(R.id.fabAddAnimal_photo);
        imgAddAnimal = findViewById(R.id.addImgAnimal);

        radioGroup = findViewById(R.id.radioGroup);

        autoCompleteUf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ufAnimal = parent.getItemAtPosition(position).toString();
            }
        });

        AppCompatImageButton btnBack = findViewById(R.id.btnAddAnimal_back);
        btnBack.setOnClickListener(v -> finish());

        AppCompatButton btnSalvar = findViewById(R.id.btnAddAnimal);
        btnSalvar.setOnClickListener(v -> {
            uploadFoto();
        });

        fabAddFotoAnimal.setOnClickListener(v -> escolherFoto());
        imgAddAnimal.setOnClickListener(v -> escolherFoto());

    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if(o.getResultCode() == Activity.RESULT_OK) {
                        Intent data = o.getData();
                        uriImageAnimal = data.getData();
                        Picasso.get().load(uriImageAnimal).resize(130, 130).centerCrop().into(imgAddAnimal);
                    } else {
                        Toast.makeText(ADD_MissingPet_Page.this, "Nenhuma imagem selecionada.", Toast.LENGTH_SHORT).show();
                    }
                }
            });


    public void escolherFoto() {
        Intent photoPicker = new Intent(Intent.ACTION_PICK);
        photoPicker.setType("image/*");
        activityResultLauncher.launch(photoPicker);
    }

    public void uploadFoto() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Imagens Animal")
                .child(uriImageAnimal.getLastPathSegment());

        storageReference.putFile(uriImageAnimal).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imgAnimalStr = urlImage.toString();
                uploadData();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ADD_MissingPet_Page.this, "Falha ao adicionar a imagem", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void uploadData() {

        radioGroup = findViewById(R.id.radioGroup);
        int i = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(i);

        radioButtonStr = radioButton.getText().toString();
        nomeAnimal = edtNomeAnimal.getText().toString();
        descAnimal = edtDescAnimal.getText().toString();
        cidadeAnimal = edtCidade.getText().toString();
        bairroAnimal = edtBairro.getText().toString();
        logradouroAnimal = edtLogradouro.getText().toString();

        databaseReference = FirebaseDatabase.getInstance().getReference("animais").child(radioButtonStr);
        animalId = databaseReference.push().getKey();
        userID = firebaseUser.getUid();

        endConcat = logradouroAnimal + ", " + bairroAnimal + ", " + cidadeAnimal + ", " + ufAnimal;

        Geocoder geocoder = new Geocoder(ADD_MissingPet_Page.this);
        try {
            List<Address> addressList = geocoder.getFromLocationName(endConcat, 1);
            if(addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);
                double lat = address.getLatitude();
                double lng = address.getLongitude();

                latAnimal = String.valueOf(lat);
                lngAnimal = String.valueOf(lng);
                AnimalModel animalModel = new AnimalModel(animalId, imgAnimalStr, radioButtonStr, nomeAnimal, descAnimal, ufAnimal,
                                                        cidadeAnimal, bairroAnimal, logradouroAnimal, latAnimal, lngAnimal, userID);

                // BLOCO DE CÓDIGO QUE ADICIONA O ANIMAL NA TABELA "ANIMAIS"
                // CRIA UMA RAMIFICAÇÃO DEPENDENDO DA CLASSIFICAÇÃO DO ANIMAL (PERDIDO, ADOÇÃO OU ENCONTRADO)
                // CADA RAMIFICAÇÃO RECEBE O ID DO USUÁRIO QUE ESTÁ CADASTRANDO O ANIMAL
                // O ANIMAL É CADASTRADO COM O NOME
                databaseReference.child(animalId).setValue(animalModel)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(ADD_MissingPet_Page.this, "Animal adicionado com sucesso!", Toast.LENGTH_SHORT).show();
                                finish();
                                // BOTAR PROGRESS BAR
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ADD_MissingPet_Page.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }
}