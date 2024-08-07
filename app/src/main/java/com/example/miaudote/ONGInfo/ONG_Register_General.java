package com.example.miaudote.ONGInfo;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ONG_Register_General extends AppCompatActivity {

    // FIREBASE
    FirebaseAuth mAuth;
    StorageReference storageReference;
    FirebaseUser firebaseUser;

    // WIDGETS
    TextInputEditText edtNomeOng, edtDescOng, edtCidadeOng, edtBairroOng, edtLograOng;
    String nomeOng, descOng, cidadeOng, bairroOng, logradouroOng;
    AppCompatImageButton btnCttOng, btnBack;
    ImageView imgOng;

    Uri uriImage;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ong_register_general);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        edtNomeOng = findViewById(R.id.edtNomeONG);
        edtDescOng = findViewById(R.id.edtDescONG);
        edtCidadeOng = findViewById(R.id.edtOngCidade);
        edtBairroOng = findViewById(R.id.edtOngBairro);
        edtLograOng = findViewById(R.id.edtOngLogradouro);
        imgOng = findViewById(R.id.addFotoOng);

        btnCttOng = findViewById(R.id.fab_nextCtt_confirmar);
        btnBack = findViewById(R.id.btnOngGeneralBack);
        AppCompatButton btnAddFotoOng = findViewById(R.id.btnAddFotoOng);

        btnAddFotoOng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escolherImage();
            }
        });

        btnCttOng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentDataOng();
            }
        });

        btnBack.setOnClickListener(v -> {
            finish();
        });

    }

    private void escolherImage() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void intentDataOng() {
        nomeOng = edtNomeOng.getText().toString();
        descOng = edtDescOng.getText().toString();
        cidadeOng = edtCidadeOng.getText().toString();
        bairroOng = edtBairroOng.getText().toString();
        logradouroOng = edtLograOng.getText().toString();

        Intent i = new Intent(ONG_Register_General.this, ONG_Register_Contact.class);
        i.putExtra("nomeOngE", nomeOng);
        i.putExtra("descOngE", descOng);
        i.putExtra("cidadeOngE", cidadeOng);
        i.putExtra("bairroOngE", bairroOng);
        i.putExtra("logradouroOngE", logradouroOng);
        startActivity(i);

    }
}