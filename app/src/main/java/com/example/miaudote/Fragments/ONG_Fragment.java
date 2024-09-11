package com.example.miaudote.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.miaudote.Adapter.OngAdapter;
import com.example.miaudote.Models.OngModel;
import com.example.miaudote.ONGInfo.ONG_Register_General;
import com.example.miaudote.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ONG_Fragment extends Fragment {

    // FIREBASE
    DatabaseReference databaseReference;

    // WIDGETS
    RecyclerView rvOng;

    List<OngModel> ongModelList;
    ValueEventListener eventListener;

    public ONG_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ong, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference("ong aprovadas");

        rvOng = view.findViewById(R.id.rvOng);
        GridLayoutManager gridLayout = new GridLayoutManager(getActivity(), 2);
        rvOng.setLayoutManager(gridLayout);

        // BOTÃO QUE DIRECIONA PARA A PÁGINA DE CADASTRO DE ONGs
        FloatingActionButton btnAddOng = view.findViewById(R.id.fabAddOng);
        btnAddOng.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ONG_Register_General.class);
            startActivity(intent);
        });

        ongModelList = new ArrayList<>();
        OngAdapter ongAdapter = new OngAdapter(getActivity(), ongModelList);
        rvOng.setAdapter(ongAdapter);

        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ongModelList.clear();
                for(DataSnapshot itemSnapshot: snapshot.getChildren()) {
                    OngModel ongModel = itemSnapshot.getValue(OngModel.class);
                    ongModelList.add(ongModel);
                }
                ongAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}