package com.example.miaudote;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ADD_AdoptionPet_Page extends AppCompatActivity {

    TextInputEditText edtNomeAnimal, edtIdadeAnimal, edtDescAnimal;
    String imgUrl;
    Uri uri;
    String[] itemsDropdown = {"Cachorro", "Gato", "Outros"};
    String[] itemsUF = {"AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"};
    ArrayAdapter<String> adapterItems, adapterUF, adapterCidade;
    AutoCompleteTextView autoCompleteDropdown, autoCompleteUf, autoCompleteCidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_adoption_pet_page);

        ImageView imgFotoAnimal = (ImageView) findViewById(R.id.imgAddAdpt_fotoAnimal);
        FloatingActionButton fabAddFoto = (FloatingActionButton) findViewById(R.id.fabAddAdpt_uploadImg);

        AppCompatButton btnSalvarAnimalAdocao = (AppCompatButton) findViewById(R.id.btnAddAdpt_salvar);
        AppCompatImageButton btnAddAdpt_back = (AppCompatImageButton) findViewById(R.id.btnAddAdpt_back);

        autoCompleteDropdown = findViewById(R.id.edtAddAdpt_especieAutoComplete);
        autoCompleteUf = findViewById(R.id.edtAddAdpt_ufAutoComplete);
        autoCompleteCidade = findViewById(R.id.edtAddAdpt_cidadeAutoComplete);

        edtNomeAnimal = findViewById(R.id.edtAddAdpt_nomeAnimal);
        edtIdadeAnimal = findViewById(R.id.edtAddAdpt_idade);
        edtDescAnimal = findViewById(R.id.edtAddAdpt_desc);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        if(o.getResultCode() == Activity.RESULT_OK) {
                            Intent data = o.getData();
                            uri = data.getData();
                            imgFotoAnimal.setImageURI(uri);
                        } else {
                            Toast.makeText(ADD_AdoptionPet_Page.this, "Deu errado", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        imgFotoAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item, itemsDropdown);
        autoCompleteDropdown.setAdapter(adapterItems);
        autoCompleteDropdown.setOnItemClickListener((parent, view, position, id) -> {

            String item = parent.getItemAtPosition(position).toString();

            // Lógica para salvar no banco de dados ao invés do toast
            Toast.makeText(getApplicationContext(), "Item:" + item, Toast.LENGTH_SHORT).show();
        });

        adapterUF = new ArrayAdapter<String>(this, R.layout.list_item, itemsUF);
        autoCompleteUf.setAdapter(adapterUF);
        autoCompleteUf.setOnItemClickListener((parent, view, position, id) -> {
            String item = parent.getItemAtPosition(position).toString();


            // Lógica para salvar no banco de dados ao invés do toast
            Toast.makeText(getApplicationContext(), "Item:" + item, Toast.LENGTH_SHORT).show();
        });

        btnSalvarAnimalAdocao.setOnClickListener(v -> {
            // Lógica para salvar no firebase os dados
            Intent intent = new Intent(ADD_AdoptionPet_Page.this, Main_Page.class);
            startActivity(intent);
            finish();
        });

        btnAddAdpt_back.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    public void saveData() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Android Images")
                .child(uri.getLastPathSegment());

        AlertDialog.Builder builder = new AlertDialog.Builder(ADD_AdoptionPet_Page.this);
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imgUrl = urlImage.toString();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }

    public void uploadData() {
        String nome = edtNomeAnimal.getText().toString();
        String desc = edtDescAnimal.getText().toString();

        DataClassAdopt dataClass = new DataClassAdopt(nome, desc, imgUrl);
        FirebaseDatabase.getInstance().setLogLevel(Logger.Level.DEBUG);
        FirebaseDatabase.getInstance().getReference("Teste").child(nome)
                .setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(ADD_AdoptionPet_Page.this, "Salvado", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ADD_AdoptionPet_Page.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}