package com.example.miaudote.PetInfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.os.Bundle;
import android.view.View;
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

public class MissingPet_Info extends AppCompatActivity implements OnMapReadyCallback {

    // FIREBASE
    FirebaseAuth mAuth;
    DatabaseReference usuarioRef;

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

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapMissingPet);
        mapFragment.getMapAsync(this);

        txtNomeAnimal = findViewById(R.id.txtMissingInfo_nomeAnimal);
        txtDescAnimal = findViewById(R.id.txtMissingInfo_desc);
        txtEmail = findViewById(R.id.txtMissingInfo_email);
        txtTelefone = findViewById(R.id.txtMissingInfo_telefone);
        imgAnimal = findViewById(R.id.imgMissingInfo_fotoAnimal);
        txtEnd = findViewById(R.id.txtMissingInfo_end);

        btnBack.setOnClickListener(v -> finish());

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Picasso.get().load(bundle.getString("imgAnimalE")).resize(130, 130).into(imgAnimal);
            txtNomeAnimal.setText(bundle.getString("nomeAnimalE"));
            txtDescAnimal.setText(bundle.getString("descAnimalE"));
            txtEnd.setText(bundle.getString("enderecoE"));
            latitude = bundle.getString("latitude");
            longitude = bundle.getString("longitute");

            lat = Double.parseDouble(latitude);
            lng = Double.parseDouble(longitude);

            userID = bundle.getString("User ID");
            usuarioRef = FirebaseDatabase.getInstance().getReference("usuarios").child(userID);
            usuarioRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String email = snapshot.child("email").getValue().toString();
                    txtEmail.setText(email);
                    // String telefone = usuarioSnapshot.child("telefone").getValue(String.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
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