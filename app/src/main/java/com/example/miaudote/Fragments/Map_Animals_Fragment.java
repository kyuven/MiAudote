package com.example.miaudote.Fragments;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.miaudote.Models.AnimalModel;
import com.example.miaudote.PetInfo.ADD_MissingPet_Page;
import com.example.miaudote.PetInfo.AdoptionPet_Info;
import com.example.miaudote.PetInfo.MissingPet_Info;
import com.example.miaudote.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Map_Animals_Fragment extends Fragment implements OnMapReadyCallback {

    // DATABASE
    DatabaseReference databaseReference;

    // WIDGETS
    FloatingActionButton fabAddAnimalPerdido;
    double latitude, longitude;
    ChildEventListener mChildEventListener;
    String userID, nomeAnimal, descAnimal, fotoAnimal, cidadeAnimal, lograAnimal, bairroAnimal, ufAnimal, animalId, lat, lng;

    // LOCALIZAÇÃO
    private final int FINE_PERMISSION_CODE = 1;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    GoogleMap mMap;
    Marker myMarker;
    LatLng posicao, userLatLng;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map__animals, container, false);

        fabAddAnimalPerdido = view.findViewById(R.id.fabAddAnimalPerdido);
        fabAddAnimalPerdido.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ADD_MissingPet_Page.class);
            startActivity(intent);
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        getLastLocation();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("animais");
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);

            return;
        }
        com.google.android.gms.tasks.Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null) {
                    currentLocation = location;
                    SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapPage);
                    if (mapFragment != null) {
                        mapFragment.getMapAsync(Map_Animals_Fragment.this);
                    }
                }
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        if (currentLocation != null) {
            userLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 14));
        } else {
            Log.e("Map_Animals_Fragment", "Current location is null");
        }
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                Intent i = new Intent(getContext(), MissingPet_Info.class);
                i.putExtra("userIdAnimalM", userID);
                i.putExtra("imgAnimalM", fotoAnimal);
                i.putExtra("nomeAnimalM", nomeAnimal);
                i.putExtra("descAnimalM", descAnimal);
                i.putExtra("ufAnimalM", ufAnimal);
                i.putExtra("cidadeAnimalM", cidadeAnimal);
                i.putExtra("lograAnimalM", lograAnimal);
                i.putExtra("bairroAnimalM", bairroAnimal);
                i.putExtra("latAnimalM", lat);
                i.putExtra("lngAnimalM", lng);
                startActivity(i);
                return true;
            }
        });
        addMarkersToMap();
    }

    public void addMarkersToMap() {
        Map<String, String> mMarkerMap = new HashMap<>();
       databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot itemSnapshot: snapshot.getChildren()) {
                    String branch = itemSnapshot.getKey();
                    for (DataSnapshot animalSnapshot : itemSnapshot.getChildren()) {
                        try {
                            userID = animalSnapshot.child("userId").getValue(String.class);
                            fotoAnimal = animalSnapshot.child("imgAnimal").getValue(String.class);
                            nomeAnimal = animalSnapshot.child("nomeAnimal").getValue(String.class);
                            descAnimal = animalSnapshot.child("descAnimal").getValue(String.class);
                            ufAnimal = animalSnapshot.child("ufAnimal").getValue(String.class);
                            cidadeAnimal = animalSnapshot.child("cidadeAnimal").getValue(String.class);
                            lograAnimal = animalSnapshot.child("lograAnimal").getValue(String.class);
                            bairroAnimal = animalSnapshot.child("bairroAnimal").getValue(String.class);
                            lat = animalSnapshot.child("latAnimal").getValue(String.class);
                            lng = animalSnapshot.child("lngAnimal").getValue(String.class);

                            latitude = Double.parseDouble(lat);
                            longitude = Double.parseDouble(lng);
                            posicao = new LatLng(latitude, longitude);

                            float color = getBranchColor(branch);

                            myMarker = mMap.addMarker(new MarkerOptions()
                                    .position(posicao)
                                    .icon(BitmapDescriptorFactory.defaultMarker(color)));

                        } catch (Exception e) {
                            Log.e("Map_Animals_Fragment", "Error adding marker", e);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private float getBranchColor(String branch) {
        switch (branch) {
            case "Animal para adoção":
                return BitmapDescriptorFactory.HUE_RED;
            case "Animal perdido":
                return BitmapDescriptorFactory.HUE_GREEN;
            case "Animal encontrado":
                return BitmapDescriptorFactory.HUE_BLUE;
            default:
                return BitmapDescriptorFactory.HUE_ORANGE;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == FINE_PERMISSION_CODE) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(getActivity(), "O acesso a sua localização foi negado.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}