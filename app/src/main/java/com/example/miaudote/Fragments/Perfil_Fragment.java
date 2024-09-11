package com.example.miaudote.Fragments;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.miaudote.Models.UserModel;
import com.example.miaudote.PetInfo.UserAnimals;
import com.example.miaudote.UserData.Actv_EdtDados;
import com.example.miaudote.UserData.Actv_EdtEmail;
import com.example.miaudote.UserData.Actv_EdtSenha;
import com.example.miaudote.UserData.LoginIn_Activity;
import com.example.miaudote.R;
import com.example.miaudote.UserData.Termos_Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Perfil_Fragment extends Fragment {

    // FIREBASE
    private FirebaseAuth auth;
    FirebaseUser firebaseUser;

    // WIDGETS
    TextView txtDeleteAcc, txtPerfil_nomeUser, txtPerfil_emailUser;
    ImageView imgPerfil_user;
    LinearLayout linearlyt_dados, linearlyt_email, linearlyt_senha;
    AppCompatButton btnMudarPerfil, btnTxtLogOut, btnCancelLogout, btnConfirmLogout, btnDeleteUser;
    AppCompatImageButton btnTermos, btnMausTratos, btnIconLogOut, btn_backEdtDados, btnCancel,
                        btnNextDados, btnNextEmail, btnNextSenha, btnDelete, btnAnimaisUser;
    ConstraintLayout linearlyt_mausTratos, linearlyt_userAnimals;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        // FIREBASE
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        // WIDGETS
        txtPerfil_nomeUser = view.findViewById(R.id.txtPerfil_nomeUser);
        txtPerfil_emailUser = view.findViewById(R.id.txtPerfil_emailUser);
        imgPerfil_user = view.findViewById(R.id.imgPerfil_user);

        btnTermos = view.findViewById(R.id.btnPerfil_termos);
        btnMausTratos = view.findViewById(R.id.btnMausTratos);
        btnIconLogOut = view.findViewById(R.id.btnPerfil_iconLogOut);
        btnAnimaisUser = view.findViewById(R.id.btnAnimaisUser);

        linearlyt_userAnimals = view.findViewById(R.id.linearLytUserAnimais);
        linearlyt_mausTratos = view.findViewById(R.id.linearLyMausTratos);

        btnMudarPerfil = view.findViewById(R.id.btnPerfil_mudarPerfil);
        btnTxtLogOut = view.findViewById(R.id.btnPerfil_txtLogOut);

        if (firebaseUser != null){
            showUserPerfil();
        }

        // CHAMA A LÓGICA DE LOG OUT
        btnTxtLogOut.setOnClickListener(v -> {
            logOut();
        });
        btnIconLogOut.setOnClickListener(v -> {
            logOut();
        });

        btnTermos.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Termos_Activity.class);
            startActivity(intent);
        });

        // CHAMA O BANNER DE MAUS TRATOS AOS ANIMAIS
        linearlyt_mausTratos.setOnClickListener(v -> bannerMausTratos());
        btnMausTratos.setOnClickListener(v -> {
            bannerMausTratos();
        });

        // ENVIA PARA A PÁGINA DE ANIMAIS CADASTRADOS PELO USUÁRIO
        linearlyt_userAnimals.setOnClickListener(v -> {
            Intent i = new Intent(requireActivity(), UserAnimals.class);
            startActivity(i);
        });

        btnMudarPerfil.setOnClickListener(v -> {
            View alertCustomDialog = LayoutInflater.from(getActivity()).inflate(R.layout.change_profile_options, null);
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

            alertDialog.setView(alertCustomDialog);
            linearlyt_dados = alertCustomDialog.findViewById(R.id.linearlyt_dados);
            linearlyt_email = alertCustomDialog.findViewById(R.id.linearlyt_email);
            linearlyt_senha = alertCustomDialog.findViewById(R.id.linearlyt_senha);

            btn_backEdtDados = alertCustomDialog.findViewById(R.id.fab_backEdtDados);
            btnDeleteUser = alertCustomDialog.findViewById(R.id.btnDeleteUser);
            btnDelete = alertCustomDialog.findViewById(R.id.btnDeleteUserIcon);
            btnNextDados = alertCustomDialog.findViewById(R.id.btnNextDados);
            btnNextEmail = alertCustomDialog.findViewById(R.id.btnNextEmail);
            btnNextSenha = alertCustomDialog.findViewById(R.id.btnNextSenha);

            final AlertDialog dialog = alertDialog.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            dialog.show();

            btn_backEdtDados.setOnClickListener(v13 -> dialog.cancel());
            // IR PARA A PÁGINA DE ALTERAÇÃO DE DADOS DO USUÁRIO
            linearlyt_dados.setOnClickListener(v14 -> {
                Intent intent = new Intent(getActivity(), Actv_EdtDados.class);
                startActivity(intent);
                dialog.dismiss();
            });
            btnNextDados.setOnClickListener(v14 -> {
                Intent intent = new Intent(getActivity(), Actv_EdtDados.class);
                startActivity(intent);
                dialog.dismiss();
            });

            // IR PARA A PÁGINA DE ALTERAÇÃO DE SENHA
            linearlyt_email.setOnClickListener(v14 -> {
                Intent i = new Intent(getActivity(), Actv_EdtEmail.class);
                startActivity(i);
                dialog.dismiss();
            });
            btnNextEmail.setOnClickListener(v14 -> {
                Intent i = new Intent(getActivity(), Actv_EdtEmail.class);
                startActivity(i);
                dialog.dismiss();
            });

            // IR PARA A PÁGINA DE ALTERAÇÃO DE SENHA
            linearlyt_senha.setOnClickListener(v14 -> {
                Intent intenT = new Intent(getActivity(), Actv_EdtSenha.class);
                startActivity(intenT);
                dialog.dismiss();
            });
            btnNextSenha.setOnClickListener(v14 -> {
                Intent intenT = new Intent(getActivity(), Actv_EdtSenha.class);
                startActivity(intenT);
                dialog.dismiss();
            });

            // DELETAR CONTA
            btnDelete.setOnClickListener(v1 -> {
                dialog.dismiss();
                deleteAccount();
            });
            btnDeleteUser.setOnClickListener(v12 -> {
                dialog.dismiss();
                deleteAccount();
            });
        });

        return view;
    }

    private void bannerMausTratos() {
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

    public void deleteAccount() {
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
                if (user != null) {
                    String userId = user.getUid();
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

                    DatabaseReference userRef = databaseReference.child("usuarios").child(userId);
                    userRef.removeValue().addOnCompleteListener(userDeleteTask -> {
                        if (userDeleteTask.isSuccessful()) {
                            deleteAnimalsInBranch(databaseReference.child("animais").child("Animal encontrado"), userId);
                            deleteAnimalsInBranch(databaseReference.child("animais").child("Animal para adoção"), userId);
                            deleteAnimalsInBranch(databaseReference.child("animais").child("Animal perdido"), userId);
                        } else {
                            Log.d(TAG, "Erro ao deletar dados do usuário: " + userDeleteTask.getException().getMessage());
                        }
                    });
                }
            }
        });
    }

    private void deleteAnimalsInBranch(DatabaseReference branchRef, String userId) {
        branchRef.orderByChild("userId").equalTo(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            snapshot.getRef().removeValue(); // Remove cada animal
                        }

                        checkIfAllBranchesProcessed();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(TAG, "Erro ao buscar animais: " + databaseError.getMessage());
                    }
                });
    }

    private void checkIfAllBranchesProcessed() {

        FirebaseAuth.getInstance().getCurrentUser().delete().addOnCompleteListener(authDeleteTask -> {
            if (authDeleteTask.isSuccessful()) {
                Log.d(TAG, "Usuário e dados deletados com sucesso");
                Intent i = new Intent(getActivity(), LoginIn_Activity.class);
                startActivity(i);
            } else {
                Log.d(TAG, "Erro ao deletar usuário: " + authDeleteTask.getException().getMessage());
            }
        });
    }

}