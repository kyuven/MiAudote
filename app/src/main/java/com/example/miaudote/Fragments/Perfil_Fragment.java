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
            View alertCustomDialog = LayoutInflater.from(getActivity()).inflate(R.layout.change_profile_options, null);
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

            alertDialog.setView(alertCustomDialog);
            linearlyt_dados = alertCustomDialog.findViewById(R.id.linearlyt_dados);
            linearlyt_email = alertCustomDialog.findViewById(R.id.linearlyt_email);
            linearlyt_senha = alertCustomDialog.findViewById(R.id.linearlyt_senha);
            linearlyt_delete = alertCustomDialog.findViewById(R.id.linearlyt_delete);
            btn_backEdtDados = alertCustomDialog.findViewById(R.id.fab_backEdtDados);

            final AlertDialog dialog = alertDialog.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            dialog.show();

            btn_backEdtDados.setOnClickListener(v13 -> dialog.cancel());
            linearlyt_dados.setOnClickListener(v14 -> {

                // CRIAR MÉTODO COM PARAMETRO DA ACTIVITY
                // TALVEZ MUDAR PRA SWITCH ?

                Intent i = new Intent(getActivity(), Actv_EdtDados.class);
                startActivity(i);
            });

            linearlyt_email.setOnClickListener(v14 -> {
                Intent i = new Intent(getActivity(), Actv_EdtEmail.class);
                startActivity(i);
            });

            linearlyt_senha.setOnClickListener(v14 -> {
                Intent i = new Intent(getActivity(), Actv_EdtSenha.class);
                startActivity(i);
            });

            linearlyt_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    View alertCustomDialog = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_custom, null);
                    AlertDialog.Builder deletarUser = new AlertDialog.Builder(getActivity());

                    deletarUser.setView(alertCustomDialog);
                    txtDeleteAcc = alertCustomDialog.findViewById(R.id.txtDialogCustom_sair);
                    btnCancelLogout = alertCustomDialog.findViewById(R.id.btnDialogCustom_nao);
                    btnConfirmLogout = alertCustomDialog.findViewById(R.id.btnDialogCustom_sim);

                    final AlertDialog dialogDel = deletarUser.create();
                    dialogDel.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                    dialogDel.show();

                    txtDeleteAcc.setText("DELETAR SUA CONTA?");
                    btnCancelLogout.setOnClickListener(v15 -> dialogDel.cancel());
                    btnConfirmLogout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "Usuário deleta com sucesso");
                                        Intent i = new Intent(getActivity(), LoginIn_Activity.class);
                                        startActivity(i);
                                    }
                                }
                            });
                        }
                    });
                }
            });
        });

        return view;
    }
}