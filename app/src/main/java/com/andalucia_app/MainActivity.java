package com.andalucia_app;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        // 1. Mantenemos el código que protege tus PNGs y el diseño de la barra
        bottomNav.setItemIconTintList(null);

        // 2. Cargamos el Fragment de Inicio por defecto al abrir la app
        if (savedInstanceState == null) {
            cargarFragment(new InicioFragment());
            bottomNav.setSelectedItemId(R.id.nav_inicio);
        }

        // 3. Escuchamos los clics en los botones de la barra
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_inicio) {
                cargarFragment(new InicioFragment());
                return true;
            } else if (itemId == R.id.nav_personajes) {
                cargarFragment(new PersonajesFragment());
                return true;
            } else if (itemId == R.id.nav_videos) {
                cargarFragment(new VideosFragment());
                return true;
            } else if (itemId == R.id.nav_sonidos) {
                cargarFragment(new SonidosFragment());
                return true;
            } else if (itemId == R.id.nav_animaciones) {
                cargarFragment(new AnimacionesFragment());
                return true;
            }
            return false;
        });
    }

    // Método auxiliar para no repetir código al cambiar de pantalla
    private void cargarFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Efecto de fundido al cambiar de pantalla (Toque premium)
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);

        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}