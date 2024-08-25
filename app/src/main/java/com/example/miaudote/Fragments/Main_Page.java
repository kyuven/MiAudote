package com.example.miaudote.Fragments;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.miaudote.UserData.LoginIn_Activity;
import com.example.miaudote.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Main_Page extends AppCompatActivity {

    AppCompatImageButton btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_bar);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(sharedPreferences.getBoolean("IS_FIRST_TIME", true)) {
            popUp();
            sharedPreferences.edit().putBoolean("IS_FIRST_TIME", false).apply();
        }

        bottomNavigationView.setOnItemSelectedListener((NavigationBarView.OnItemSelectedListener) item -> {
            int itemId = item.getItemId();

            if(itemId == R.id.navAdote) {
                loadFragment(new Adotar_Fragment(), false);
            } else if (itemId == R.id.navAche) {
                loadFragment(new Map_Animals_Fragment(), false);
            } else if(itemId == R.id.navOng) {
                loadFragment(new ONG_Fragment(), false);
            } else if(itemId == R.id.navPerfil) {
                loadFragment(new Perfil_Fragment(), false);
            }

            return true;
        });
        loadFragment(new Map_Animals_Fragment(), true);
    }

    protected void onStart(){
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null){
            Intent intent = new Intent(Main_Page.this, LoginIn_Activity.class);
            startActivity(intent);
            finish();
        }

    }

    private void popUp() {
        View alertCustomDialog = LayoutInflater.from(Main_Page.this).inflate(R.layout.pop_up, null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Main_Page.this);

        alertDialog.setView(alertCustomDialog);
        btnClose = alertCustomDialog.findViewById(R.id.btnCloseAd);

        final AlertDialog dialog = alertDialog.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        btnClose.setOnClickListener(v1 -> dialog.cancel());
    }

    // Método criado para diminuir código entre os if, pois será a mesma função a todos os itens
    // Cria um objeto do tipo Fragment e referenciamos a variável quando chamamos o método dentro do if
    // O boolean indica se o app recém foi aberto: caso sim, abrirá o fragmento que tiver boolean como true (quando abri o app somente)
        // caso não, ele somente terá como trocar entre os fragments
    private void loadFragment (Fragment fragment, boolean isAppInitialize) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(isAppInitialize) {
            fragmentTransaction.add(R.id.frameLayout, fragment);
        } else {
            fragmentTransaction.replace(R.id.frameLayout, fragment);
        }
        fragmentTransaction.commit();

    }
}