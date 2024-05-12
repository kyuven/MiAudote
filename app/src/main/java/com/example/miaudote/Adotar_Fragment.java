package com.example.miaudote;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Adotar_Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_adotar_, container, false);

        FloatingActionButton btnTeste = (FloatingActionButton) view.findViewById(R.id.btnTeste);
        AppCompatButton btnTeste02 = (AppCompatButton) view.findViewById(R.id.btnTeste02);


        btnTeste.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ADD_AdoptionPet_Page.class);
            startActivity(intent);
        });

        btnTeste02.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AdoptionPet_Info.class);
            startActivity(intent);
        });

        return view;
    }
}