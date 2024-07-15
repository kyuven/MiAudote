package com.example.miaudote.PetInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.miaudote.R;

public class MissingPet_Info extends AppCompatActivity {

    TextView txtNomeAnimal, txtEspecieAnimal, txtIdadeAnimal, txtDescAnimal, txtEmail, txtTelefone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missing_pet_info);
    }

    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}