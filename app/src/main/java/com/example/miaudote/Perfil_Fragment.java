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
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class Perfil_Fragment extends Fragment {

    AppCompatButton btnMudarPerfil, btnTxtLogOut, btnCancelLogout, btnConfirmLogout;
    ImageButton btnBack;
    AppCompatImageButton btnTermos, btnSobreNos, btnIconLogOut, btnEdtDados, btnEdtEmail, btnEdtSenha;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        btnMudarPerfil = view.findViewById(R.id.btnPerfil_mudarPerfil);
        btnTxtLogOut = view.findViewById(R.id.btnPerfil_txtLogOut);

        btnTermos = view.findViewById(R.id.btnPerfil_termos);
        btnSobreNos = view.findViewById(R.id.btnPerfil_sobreNos);
        btnIconLogOut = view.findViewById(R.id.btnPerfil_iconLogOut);

        btnIconLogOut.setOnClickListener(v -> {
            View alertCustomDialog = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_custom, null); // passa para a variável o layout
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

            alertDialog.setView(alertCustomDialog); // Aciona, no fragment, o layout de logout
            // pega os botões da "caixa de dialogo"
            btnCancelLogout = alertCustomDialog.findViewById(R.id.btnDialogCustom_nao);
            btnConfirmLogout = alertCustomDialog.findViewById(R.id.btnDialogCustom_sim);

            // Agora, de fato, abre a caixa de diálogo, colocando o fundo como transparente (aparece a atividade)
            final AlertDialog dialog = alertDialog.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

            btnCancelLogout.setOnClickListener(v1 -> dialog.cancel()); // Fecha a caixa de diálogo caso o botão seja pressionado
            btnConfirmLogout.setOnClickListener(v12 -> {
                Intent intent = new Intent(getActivity(), LoginIn_Activity.class);
                startActivity(intent);
            });
        });

        btnMudarPerfil.setOnClickListener(v -> {
            View alrtDialog = LayoutInflater.from(getActivity()).inflate(R.layout.change_profile_options, null);
            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

            btnBack = alrtDialog.findViewById(R.id.fab_backEdtDados);
            btnEdtDados = alrtDialog.findViewById(R.id.btnEdtDados);
            btnEdtEmail = alrtDialog.findViewById(R.id.btnEdtEmail);
            btnEdtSenha = alrtDialog.findViewById(R.id.btnEdtSenha);

            alert.setView(alrtDialog);
            final  AlertDialog dialog = alert.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            dialog.show();

            btnBack.setOnClickListener(v13 -> dialog.cancel()); // Cancela a caixa de diálogo
            btnEdtDados.setOnClickListener(v14 -> startActivity(new Intent(getActivity(), Actv_EdtDados.class))); // Abre a atividade para editar dados
            btnEdtEmail.setOnClickListener(v15 -> startActivity(new Intent(getActivity(), Actv_EdtEmail.class))); // Abre a atividade para editar email
            btnEdtSenha.setOnClickListener(v16 -> startActivity(new Intent(getActivity(), Actv_EdtSenha.class))); // Abre a atividade para editar senha
        });

        return view;
    }
}