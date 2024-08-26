package com.example.miaudote.ONGInfo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;

import com.example.miaudote.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ONG_Register_General extends AppCompatActivity {

    // FIREBASE
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;

    // WIDGETS
    TextInputEditText edtNomeOng, edtDescOng, edtCidadeOng, edtBairroOng, edtLograOng;
    String nomeOng, descOng, ufOng, cidadeOng, bairroOng, logradouroOng, imgOngStr;
    AppCompatImageButton btnCttOng, btnBack;
    ImageView imgOng;

        // WIDGETS DROPDOWN MENU
    String[] itensUf = {"AC", "AL", "AP", "AM", "BA", "CE", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB",
                        "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO", "DF"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItens;

    Uri uriImage;

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

        autoCompleteTextView = findViewById(R.id.dadosOng_ufAutoComplete);
        adapterItens = new ArrayAdapter<String>(this, R.layout.list_item, itensUf);
        autoCompleteTextView.setAdapter(adapterItens);

        btnCttOng = findViewById(R.id.fab_nextCtt_confirmar);
        btnBack = findViewById(R.id.btnOngGeneralBack);
        AppCompatButton btnAddFotoOng = findViewById(R.id.btnAddFotoOng);

        btnCttOng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataOng();
            }
        });

        btnBack.setOnClickListener(v -> {
            finish();
        });

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ufOng = parent.getItemAtPosition(position).toString();
            }
        });

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        if(o.getResultCode() == Activity.RESULT_OK) {
                            Intent data = o.getData();
                            uriImage = data.getData();
                            Picasso.get().load(uriImage).resize(48, 48).centerCrop().into(imgOng);
                        } else {
                            Toast.makeText(ONG_Register_General.this, "Nenhuma imagem selecionada.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        btnAddFotoOng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

    }

    private void saveDataOng() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("imagens ong")
                .child(uriImage.getLastPathSegment());

        storageReference.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imgOngStr = urlImage.toString();
                intentDataOng();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ONG_Register_General.this, "Falha ao adicionar a imagem", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void intentDataOng() {

        nomeOng = edtNomeOng.getText().toString();
        descOng = edtDescOng.getText().toString();
        cidadeOng = edtCidadeOng.getText().toString();
        bairroOng = edtBairroOng.getText().toString();
        logradouroOng = edtLograOng.getText().toString();

        Intent i = new Intent(ONG_Register_General.this, ONG_Register_Contact.class);
        i.putExtra("imgOngE", imgOngStr);
        i.putExtra("nomeOngE", nomeOng);
        i.putExtra("descOngE", descOng);
        i.putExtra("ufOngE", ufOng);
        i.putExtra("cidadeOngE", cidadeOng);
        i.putExtra("bairroOngE", bairroOng);
        i.putExtra("logradouroOngE", logradouroOng);
        startActivity(i);

    }
}