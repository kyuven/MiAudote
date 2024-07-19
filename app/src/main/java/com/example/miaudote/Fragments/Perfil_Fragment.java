package com.example.miaudote.Fragments;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.miaudote.UserData.Actv_EdtDados;
import com.example.miaudote.UserData.Actv_EdtEmail;
import com.example.miaudote.UserData.Actv_EdtSenha;
import com.example.miaudote.UserData.LoginIn_Activity;
import com.example.miaudote.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Perfil_Fragment extends Fragment {

    private FirebaseAuth auth;
    TextView txtDeleteAcc;
    LinearLayout linearlyt_dados, linearlyt_email, linearlyt_senha, linearlyt_delete;
    AppCompatButton btnMudarPerfil, btnTxtLogOut, btnCancelLogout, btnConfirmLogout;
    AppCompatImageButton btnTermos, btnSobreNos, btnIconLogOut, btn_backEdtDados;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        auth = FirebaseAuth.getInstance();

        btnMudarPerfil = view.findViewById(R.id.btnPerfil_mudarPerfil);
        btnTxtLogOut = view.findViewById(R.id.btnPerfil_txtLogOut);

        btnTermos = view.findViewById(R.id.btnPerfil_termos);
        btnSobreNos = view.findViewById(R.id.btnPerfil_sobreNos);
        btnIconLogOut = view.findViewById(R.id.btnPerfil_iconLogOut);

        btnTxtLogOut.setOnClickListener(v -> {

            // CRIAR UM MÉTODO?
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
                auth.signOut();
                Intent intent = new Intent(getActivity(), LoginIn_Activity.class);
                startActivity(intent);
            });
        });

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
                auth.signOut();
                Intent intent = new Intent(getActivity(), LoginIn_Activity.class);
                startActivity(intent);
            });
        });

        btnMudarPerfil.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), Actv_EdtEmail.class);
            startActivity(i);
        });

        return view;
    }
}