package com.example.miaudote.Fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.miaudote.UserData.LoginIn_Activity;
import com.example.miaudote.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Main_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_bar);

        bottomNavigationView.setOnItemSelectedListener((NavigationBarView.OnItemSelectedListener) item -> {

            int itemId = item.getItemId();

            if(itemId == R.id.navAdote) {
                // chama o método e substitui a variável fragment pelo fragment correspondente
                loadFragment(new Adotar_Fragment(), false);

            } else if (itemId == R.id.navAche) {
                // chama o método e substitui a variável fragment pelo fragment correspondente
                loadFragment(new Map_Animals_Fragment(), false);

            } else if(itemId == R.id.navOng) {
                // chama o método e substitui a variável fragment pelo fragment correspondente
                loadFragment(new ONG_Fragment(), false);

            } else if(itemId == R.id.navPerfil) {
                // chama o método e substitui a variável fragment pelo fragment correspondente
                loadFragment(new Perfil_Fragment(), false);
            }

            return true;
        });

        // Inicializa o app no Fragmento do mapa dos animais
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