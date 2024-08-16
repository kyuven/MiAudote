package com.example.miaudote.UserData;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.miaudote.Fragments.Perfil_Fragment;
import com.example.miaudote.Models.UserModel;
import com.example.miaudote.ONGInfo.ONG_Register_Contact;
import com.example.miaudote.ONGInfo.ONG_Register_General;
import com.example.miaudote.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Actv_EdtDados extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    StorageReference storageReference;
    AppCompatButton btnUploadFoto, btnAtualizarDados;
    TextInputEditText edtNovoNome;
    Uri uriImage;
    String imgUserStr, nomeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actv_edt_dados);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("usuarios");
        // storageReference = FirebaseStorage.getInstance().getReference("Imagens Usuarios");

        edtNovoNome = findViewById(R.id.edt_novoNome);
        ImageView fotoPerfil = findViewById(R.id.edt_fotoPerfil);

        btnUploadFoto = findViewById(R.id.btnAttFotoPerfil);
        btnAtualizarDados = findViewById(R.id.btnAttDadosUser);

        AppCompatImageButton btnBack = findViewById(R.id.btnEdtUser_back);
        btnBack.setOnClickListener(v -> {
            Intent i = new Intent(Actv_EdtDados.this, Perfil_Fragment.class);
            startActivity(i);
            finish();
        });

        // Update foto de perfil do usuário
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        if(o.getResultCode() == Activity.RESULT_OK) {
                            Intent data = o.getData();
                            uriImage = data.getData();
                            fotoPerfil.setImageURI(uriImage);
                        } else {
                            Toast.makeText(Actv_EdtDados.this, "Nenhuma imagem selecionada.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        btnUploadFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        // Update dados (foto de perfil e nome)
        btnAtualizarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData(firebaseUser);
            }
        });
    }



    private void updateData(FirebaseUser firebaseUser){
        nomeUser = edtNovoNome.getText().toString().trim();
        String userID = firebaseUser.getUid();
        reference.child(userID).child("nome").setValue(nomeUser);
        finish();
        // BOTAR PROGRESS BAR
    }
}