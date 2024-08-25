package com.example.miaudote.PetInfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class MissingPet_Info extends AppCompatActivity {

    // FIREBASE
    FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    // GOOGLE MAP
    private GoogleMap myMap;

    // WIDGETS
    TextView txtNomeAnimal, txtDescAnimal, txtEmail, txtTelefone, txtEnd;
    ImageView imgAnimal;
    AppCompatImageButton btnBack;
    String userID, latitude, longitude;
    double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missing_pet_info);

        mAuth = FirebaseAuth.getInstance();

        //SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapMissingPet);
        //mapFragment.getMapAsync(this);

        txtNomeAnimal = findViewById(R.id.txtMissingInfo_nomeAnimal);
        txtDescAnimal = findViewById(R.id.txtMissingInfo_desc);
        txtEmail = findViewById(R.id.txtMissingInfo_email);
        txtTelefone = findViewById(R.id.txtMissingInfo_telefone);
        imgAnimal = findViewById(R.id.imgMissingInfo_fotoAnimal);
        txtEnd = findViewById(R.id.txtMissingInfo_end);

        btnBack.setOnClickListener(v -> finish());

        Intent intent = getIntent();
        String animalId = intent.getStringExtra("animalId");

        if (animalId != null) {
            Log.d("MissingPet_Info", "Received Animal ID: " + animalId);
        } else {
            Log.e("MissingPet_Info", "Animal ID is null");
        }

    }

    private void loadAnimalData(String animalId) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String nomeAnimal = snapshot.child("nomeAnimal").getValue(String.class);
                    String descAnimal = snapshot.child("descAnimal").getValue(String.class);
                    String latAnimal = snapshot.child("latAnimal").getValue(String.class);
                    String lngAnimal = snapshot.child("lngAnimal").getValue(String.class);

                    lat = Double.parseDouble(latAnimal);
                    lng = Double.parseDouble(lngAnimal);
                    txtNomeAnimal.setText(nomeAnimal);
                    txtDescAnimal.setText(descAnimal);
                } else {
                    Toast.makeText(MissingPet_Info.this, "No data found for the selected animal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MissingPet_Info.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //@Override
    //public void onMapReady(@NonNull GoogleMap googleMap) {
        //myMap = googleMap;
        //LatLng endereco = new LatLng(lat, lng);
        //myMap.addMarker(new MarkerOptions().position(endereco));
        //myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(endereco, 16f));
        //myMap.getUiSettings().setZoomControlsEnabled(true);
        //myMap.getUiSettings().setScrollGesturesEnabled(true);
    //}
}