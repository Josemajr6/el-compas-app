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

/**
 * ════════════════════════════════════════════════════════════════
 * MainActivity — Bottom Nav personalizada
 * ────────────────────────────────────────────────────────────────
 * Navegación completamente custom: 5 botones con:
 *   · Icono vectorial que cambia de tinte activo/inactivo
 *   · Punto indicador debajo del icono activo
 *   · Animación de rebote (OvershootInterpolator) al seleccionar
 *   · Botón central de Vídeos con fondo circular destacado
 *
 * Para añadir una nueva sección:
 *   1. Añade el botón en activity_main.xml
 *   2. Referencia sus Views en initNavViews()
 *   3. Añade el case en setNavSelected()
 * ════════════════════════════════════════════════════════════════
 */
public class MainActivity extends AppCompatActivity {

    // ── Referencias a iconos ──────────────────────────────────────
    private ImageView iconInicio, iconPersonajes, iconVideos, iconSonidos, iconAnimaciones;

    // ── Referencias a puntos indicadores ─────────────────────────
    private View dotInicio, dotPersonajes, dotVideos, dotSonidos, dotAnimaciones;

    // ── Referencias a los botones (para animación scale) ─────────
    private LinearLayout btnInicio, btnPersonajes, btnSonidos, btnAnimaciones;
    private LinearLayout btnVideos; // es un LinearLayout también

    // ── Colores de tinte ─────────────────────────────────────────
    private int colorActivo;
    private int colorInactivo;

    // ── Ítem seleccionado actualmente ────────────────────────────
    private int selectedItem = 0; // 0=inicio, 1=personajes, 2=videos, 3=sonidos, 4=anim

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colorActivo   = ContextCompat.getColor(this, R.color.text_dark_brown);
        colorInactivo = ContextCompat.getColor(this, R.color.sage_green);

        initNavViews();
        setupClickListeners();

        // Estado inicial: Inicio
        if (savedInstanceState == null) {
            setNavSelected(0);
            cargarFragment(new InicioFragment());
        }
    }

    // ─────────────────────────────────────────────────────────────
    //  Inicialización de views
    // ─────────────────────────────────────────────────────────────

    private void initNavViews() {
        // Botones
        btnInicio      = findViewById(R.id.nav_btn_inicio);
        btnPersonajes  = findViewById(R.id.nav_btn_personajes);
        btnVideos      = findViewById(R.id.nav_btn_videos);
        btnSonidos     = findViewById(R.id.nav_btn_sonidos);
        btnAnimaciones = findViewById(R.id.nav_btn_animaciones);

        // Iconos
        iconInicio      = findViewById(R.id.nav_icon_inicio);
        iconPersonajes  = findViewById(R.id.nav_icon_personajes);
        iconVideos      = findViewById(R.id.nav_icon_videos);
        iconSonidos     = findViewById(R.id.nav_icon_sonidos);
        iconAnimaciones = findViewById(R.id.nav_icon_animaciones);

        // Puntos
        dotInicio      = findViewById(R.id.nav_dot_inicio);
        dotPersonajes  = findViewById(R.id.nav_dot_personajes);
        dotVideos      = findViewById(R.id.nav_dot_videos);
        dotSonidos     = findViewById(R.id.nav_dot_sonidos);
        dotAnimaciones = findViewById(R.id.nav_dot_animaciones);
    }

    // ─────────────────────────────────────────────────────────────
    //  Click listeners
    // ─────────────────────────────────────────────────────────────

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

    // ─────────────────────────────────────────────────────────────
    //  Estado visual del nav
    // ─────────────────────────────────────────────────────────────

    /**
     * Actualiza tintes e indicadores según el ítem seleccionado.
     * @param index 0=inicio · 1=personajes · 2=videos · 3=sonidos · 4=animaciones
     */
    private void setNavSelected(int index) {
        selectedItem = index;

        // Restablecer todos a inactivo
        resetAllNav();

        // Activar el seleccionado
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
                // El ícono de vídeos va en blanco porque el fondo es sage_green
                iconVideos.setColorFilter(ContextCompat.getColor(this, R.color.background_light));
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
        // Tinte inactivo en todos
        iconInicio.setColorFilter(colorInactivo);
        iconPersonajes.setColorFilter(colorInactivo);
        // Vídeos siempre en blanco (fondo circular sage_green)
        iconVideos.setColorFilter(ContextCompat.getColor(this, R.color.background_light));
        iconSonidos.setColorFilter(colorInactivo);
        iconAnimaciones.setColorFilter(colorInactivo);

        // Ocultar todos los puntos
        dotInicio.setVisibility(View.INVISIBLE);
        dotPersonajes.setVisibility(View.INVISIBLE);
        dotVideos.setVisibility(View.INVISIBLE);
        dotSonidos.setVisibility(View.INVISIBLE);
        dotAnimaciones.setVisibility(View.INVISIBLE);
    }

    // ─────────────────────────────────────────────────────────────
    //  Animación de rebote al seleccionar un ítem
    // ─────────────────────────────────────────────────────────────

    private void animateBounce(View target) {
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

    // ─────────────────────────────────────────────────────────────
    //  Carga de fragmentos
    // ─────────────────────────────────────────────────────────────

    private void cargarFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }
}