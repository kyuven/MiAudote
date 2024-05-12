package com.example.miaudote;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ONG_Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ong, container, false);

        // botão de teste
        AppCompatButton btnTesteOng = (AppCompatButton) view.findViewById(R.id.btnTesteOng);

        //botão everá ser substituido pelo do card
        btnTesteOng.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ONG_Page.class);
            startActivity(intent);
        });

        return view;
    }
}