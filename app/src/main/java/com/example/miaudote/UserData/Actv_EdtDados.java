package com.example.miaudote.UserData;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;

import com.example.miaudote.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Actv_EdtDados extends AppCompatActivity {

    // FIREBASE
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    StorageReference storageReference;

    // WIDGETS
    AppCompatButton btnUploadFoto, btnAtualizarDados;
    TextInputEditText edtNovoNome;
    Uri uriImage;
    private static final int PICK_IMAGE_REQUEST = 1;
    String nomeUser;
    ImageView fotoPerfil;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actv_edt_dados);

        // FIREBASE
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("usuarios");
        storageReference = FirebaseStorage.getInstance().getReference("imagens usuarios");

        // WIDGETS
        edtNovoNome = findViewById(R.id.edt_novoNome);
        fotoPerfil = findViewById(R.id.edt_fotoPerfil);

        btnUploadFoto = findViewById(R.id.btnAttFotoPerfil);
        btnAtualizarDados = findViewById(R.id.btnAttDadosUser);

        progressBar = findViewById(R.id.progressBarEdtUser);
        progressBar.setVisibility(View.INVISIBLE);

        // VOLTA PRA PÁGINA ANTERIOR
        AppCompatImageButton btnBack = findViewById(R.id.btnEdtUser_back);
        btnBack.setOnClickListener(v -> {
            finish();
        });

        // ACIONA O PHOTOPICKER PRA SELECIONAR A FOTO
        btnUploadFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent();
                photoPicker.setType("image/*");
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(photoPicker, PICK_IMAGE_REQUEST);
            }
        });

        // UPDATE DADOS (FOTOS DE PERFIL E NOME)
        btnAtualizarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // VERIFICA SE A IMAGEM É NULA OU O CAMPO DE TEXTO
                if (edtNovoNome.getText() != null || uriImage != null) {
                    updateData();
                } else {
                    saveData();
                }
            }
        });
    }

    // PEGA A IMAGEM SELECIONADA PELO USUÁRIO E COLOCA NO IMAGEVIEW DA TELA
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
            && data != null && data.getData() != null){
            uriImage = data.getData();
            Picasso.get().load(uriImage).resize(140, 140).into(fotoPerfil);
        }
    }

    public void updateData(){

        // SE O USUÁRIO TIVER SELECIONADO UMA IMAGEM
        if(uriImage != null) {


            StorageReference fileReference = storageReference.child(mAuth.getCurrentUser().getUid() + "." + getFileExtension(uriImage));
            fileReference.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            nomeUser = edtNovoNome.getText().toString().trim();
                            if (!nomeUser.isEmpty()) { // SE O USUÁRIO TIVER DIGITADO O NOME

                                // MUDA A IMAGEM NO BANCO DE DADOS
                                Uri downloadUri = uri;
                                firebaseUser = mAuth.getCurrentUser();
                                UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                        .setPhotoUri(downloadUri).build();
                                firebaseUser.updateProfile(profileChangeRequest);

                                // PEGA O ID DO USUÁRIO PARA MUDAR O NOME
                                String userID = firebaseUser.getUid();
                                reference.child(userID).child("nome").setValue(nomeUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            progressBar.setVisibility(View.VISIBLE);
                                            Toast.makeText(Actv_EdtDados.this, "Nome atualizado com sucesso", Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Toast.makeText(Actv_EdtDados.this, "Falha ao atualizar nome", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                // MUDA A IMAGEM NO BANCO DE DADOS
                                progressBar.setVisibility(View.VISIBLE);
                                Uri downloadUri = uri;
                                firebaseUser = mAuth.getCurrentUser();
                                UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                        .setPhotoUri(downloadUri).build();
                                firebaseUser.updateProfile(profileChangeRequest);
                            }
                        }
                    });
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Actv_EdtDados.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else { // SE O USUÁRIO NÃO TIVER SELECIONADO UMA IMAGEM
            saveData();
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    // ATUALIZA SOMENTE O NOME SE A IMAGEM FOR VAZIA
    public void saveData() {
        nomeUser = edtNovoNome.getText().toString().trim();
        if (!nomeUser.isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);
            String userID = firebaseUser.getUid();
            reference.child(userID).child("nome").setValue(nomeUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Actv_EdtDados.this, "Nome atualizado com sucesso", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(Actv_EdtDados.this, "Falha ao atualizar nome", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}