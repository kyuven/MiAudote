package com.example.miaudote.PetInfo;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Actv_EdtDadosAnimal extends AppCompatActivity {

    // FIREBASE
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    // WIDGETS
    TextInputEditText edtNovoNomeAnimal, edtNovaDescAnimal, edtNovoLogradouro, edtNovoBairro, edtNovoCidade;
    String txtNomeAnimal, txtDescAnimal, txtLograAnimal, txtBairroAnimal, txtCidadeAnimal, animalId, newImgAnimalStr,
            txtNovoNomeAnimal, txtNovaDescAnimal, txtNovoLograAnimal, txtNovoBairroAnimal, txtNovaCidadeAnimal, ufAnimal;

    ArrayAdapter<String> adapterEditarUF;
    AutoCompleteTextView autoEditarCompleteUf;
    String[] itemsUFAnimal = {"AC", "AL", "AP", "AM", "BA", "CE", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB",
            "PR", "PE", "PI", "RJ", "RN", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO", "DF"};

    AppCompatImageButton btnBackAnimalEdtDados, btnDeleteAnimalIcon;
    AppCompatButton btnDeleteAnimalTxt, btnSaveData;
    FloatingActionButton fabAddFotoEdtAnimal;
    ImageView imgEdtAnimal;

    boolean found;
    Uri uriImageAnimal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actv_edt_dados_animal);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("animais");

        edtNovoNomeAnimal = findViewById(R.id.edtDadosAnimal_nomeAnimal);
        edtNovaDescAnimal = findViewById(R.id.edtDadosAnimal_desc);
        edtNovoLogradouro = findViewById(R.id.edtDadosAnimal_logradouro);
        edtNovoBairro = findViewById(R.id.edtDadosAnimal_bairro);
        edtNovoCidade = findViewById(R.id.edtDadosAnimal_cidade);
        imgEdtAnimal = findViewById(R.id.imgEdtDadosPet);
        
        btnDeleteAnimalIcon = findViewById(R.id.btnDeleteIconAnimal);
        btnDeleteAnimalTxt = findViewById(R.id.btnDeleteAnimal);
        btnSaveData = findViewById(R.id.btnDadosAnimal_salvar);

        autoEditarCompleteUf = findViewById(R.id.edtDadosAnimal_ufAutoComplete);
        adapterEditarUF = new ArrayAdapter<String>(this, R.layout.list_item, itemsUFAnimal);
        autoEditarCompleteUf.setAdapter(adapterEditarUF);

        autoEditarCompleteUf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ufAnimal = parent.getItemAtPosition(position).toString();
            }
        });

        fabAddFotoEdtAnimal = findViewById(R.id.fabDadosAnimal_photo);
        btnBackAnimalEdtDados = findViewById(R.id.btnEdtDadosAnimal_back);
        btnBackAnimalEdtDados.setOnClickListener(v -> finish());
        
        btnDeleteAnimalTxt.setOnClickListener(v -> deleteAnimal());
        btnDeleteAnimalIcon.setOnClickListener(v -> deleteAnimal());

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            Picasso.get().load(bundle.getString("Imagem Animal User")).resize(130, 130).into(imgEdtAnimal);
            txtNomeAnimal = bundle.getString("Nome Animal User");
            txtDescAnimal = bundle.getString("Descrição Animal User");
            txtLograAnimal = bundle.getString("Logradouro Animal User");
            txtBairroAnimal = bundle.getString("Bairro Animal User");
            txtCidadeAnimal = bundle.getString("Cidade Animal User");
            animalId = bundle.getString("Animal ID");

            edtNovoNomeAnimal.setText(txtNomeAnimal);
            edtNovaDescAnimal.setText(txtDescAnimal);
            edtNovoCidade.setText(txtCidadeAnimal);
            edtNovoLogradouro.setText(txtLograAnimal);
            edtNovoBairro.setText(txtBairroAnimal);
        }

        fabAddFotoEdtAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escolherFoto();
            }
        });

        btnSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uriImageAnimal != null) {
                    uploadFoto();
                } else {
                    editDataAnimal();
                }
            }
        });
    }

    // ADICIONA A IMAGEM
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if(o.getResultCode() == Activity.RESULT_OK) {
                        Intent data = o.getData();
                        uriImageAnimal = data.getData();
                        Picasso.get().load(uriImageAnimal).resize(130, 130).centerCrop().into(imgEdtAnimal);
                    } else {
                        Toast.makeText(Actv_EdtDadosAnimal.this, "Nenhuma imagem selecionada.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    public void escolherFoto() {
        Intent photoPicker = new Intent(Intent.ACTION_PICK);
        photoPicker.setType("image/*");
        activityResultLauncher.launch(photoPicker);
    }

    public void uploadFoto() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("imagens animais")
                .child(uriImageAnimal.getLastPathSegment());

        storageReference.putFile(uriImageAnimal).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                uriTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        newImgAnimalStr = uri.toString();
                        editDataAnimal();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Actv_EdtDadosAnimal.this, "Falha ao adicionar a imagem", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void editDataAnimal() {
        txtNovoNomeAnimal = edtNovoNomeAnimal.getText().toString();
        txtNovaDescAnimal = edtNovaDescAnimal.getText().toString();
        txtNovaCidadeAnimal = edtNovoCidade.getText().toString();
        txtNovoBairroAnimal = edtNovoBairro.getText().toString();
        txtNovoLograAnimal = edtNovoLogradouro.getText().toString();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                found = false;
                for (DataSnapshot branchSnapshot : dataSnapshot.getChildren()) {
                    String branchKey = branchSnapshot.getKey();
                    DatabaseReference branchRef = databaseReference.child(branchKey);

                    branchRef.child(animalId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot animalSnapshot) {
                            if (animalSnapshot.exists()) {
                                branchRef.child(animalId).child("nomeAnimal").setValue(txtNovoNomeAnimal);
                                branchRef.child(animalId).child("descAnimal").setValue(txtNovaDescAnimal);
                                branchRef.child(animalId).child("cidadeAnimal").setValue(txtNovaCidadeAnimal);
                                branchRef.child(animalId).child("bairroAnimal").setValue(txtNovoBairroAnimal);
                                branchRef.child(animalId).child("lograAnimal").setValue(txtNovoLograAnimal);
                                branchRef.child(animalId).child("ufAnimal").setValue(ufAnimal);

                                // Se uma nova imagem foi escolhida, atualiza também
                                if (newImgAnimalStr != null) {
                                    branchRef.child(animalId).child("imgAnimal").setValue(newImgAnimalStr);
                                }
                                finish();
                                found = true;
                            }

                            if (found) {
                                return;
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(Actv_EdtDadosAnimal.this, "Erro ao acessar o banco de dados", Toast.LENGTH_SHORT).show();
                        }
                    });
                    if (found) {
                        return;
                    }
                }
                if (!found) {
                    Toast.makeText(Actv_EdtDadosAnimal.this, "Animal não encontrado", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Actv_EdtDadosAnimal.this, "Erro ao acessar o banco de dados", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteAnimal() {

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                found = false;
                for (DataSnapshot branchSnapshot : dataSnapshot.getChildren()) {
                    String branchKey = branchSnapshot.getKey();
                    DatabaseReference branchRef = databaseReference.child(branchKey);

                    branchRef.child(animalId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot animalSnapshot) {
                            if (animalSnapshot.exists()) {
                                branchRef.child(animalId).removeValue().addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Actv_EdtDadosAnimal.this, "Animal excluído com sucesso", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Actv_EdtDadosAnimal.this, "Falha ao excluir o animal", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                found = true;
                            }

                            if (found) {
                                return;
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(Actv_EdtDadosAnimal.this, "Erro ao acessar o banco de dados", Toast.LENGTH_SHORT).show();
                        }
                    });
                    if (found) {
                        return;
                    }
                }
                if (!found) {
                    Toast.makeText(Actv_EdtDadosAnimal.this, "Animal não encontrado", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Actv_EdtDadosAnimal.this, "Erro ao acessar o banco de dados", Toast.LENGTH_SHORT).show();
            }
        });
    }
}