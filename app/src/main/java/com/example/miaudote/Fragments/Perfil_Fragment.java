package com.example.miaudote.Fragments;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miaudote.Models.UserModel;
import com.example.miaudote.UserData.Actv_EdtDados;
import com.example.miaudote.UserData.Actv_EdtEmail;
import com.example.miaudote.UserData.Actv_EdtSenha;
import com.example.miaudote.UserData.LoginIn_Activity;
import com.example.miaudote.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Perfil_Fragment extends Fragment {

    private FirebaseAuth auth;
    FirebaseUser firebaseUser;
    TextView txtDeleteAcc, txtPerfil_nomeUser, txtPerfil_emailUser;
    ImageView imgPerfil_user;
    LinearLayout linearlyt_mausTratos, linearlyt_dados, linearlyt_email, linearlyt_senha, linearlyt_delete;
    AppCompatButton btnMudarPerfil, btnTxtLogOut, btnCancelLogout, btnConfirmLogout;
    AppCompatImageButton btnTermos, btnMausTratos, btnIconLogOut, btn_backEdtDados, btnCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        txtPerfil_nomeUser = view.findViewById(R.id.txtPerfil_nomeUser);
        txtPerfil_emailUser = view.findViewById(R.id.txtPerfil_emailUser);
        imgPerfil_user = view.findViewById(R.id.imgPerfil_user);

        btnTermos = view.findViewById(R.id.btnPerfil_termos);
        btnMausTratos = view.findViewById(R.id.btnMausTratos);
        btnIconLogOut = view.findViewById(R.id.btnPerfil_iconLogOut);

        btnMudarPerfil = view.findViewById(R.id.btnPerfil_mudarPerfil);
        btnTxtLogOut = view.findViewById(R.id.btnPerfil_txtLogOut);

        if (firebaseUser == null){
            Toast.makeText(getActivity(), "Alguma coisa deu errado!", Toast.LENGTH_SHORT).show();
        } else {
            showUserPerfil();
        }

        btnTxtLogOut.setOnClickListener(v -> {
            logOut();
        });

        btnIconLogOut.setOnClickListener(v -> {
            logOut();
        });

        btnMausTratos.setOnClickListener(v -> {
            View alertCustomDialog = LayoutInflater.from(getActivity()).inflate(R.layout.pop_up, null);
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

            alertDialog.setView(alertCustomDialog);
            btnCancel = alertCustomDialog.findViewById(R.id.btnCloseAd);

            final AlertDialog dialog = alertDialog.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            dialog.show();
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
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
                Intent intent = new Intent(getActivity(), Actv_EdtDados.class);
                startActivity(intent);
                dialog.dismiss();
            });

            linearlyt_email.setOnClickListener(v14 -> {
                Intent i = new Intent(getActivity(), Actv_EdtEmail.class);
                startActivity(i);
                dialog.dismiss();
            });

            linearlyt_senha.setOnClickListener(v14 -> {
                Intent intenT = new Intent(getActivity(), Actv_EdtSenha.class);
                startActivity(intenT);
                dialog.dismiss();
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

    public void logOut() {
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
    }

    private void showUserPerfil() {

        String userID = firebaseUser.getUid();

        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("usuarios");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel model = snapshot.getValue(UserModel.class);
                if(model != null) {
                    txtPerfil_nomeUser.setText(model.getNome());
                    txtPerfil_emailUser.setText(model.getEmail());

                    Uri uri = firebaseUser.getPhotoUrl();
                    Picasso.get().load(uri).resize(130, 130).into(imgPerfil_user);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}