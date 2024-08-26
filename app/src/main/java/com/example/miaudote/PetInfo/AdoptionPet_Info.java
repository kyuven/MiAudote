package com.example.miaudote.PetInfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.miaudote.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class AdoptionPet_Info extends AppCompatActivity implements OnMapReadyCallback {

    //FIREBASE
    FirebaseAuth mAuth;
    DatabaseReference usuarioRef;

    // GOOGLE MAP
    private GoogleMap myMap;

    // WIDGETS
    TextView txtNomeAnimal, txtDescAnimal, txtEmailDono, txtTelefoneDono, txtEnd;
    AppCompatImageButton btnBackAnimalInfo;
    ImageView imgAnimalInfo;
    String ufAnimalStr, cidadeAnimalStr, bairroAnimalStr, lograAnimalStr, concatEndAnimal, userID, latAnimal, lngAnimal;
    double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adoption_pet_info);

        mAuth = FirebaseAuth.getInstance();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapAdpt);
        mapFragment.getMapAsync(this);

        btnBackAnimalInfo = findViewById(R.id.btnAdpt_infoAnimal);
        imgAnimalInfo = findViewById(R.id.imgAdptInfo_fotoAnimal);

        txtNomeAnimal = findViewById(R.id.txtAdptInfo_nomeAnimal);
        txtDescAnimal = findViewById(R.id.txtAdptInfo_desc);
        txtEmailDono = findViewById(R.id.txtAdptInfo_email);
        txtTelefoneDono = findViewById(R.id.txtAdptInfo_telefone);
        txtEnd = findViewById(R.id.txtAdptInfo_end);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            Picasso.get().load(bundle.getString("Imagem Animal Adoção")).resize(130, 130).into(imgAnimalInfo);

            txtNomeAnimal.setText(bundle.getString("Nome Animal Adoção"));
            txtDescAnimal.setText(bundle.getString("Descrição Animal Adoção"));
            ufAnimalStr = bundle.getString("UF Animal Adoção");
            cidadeAnimalStr = bundle.getString("Cidade Animal Adoção");
            bairroAnimalStr = bundle.getString("Bairro Animal Adoção");
            lograAnimalStr = bundle.getString("Logradouro Animal Adoção");
            concatEndAnimal = ufAnimalStr + ", " + cidadeAnimalStr + ", " + bairroAnimalStr + ", " + lograAnimalStr;
            txtEnd.setText(concatEndAnimal);
            latAnimal = bundle.getString("Latitude Animal Adoção");
            lngAnimal = bundle.getString("Longitude Animal Adoção");
            lat = Double.parseDouble(latAnimal);
            lng = Double.parseDouble(lngAnimal);

            userID = bundle.getString("User ID");
            usuarioRef = FirebaseDatabase.getInstance().getReference("usuarios").child(userID);
            usuarioRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String email = snapshot.child("email").getValue().toString();
                    txtEmailDono.setText(email);
                    // String telefone = usuarioSnapshot.child("telefone").getValue(String.class);
                }

               @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        btnBackAnimalInfo.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;
        LatLng endereco = new LatLng(lat, lng);
        myMap.addMarker(new MarkerOptions().position(endereco));
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(endereco, 16f));
        myMap.getUiSettings().setZoomControlsEnabled(true);
        myMap.getUiSettings().setScrollGesturesEnabled(true);
    }
}