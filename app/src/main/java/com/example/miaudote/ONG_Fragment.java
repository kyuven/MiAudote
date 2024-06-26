package com.example.miaudote;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ONG_Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ong, container, false);

        // botão de teste
        FloatingActionButton btnAddOng = view.findViewById(R.id.fabAddOng);

        //botão everá ser substituido pelo do card
        btnAddOng.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ONG_Register_General.class);
            startActivity(intent);
        });

        return view;
    }
}