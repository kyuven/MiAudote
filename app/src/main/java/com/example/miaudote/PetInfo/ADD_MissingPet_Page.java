package com.example.miaudote.PetInfo;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miaudote.Fragments.Main_Page;
import com.example.miaudote.Fragments.Map_Animals_Fragment;
import com.example.miaudote.Models.AnimalModel;
import com.example.miaudote.ONGInfo.ONG_Register_General;
import com.example.miaudote.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
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
import java.util.Locale;

public class ADD_MissingPet_Page extends AppCompatActivity implements OnMapReadyCallback {

    // FIREBASE
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;

    // LOCALIZAÇÃO
    private final int FINE_PERMISSION_CODE = 1;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    GoogleMap myMap;
    LatLng userAnimalLatLng;

    // WIDGTES
    TextView txtUf, txtCidade, txtBairo, txtLogra;
    TextInputEditText edtNomeAnimal, edtDescAnimal, edtCidade, edtBairro, edtLogradouro;
    RadioGroup radioGroup;
    RadioButton radioButton;
    ImageView imgAddAnimal;
    FloatingActionButton fabAddFotoAnimal;
    AppCompatCheckBox ckbEnd;
    TextInputLayout txtInptLyt;
    ProgressBar progressBar;

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
        txtInptLyt = findViewById(R.id.txtInptUf);
        txtUf = findViewById(R.id.txtAddAnimalUF);
        txtCidade = findViewById(R.id.txtAddAnimalCidade);
        txtBairo = findViewById(R.id.txtAddAnimalBairro);
        txtLogra = findViewById(R.id.txtAddAnimalLogradouro);

        edtNomeAnimal = findViewById(R.id.edtAddAnimal_nome);
        edtDescAnimal = findViewById(R.id.edtAddAnimal_desc);
        edtCidade = findViewById(R.id.edtAddAnimal_cidade);
        edtBairro = findViewById(R.id.edtAddAnimal_bairro);
        edtLogradouro = findViewById(R.id.edtAddAnimal_logradouro);

        ckbEnd = findViewById(R.id.ckbEndAtual);
        progressBar = findViewById(R.id.progressBarAddAnimal);
        progressBar.setVisibility(View.INVISIBLE);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapAddAnimal);
        mapFragment.getMapAsync(this);
        getLastLocation();

        autoCompleteUf = findViewById(R.id.edtAddAnimalUfAutoComplete);
        adapterUF = new ArrayAdapter<String>(this, R.layout.list_item, itemsUFAnimal);
        autoCompleteUf.setAdapter(adapterUF);

        fabAddFotoAnimal = findViewById(R.id.fabAddAnimal_photo);
        imgAddAnimal = findViewById(R.id.addImgAnimal);

        radioGroup = findViewById(R.id.radioGroup);

        ckbEnd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    txtInptLyt.setVisibility(View.GONE);
                    txtUf.setVisibility(View.GONE);
                    txtCidade.setVisibility(View.GONE);
                    txtBairo.setVisibility(View.GONE);
                    txtLogra.setVisibility(View.GONE);
                    autoCompleteUf.setVisibility(View.GONE);
                    edtCidade.setVisibility(View.GONE);
                    edtBairro.setVisibility(View.GONE);
                    edtLogradouro.setVisibility(View.GONE);
                    mapFragment.getView().setVisibility(View.VISIBLE);
                    getLastLocation();
                } else {
                    txtInptLyt.setVisibility(View.VISIBLE);
                    txtUf.setVisibility(View.VISIBLE);
                    txtCidade.setVisibility(View.VISIBLE);
                    txtBairo.setVisibility(View.VISIBLE);
                    txtLogra.setVisibility(View.VISIBLE);
                    autoCompleteUf.setVisibility(View.VISIBLE);
                    edtCidade.setVisibility(View.VISIBLE);
                    edtBairro.setVisibility(View.VISIBLE);
                    edtLogradouro.setVisibility(View.VISIBLE);
                    mapFragment.getView().setVisibility(View.GONE);
                }
            }
        });

        autoCompleteUf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ufAnimal = parent.getItemAtPosition(position).toString();
            }
        });

        txtInptLyt.setVisibility(View.GONE);
        txtUf.setVisibility(View.GONE);
        txtCidade.setVisibility(View.GONE);
        txtBairo.setVisibility(View.GONE);
        txtLogra.setVisibility(View.GONE);
        autoCompleteUf.setVisibility(View.GONE);
        edtCidade.setVisibility(View.GONE);
        edtBairro.setVisibility(View.GONE);
        edtLogradouro.setVisibility(View.GONE);

        AppCompatImageButton btnBack = findViewById(R.id.btnAddAnimal_back);
        btnBack.setOnClickListener(v -> finish());

        AppCompatButton btnSalvar = findViewById(R.id.btnAddAnimal);
        btnSalvar.setOnClickListener(v -> {
            if (edtNomeAnimal.getText().toString().isEmpty()) {
                Toast.makeText(this, "O nome não pode ser nulo.", Toast.LENGTH_SHORT).show();
            } else if (edtDescAnimal.getText().toString().isEmpty()) {
                Toast.makeText(this, "A descrição não pode ser nula.", Toast.LENGTH_SHORT).show();
            } else {
                if(ckbEnd.isChecked()) {
                    reverseGeocoding();
                } else {
                    uploadFoto();
                }
            }
            progressBar.setVisibility(View.VISIBLE);
        });

        fabAddFotoAnimal.setOnClickListener(v -> escolherFoto());
        imgAddAnimal.setOnClickListener(v -> escolherFoto());

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;
        if (currentLocation != null) {
            userAnimalLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            myMap.addMarker(new MarkerOptions().position(userAnimalLatLng).title("Você está aqui"));
            myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userAnimalLatLng, 13));
        } else {
            Toast.makeText(this, "Não foi possível obter sua localização.", Toast.LENGTH_SHORT).show();
        }
    }

    // PEGA A LOCALIZAÇÃO DO USUÁRIO
    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
            return;
        }
        com.google.android.gms.tasks.Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null) {
                    currentLocation = location;
                    // Atualize o mapa aqui se o mapa já estiver pronto
                    if (myMap != null) {
                        userAnimalLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                        myMap.addMarker(new MarkerOptions().position(userAnimalLatLng).title("Você está aqui"));
                        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userAnimalLatLng, 13));
                    }
                } else {
                    Toast.makeText(ADD_MissingPet_Page.this, "Não foi possível obter sua localização.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == FINE_PERMISSION_CODE) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(this, "O acesso a sua localização foi negado.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // ADICIONA A IMAGEM
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

        nomeAnimal = edtNomeAnimal.getText().toString();
        descAnimal = edtDescAnimal.getText().toString();

        if (ckbEnd.isChecked()) {
            cidadeAnimal = cidadeAnimal != null ? cidadeAnimal : "";
            bairroAnimal = bairroAnimal != null ? bairroAnimal : "";
            logradouroAnimal = logradouroAnimal != null ? logradouroAnimal : "";
            ufAnimal = ufAnimal != null ? ufAnimal : "";
        } else {
            cidadeAnimal = edtCidade.getText().toString();
            bairroAnimal = edtBairro.getText().toString();
            logradouroAnimal = edtLogradouro.getText().toString();
        }
        endConcat = logradouroAnimal + ", " + bairroAnimal + ", " + cidadeAnimal + ", " + ufAnimal;

        // PEGA O VALOR DO RADIO BUTTON
        radioGroup = findViewById(R.id.radioGroup);
        int i = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(i);

        radioButtonStr = radioButton.getText().toString();

        databaseReference = FirebaseDatabase.getInstance().getReference("animais").child(radioButtonStr); // PEGA A TABELA ANIMAL E A BRANCH SELECIONADA NO RADIO BUTTON
        animalId = databaseReference.push().getKey(); // CRIA UM ID ÚNICO PARA CADA ANIMAL
        userID = firebaseUser.getUid(); // PEGA O ID DO USUÁRIO

        // TRANSFORMA O ENDEREÇO PARA LATITUDE E LONGITUTE
        Geocoder geocoder = new Geocoder(ADD_MissingPet_Page.this);
        try {
            List<Address> addressList = geocoder.getFromLocationName(endConcat, 1);
            if(addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);
                double lat = address.getLatitude();
                double lng = address.getLongitude();

                // TRANSFORMA O DOUBLE EM STRING
                latAnimal = String.valueOf(lat);
                lngAnimal = String.valueOf(lng);
                // ADICIONA TUDO NO MODEL
                AnimalModel animalModel = new AnimalModel(animalId, imgAnimalStr, radioButtonStr, nomeAnimal, descAnimal, ufAnimal,
                                                        cidadeAnimal, bairroAnimal, logradouroAnimal, latAnimal, lngAnimal, userID);

                databaseReference.child(animalId).setValue(animalModel)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(ADD_MissingPet_Page.this, "Animal adicionado com sucesso!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ADD_MissingPet_Page.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show(); // TRATAMENTO DE ERRO
        }

    }

    public void reverseGeocoding() {
        Geocoder geocoder =  new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
            if (addressList.size() > 0) {
                if (addressList != null && addressList.size() > 0) {
                    Address address = addressList.get(0);

                    // Extraí as partes do endereço
                    logradouroAnimal = address.getAdminArea();
                    bairroAnimal = address.getSubLocality();
                    cidadeAnimal = address.getLocality();
                    ufAnimal = address.getAdminArea();
                    uploadFoto();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}