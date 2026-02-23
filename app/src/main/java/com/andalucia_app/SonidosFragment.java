package com.andalucia_app;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.andalucia_app.adapter.SonidoAdapter;
import com.andalucia_app.entity.Sonido;
import java.util.ArrayList;
import java.util.List;

/**
 * ════════════════════════════════════════════════════════════════
 * SonidosFragment — "Sonidos de Andalucía"
 * ────────────────────────────────────────────────────────────────
 * Muestra una lista de canciones reproducibles con MediaPlayer.
 * Cada tarjeta tiene: portada, título, artista, duración,
 * barra de progreso interactiva y controles Play/Pausa/Stop.
 *
 * ¿CÓMO AÑADIR UNA CANCIÓN?
 *   1. Copia el fichero .mp3 en  app/src/main/res/raw/
 *   2. Añade en crearListaSonidos():
 *        new Sonido(
 *            "Título",           ← nombre de la canción
 *            "Artista",          ← nombre del artista / grupo
 *            "3:45",             ← duración (texto informativo)
 *            "Flamenco",         ← género / categoría
 *            R.drawable.sonidos, ← portada (imagen en drawable)
 *            R.raw.mi_cancion    ← recurso de audio en res/raw/
 *        )
 *
 * ¿CÓMO CAMBIAR UNA CANCIÓN EXISTENTE?
 *   Solo cambia R.raw.nombre_del_fichero por tu nuevo recurso.
 * ════════════════════════════════════════════════════════════════
 */
public class SonidosFragment extends Fragment {

    private SonidoAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sonidos, container, false);

        RecyclerView rv = view.findViewById(R.id.rv_sonidos);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new SonidoAdapter(crearListaSonidos());
        rv.setAdapter(adapter);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (adapter != null) adapter.releaseActivePlayer();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (adapter != null) adapter.releaseActivePlayer();
    }

    // ─────────────────────────────────────────────────────────────
    //  CATÁLOGO DE SONIDOS
    //  ▸ Modifica aquí para añadir, cambiar o quitar canciones.
    //  ▸ Las imágenes de portada deben estar en res/drawable/
    //  ▸ Los audios deben estar en res/raw/
    // ─────────────────────────────────────────────────────────────
    private List<Sonido> crearListaSonidos() {
        List<Sonido> lista = new ArrayList<>();

        // ── CANCIÓN 1 ──────────────────────────────────────────
        // TODO: Cambia R.raw.cancion_1 por tu fichero de audio
        //       y R.drawable.sonidos por la portada real
        lista.add(new Sonido(
                "Entre Dos Aguas",          // string: sonido_1_titulo
                "Paco de Lucía",            // string: sonido_1_artista
                "4:23",                     // string: sonido_1_duracion
                "Flamenco",                 // string: sonido_1_categoria
                R.drawable.sonidos,         // drawable: portada_1
                R.raw.cancion_1             // raw: tu archivo .mp3
        ));

        // ── CANCIÓN 2 ──────────────────────────────────────────
        // TODO: Cambia R.raw.cancion_2 por tu fichero de audio
        lista.add(new Sonido(
                "Ay Mi Perro",              // string: sonido_2_titulo
                "Los Romeros de la Puebla", // string: sonido_2_artista
                "3:10",                     // string: sonido_2_duracion
                "Sevillanas",               // string: sonido_2_categoria
                R.drawable.sonidos,         // drawable: portada_2
                R.raw.cancion_2             // raw: tu archivo .mp3
        ));

        // ── CANCIÓN 3 ──────────────────────────────────────────
        // TODO: Cambia R.raw.cancion_3 por tu fichero de audio
        lista.add(new Sonido(
                "La Zarzamora",             // string: sonido_3_titulo
                "Lola Flores",              // string: sonido_3_artista
                "2:58",                     // string: sonido_3_duracion
                "Copla",                    // string: sonido_3_categoria
                R.drawable.sonidos,         // drawable: portada_3
                R.raw.cancion_3             // raw: tu archivo .mp3
        ));

        // ── CANCIÓN 4 ──────────────────────────────────────────
        // TODO: Cambia R.raw.cancion_4 por tu fichero de audio
        lista.add(new Sonido(
                "A Tu Vera",                // string: sonido_4_titulo
                "Lola Flores",              // string: sonido_4_artista
                "3:33",                     // string: sonido_4_duracion
                "Rumba",                    // string: sonido_4_categoria
                R.drawable.sonidos,         // drawable: portada_4
                R.raw.cancion_4             // raw: tu archivo .mp3
        ));

        return lista;
    }
}