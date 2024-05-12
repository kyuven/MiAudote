package com.example.miaudote;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Map_Animals_Fragment extends Fragment {

    FloatingActionButton fabAddAnimalPerdido, fabVerAnimalInfo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map__animals, container, false);

        fabAddAnimalPerdido = view.findViewById(R.id.fabAddAnimalPerdido);
        fabVerAnimalInfo = view.findViewById(R.id.btnTesteInfo);

        fabAddAnimalPerdido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ADD_MissingPet_Page.class);
                startActivity(intent);
            }
        });

        fabVerAnimalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MissingPet_Info.class);
                startActivity(intent);
            }
        });

        return view;
    }

    // Criar método para economizar código de Intent
}