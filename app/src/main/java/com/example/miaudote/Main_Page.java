package com.example.miaudote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

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