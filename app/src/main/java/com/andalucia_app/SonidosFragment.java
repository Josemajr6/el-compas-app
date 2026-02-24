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
        lista.add(new Sonido(
                "Estamos a Martes",          // string: sonido_1_titulo
                "Gordo Master",            // string: sonido_1_artista
                "4:05",                     // string: sonido_1_duracion
                "Break",                 // string: sonido_1_categoria
                R.drawable.gordomaster,         // drawable: portada_1
                R.raw.estamosamartes             // raw: tu archivo .mp3
        ));

        // ── CANCIÓN 1 ──────────────────────────────────────────
        // TODO: Cambia R.raw.cancion_1 por tu fichero de audio
        //       y R.drawable.sonidos por la portada real
        lista.add(new Sonido(
                "Fardos Hardtech",          // string: sonido_1_titulo
                "Jc Reyes",            // string: sonido_1_artista
                "2:00",                     // string: sonido_1_duracion
                "HardTech",                 // string: sonido_1_categoria
                R.drawable.fardos_jc,         // drawable: portada_1
                R.raw.jc_reyes_fardos_hardtech_mashup             // raw: tu archivo .mp3
        ));

        // ── CANCIÓN 2 ──────────────────────────────────────────
        // TODO: Cambia R.raw.cancion_2 por tu fichero de audio
        lista.add(new Sonido(
                "El Patio x Ayer 2",              // string: sonido_2_titulo
                "Pepe y Vizio & Anuel AA", // string: sonido_2_artista
                "7:21",                     // string: sonido_2_duracion
                "Mashup",               // string: sonido_2_categoria
                R.drawable.pepeyvizio_anuel,         // drawable: portada_2
                R.raw.el_patio_x_ayer_2_pepe_y_vizio
        ));

        // ── CANCIÓN 2 ──────────────────────────────────────────
        lista.add(new Sonido(
                "Estilo Gitano - Angeliyo el Blanco",              // string: sonido_2_titulo
                "Angeliyo el Blanco", // string: sonido_2_artista
                "2:04",                     // string: sonido_2_duracion
                "Flamenco",               // string: sonido_2_categoria
                R.drawable.angeliyo_blanco,         // drawable: portada_2
                R.raw.estilogitano_angeliyoelblanco
        ));

        // ── CANCIÓN 3 ──────────────────────────────────────────
        // TODO: Cambia R.raw.cancion_3 por tu fichero de audio
        lista.add(new Sonido(
                "Diabla x Bandoleros",             // string: sonido_3_titulo
                "Los Diozes & Don Omar",              // string: sonido_3_artista
                "6:18",                     // string: sonido_3_duracion
                "Mashup",                    // string: sonido_3_categoria
                R.drawable.diabla_losdioze,         // drawable: portada_3
                R.raw.los_diozes_x_don_omar_diabla_x_bandoleros             // raw: tu archivo .mp3
        ));

        // ── CANCIÓN 4 ──────────────────────────────────────────
        // TODO: Cambia R.raw.cancion_4 por tu fichero de audio
        lista.add(new Sonido(
                "Maricarmen",                // string: sonido_4_titulo
                "La Pegatina",              // string: sonido_4_artista
                "2:21",                     // string: sonido_4_duracion
                "Tech",                    // string: sonido_4_categoria
                R.drawable.la_pegatina,         // drawable: portada_4
                R.raw.la_pegatina_maricarmen_tech             // raw: tu archivo .mp3
        ));

        // ── CANCIÓN 5 ──────────────────────────────────────────
        lista.add(new Sonido(
                "La Esperanza de María - Virgen de los Reyes",                // string: sonido_4_titulo
                "Virgen de los Reyes",              // string: sonido_4_artista
                "4:37",                     // string: sonido_4_duracion
                "Marcha Procesional",                    // string: sonido_4_categoria
                R.drawable.virgendelosreyes,         // drawable: portada_4
                R.raw.laesperanzademaria             // raw: tu archivo .mp3
        ));

        return lista;
    }
}