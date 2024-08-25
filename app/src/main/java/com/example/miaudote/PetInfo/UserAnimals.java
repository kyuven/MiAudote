package com.example.miaudote.PetInfo;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miaudote.Adapter.AdocaoAdapter;
import com.example.miaudote.Adapter.UserAnimalsAdapter;
import com.example.miaudote.Models.AnimalModel;
import com.example.miaudote.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserAnimals extends AppCompatActivity {

    // FIREBASE
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    // WIDGETS
    RecyclerView rvAnimalUser;
    List<AnimalModel> animalUserList;
    ValueEventListener eventListenerAnimal;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_animals);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("animais");
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        rvAnimalUser = findViewById(R.id.rvAnimaisUser);
        GridLayoutManager gridLayout = new GridLayoutManager(this, 2);
        rvAnimalUser.setLayoutManager(gridLayout);

        animalUserList = new ArrayList<>();
        UserAnimalsAdapter userAnimalAdapter = new UserAnimalsAdapter(this, animalUserList);
        rvAnimalUser.setAdapter(userAnimalAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                animalUserList.clear();
                for (DataSnapshot animalTypeSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot animalSnapshot : animalTypeSnapshot.getChildren()) {
                        AnimalModel animal = animalSnapshot.getValue(AnimalModel.class);
                        if (animal != null && userId.equals(animal.getUserId())) {
                            animalUserList.add(animal);
                        }
                    }
                }
                userAnimalAdapter.notifyDataSetChanged(); // Notificar o adapter sobre a atualização
            } @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}