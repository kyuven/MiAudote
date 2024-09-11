package com.example.miaudote.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.miaudote.Adapter.AdocaoAdapter;
import com.example.miaudote.Models.AnimalModel;
import com.example.miaudote.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Adotar_Fragment extends Fragment {

    // FIREBASE
    DatabaseReference databaseReference;

    // WIDGETS
    RecyclerView rvAnimal;

    List<AnimalModel> animalModelList;
    ValueEventListener eventListenerAnimal;

    public Adotar_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // INFLA O LAYOUT
        View view = inflater.inflate(R.layout.fragment_adotar_, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference("animais").child("Animal para adoção");

        rvAnimal = view.findViewById(R.id.rvAdotar);
        GridLayoutManager gridLayout = new GridLayoutManager(getActivity(), 2);
        rvAnimal.setLayoutManager(gridLayout);

        animalModelList = new ArrayList<>();
        AdocaoAdapter adocaoAdapter = new AdocaoAdapter(getActivity(), animalModelList);
        rvAnimal.setAdapter(adocaoAdapter);

        eventListenerAnimal = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                animalModelList.clear();
                for(DataSnapshot itemSnapshot: snapshot.getChildren()) {
                    AnimalModel animalModel = itemSnapshot.getValue(AnimalModel.class);
                    animalModelList.add(animalModel);
                }
                adocaoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}