package com.example.miaudote;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class Perfil_Fragment extends Fragment {

    AppCompatButton btnMudarPerfil, btnTxtLogOut;
    AppCompatImageButton btnMudarSenha, btnTermos, btnSobreNos, btnIconLogOut;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        btnMudarPerfil = view.findViewById(R.id.btnPerfil_mudarPerfil);
        btnTxtLogOut = view.findViewById(R.id.btnPerfil_txtLogOut);

        btnMudarSenha = view.findViewById(R.id.btnPerfil_mudarSenha);
        btnTermos = view.findViewById(R.id.btnPerfil_termos);
        btnSobreNos = view.findViewById(R.id.btnPerfil_sobreNos);
        btnIconLogOut = view.findViewById(R.id.btnPerfil_iconLogOut);

        btnIconLogOut.setOnClickListener(v -> {
            // Método que mostra a caixa de diálogo para confirmar o log out
            // lógica de log out + firebase é dentro deste método
            // showDialogLogOut();
        });

        return view;
    }

}