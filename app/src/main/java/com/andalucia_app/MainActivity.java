package com.andalucia_app;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private ImageView iconInicio, iconPersonajes, iconVideos, iconSonidos, iconAnimaciones;

    private View dotInicio, dotPersonajes, dotVideos, dotSonidos, dotAnimaciones;

    private LinearLayout btnInicio, btnPersonajes, btnVideos, btnSonidos, btnAnimaciones;

    private int colorActivo;
    private int colorInactivo;
    private int colorVideosFondo;

    private int selectedItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colorActivo      = ContextCompat.getColor(this, R.color.yellow_sun);
        colorInactivo    = ContextCompat.getColor(this, R.color.background_light);
        colorVideosFondo = ContextCompat.getColor(this, R.color.background_light);

        initNavViews();
        setupClickListeners();

        if (savedInstanceState == null) {
            setNavSelected(0);
            cargarFragment(new InicioFragment());
        }
    }

    // inicializar las views
    private void initNavViews() {
        btnInicio      = findViewById(R.id.nav_btn_inicio);
        btnPersonajes  = findViewById(R.id.nav_btn_personajes);
        btnVideos      = findViewById(R.id.nav_btn_videos);
        btnSonidos     = findViewById(R.id.nav_btn_sonidos);
        btnAnimaciones = findViewById(R.id.nav_btn_animaciones);

        iconInicio      = findViewById(R.id.nav_icon_inicio);
        iconPersonajes  = findViewById(R.id.nav_icon_personajes);
        iconVideos      = findViewById(R.id.nav_icon_videos);
        iconSonidos     = findViewById(R.id.nav_icon_sonidos);
        iconAnimaciones = findViewById(R.id.nav_icon_animaciones);

        dotInicio      = findViewById(R.id.nav_dot_inicio);
        dotPersonajes  = findViewById(R.id.nav_dot_personajes);
        dotVideos      = findViewById(R.id.nav_dot_videos);
        dotSonidos     = findViewById(R.id.nav_dot_sonidos);
        dotAnimaciones = findViewById(R.id.nav_dot_animaciones);
    }

    private void setupClickListeners() {
        btnInicio.setOnClickListener(v -> {
            if (selectedItem != 0) {
                animateBounce(iconInicio);
                setNavSelected(0);
                cargarFragment(new InicioFragment());
            }
        });
        btnPersonajes.setOnClickListener(v -> {
            if (selectedItem != 1) {
                animateBounce(iconPersonajes);
                setNavSelected(1);
                cargarFragment(new PersonajesFragment());
            }
        });
        btnVideos.setOnClickListener(v -> {
            if (selectedItem != 2) {
                animateBounce(iconVideos);
                setNavSelected(2);
                cargarFragment(new VideosFragment());
            }
        });
        btnSonidos.setOnClickListener(v -> {
            if (selectedItem != 3) {
                animateBounce(iconSonidos);
                setNavSelected(3);
                cargarFragment(new SonidosFragment());
            }
        });
        btnAnimaciones.setOnClickListener(v -> {
            if (selectedItem != 4) {
                animateBounce(iconAnimaciones);
                setNavSelected(4);
                cargarFragment(new AnimacionesFragment());
            }
        });
    }

    public void selectNavItem(int index, Fragment fragment) {
        animateBounce(getIconForIndex(index));
        setNavSelected(index);
        cargarFragment(fragment);
    }

    private void setNavSelected(int index) {
        selectedItem = index;
        resetAllNav();

        switch (index) {
            case 0:
                iconInicio.setColorFilter(colorActivo);
                dotInicio.setVisibility(View.VISIBLE);
                break;
            case 1:
                iconPersonajes.setColorFilter(colorActivo);
                dotPersonajes.setVisibility(View.VISIBLE);
                break;
            case 2:
                iconVideos.setColorFilter(colorVideosFondo);
                dotVideos.setVisibility(View.VISIBLE);
                break;
            case 3:
                iconSonidos.setColorFilter(colorActivo);
                dotSonidos.setVisibility(View.VISIBLE);
                break;
            case 4:
                iconAnimaciones.setColorFilter(colorActivo);
                dotAnimaciones.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void resetAllNav() {
        iconInicio.setColorFilter(colorInactivo);
        iconPersonajes.setColorFilter(colorInactivo);
        iconVideos.setColorFilter(colorVideosFondo);
        iconSonidos.setColorFilter(colorInactivo);
        iconAnimaciones.setColorFilter(colorInactivo);

        dotInicio.setVisibility(View.INVISIBLE);
        dotPersonajes.setVisibility(View.INVISIBLE);
        dotVideos.setVisibility(View.INVISIBLE);
        dotSonidos.setVisibility(View.INVISIBLE);
        dotAnimaciones.setVisibility(View.INVISIBLE);
    }

    private ImageView getIconForIndex(int index) {
        switch (index) {
            case 1: return iconPersonajes;
            case 2: return iconVideos;
            case 3: return iconSonidos;
            case 4: return iconAnimaciones;
            default: return iconInicio;
        }
    }

    private void animateBounce(View target) {
        if (target == null) return;
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, "scaleX", 1f, 1.35f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, "scaleY", 1f, 1.35f, 1f);
        scaleX.setDuration(320);
        scaleY.setDuration(320);
        scaleX.setInterpolator(new OvershootInterpolator(2.5f));
        scaleY.setInterpolator(new OvershootInterpolator(2.5f));
        AnimatorSet set = new AnimatorSet();
        set.playTogether(scaleX, scaleY);
        set.start();
    }
    private void cargarFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }
}