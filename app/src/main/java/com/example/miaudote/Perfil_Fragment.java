package com.example.miaudote;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class Perfil_Fragment extends Fragment {

    AppCompatButton btnMudarPerfil, btnTxtLogOut, btnCancelLogout, btnConfirmLogout;
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
            View alertCustomDialog = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_custom, null);
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

            alertDialog.setView(alertCustomDialog);
            btnCancelLogout = alertCustomDialog.findViewById(R.id.btnDialogCustom_nao);
            btnConfirmLogout = alertCustomDialog.findViewById(R.id.btnDialogCustom_sim);

            final AlertDialog dialog = alertDialog.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            btnCancelLogout.setOnClickListener(v1 -> dialog.cancel());
            btnConfirmLogout.setOnClickListener(v12 -> {
                Intent intent = new Intent(getActivity(), LoginIn_Activity.class);
                startActivity(intent);
            });
        });

        btnMudarPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Change_Perfil.class);
            startActivity(intent);
        });

        return view;
    }
}