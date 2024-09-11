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

public class MissingPet_Info extends AppCompatActivity implements OnMapReadyCallback {

    // FIREBASE
    FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    // GOOGLE MAP
    private GoogleMap myMap;

    // WIDGETS
    TextView txtNomeAnimal, txtDescAnimal, txtEmail, txtTelefone, txtEnd;
    ImageView imgAnimal;
    AppCompatImageButton btnBack;
    String userId, fotoAnimal, nomeAnimal, descAnimal, ufAnimal, cidadeAnimal, bairroAnimal, lograAnimal, latitude, longitute, concatEnd;
    double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missing_pet_info);

        // FIREBASE
        mAuth = FirebaseAuth.getInstance();

        // PEGA O FRAGMENT E SETA O MAPA NO FRAGMENTMAP
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapMissingPet);
        mapFragment.getMapAsync(this);

        // WIDGETS REFERENCIAS
        txtNomeAnimal = findViewById(R.id.txtMissingInfo_nomeAnimal);
        txtDescAnimal = findViewById(R.id.txtMissingInfo_desc);
        txtEmail = findViewById(R.id.txtMissingInfo_email);
        txtTelefone = findViewById(R.id.txtMissingInfo_telefone);
        imgAnimal = findViewById(R.id.imgMissingInfo_fotoAnimal);
        txtEnd = findViewById(R.id.txtMissingInfo_end);
        btnBack = findViewById(R.id.btnMissing_infoAnimal);
        btnBack.setOnClickListener(v -> finish());

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            // PEGA AS INFORMAÇÕES DO CARD/FIREBASE E SETA NOS TEXTVIEW
            Picasso.get().load(bundle.getString("imgAnimalM")).resize(130, 130).into(imgAnimal);

            txtNomeAnimal.setText(bundle.getString("nomeAnimalM"));
            txtDescAnimal.setText(bundle.getString("descAnimalM"));
            ufAnimal = bundle.getString("ufAnimalM");
            cidadeAnimal = bundle.getString("cidadeAnimalM");
            bairroAnimal = bundle.getString("bairroAnimalM");
            lograAnimal = bundle.getString("lograAnimalM");
            concatEnd = ufAnimal + ", " + cidadeAnimal + ", " + lograAnimal + ", " + bairroAnimal;
            txtEnd.setText(concatEnd);
            latitude = bundle.getString("latAnimalM");
            longitute = bundle.getString("lngAnimalM");
            lat = Double.parseDouble(latitude);
            lng = Double.parseDouble(longitute);

            // PEGA O ID DO USUÁRIO E AS INFORMAÇÕES E SETA NO EMAIL E TELEFONE
            userId = bundle.getString("userIdAnimalM");
            databaseReference = FirebaseDatabase.getInstance().getReference("usuarios").child(userId);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String email = snapshot.child("email").getValue().toString();
                    String telefone = snapshot.child("telefone").getValue(String.class);
                    txtEmail.setText(email);
                    txtTelefone.setText(telefone);
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