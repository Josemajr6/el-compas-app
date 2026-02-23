package com.andalucia_app;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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

        // ─────────────────────────────────────────────────────────────
        //  FIX CENTRADO NAVBAR
        //  BottomNavigationView añade internamente padding bottom para
        //  los system insets (barra de navegación del sistema) aunque
        //  en el XML le digamos paddingBottomSystemWindowInsets="false".
        //  La única forma fiable de anularlo es sobreescribirlo desde
        //  Java una vez que la vista ya está adjunta y los insets están
        //  resueltos. Ponemos paddingTop = paddingBottom = 0 para que
        //  el LinearLayout padre (gravity="center") haga el centrado.
        // ─────────────────────────────────────────────────────────────
        ViewCompat.setOnApplyWindowInsetsListener(bottomNav, (v, insets) -> {
            v.setPadding(
                    v.getPaddingLeft(),
                    0,   // top: sin padding extra
                    v.getPaddingRight(),
                    0    // bottom: elimina el inset automático
            );
            return insets;
        });

        // Sin tinte para que los PNGs se vean como son
        bottomNav.setItemIconTintList(null);

        // Fragment de inicio por defecto
        if (savedInstanceState == null) {
            cargarFragment(new InicioFragment());
            bottomNav.setSelectedItemId(R.id.nav_inicio);
        }

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if      (id == R.id.nav_inicio)      cargarFragment(new InicioFragment());
            else if (id == R.id.nav_personajes)  cargarFragment(new PersonajesFragment());
            else if (id == R.id.nav_videos)      cargarFragment(new VideosFragment());
            else if (id == R.id.nav_sonidos)     cargarFragment(new SonidosFragment());
            else if (id == R.id.nav_animaciones) cargarFragment(new AnimacionesFragment());
            else return false;
            return true;
        });
    }

    private void cargarFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }
}