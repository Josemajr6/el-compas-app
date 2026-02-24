package com.andalucia_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andalucia_app.adapter.VideoAdapter;
import com.andalucia_app.entity.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * ════════════════════════════════════════════════════════════════
 * VideosFragment — "Andalucía en Vídeo"
 * ────────────────────────────────────────────────────────────────
 * Muestra una lista de vídeos reproducibles con ExoPlayer (Media3).
 * Cada tarjeta tiene: thumbnail, botón Play, controles nativos,
 * título, duración/categoría y descripción.
 *
 * ¿CÓMO AÑADIR UN VÍDEO?
 *   Puedes usar URLs públicas (mp4, m3u8) o recursos locales:
 *     "android.resource://com.andalucia_app/" + R.raw.mi_video
 *
 * ¿CÓMO AÑADIR UN VÍDEO LOCAL?
 *   1. Copia el fichero .mp4 en  app/src/main/res/raw/
 *   2. En crearListaVideos() añade:
 *        new Video("Título", "2:30 · Flamenco", "Descripción",
 *            R.drawable.mi_thumbnail,
 *            "android.resource://com.andalucia_app/" + R.raw.mi_video)
 * ════════════════════════════════════════════════════════════════
 */
public class VideosFragment extends Fragment {

    private VideoAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.framgent_videos, container, false);

        RecyclerView rv = view.findViewById(R.id.rv_videos);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new VideoAdapter(crearListaVideos());
        rv.setAdapter(adapter);

        return view;
    }

    /**
     * Al salir del fragmento liberamos el reproductor activo
     * para evitar fugas de memoria y que el audio siga sonando.
     */
    @Override
    public void onPause() {
        super.onPause();
        if (adapter != null) {
            adapter.releaseActivePlayer();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (adapter != null) {
            adapter.releaseActivePlayer();
        }
    }

    // ─────────────────────────────────────────────────────────────
    //  Catálogo de vídeos
    //
    //  Los tres primeros usan vídeos de muestra públicos (BigBuckBunny
    //  y Elephant Dream de Blender Foundation, dominio público).
    //  Sustitúyelos por tus propias URLs o recursos raw.
    // ─────────────────────────────────────────────────────────────
    private List<Video> crearListaVideos() {
        List<Video> lista = new ArrayList<>();

        // Vídeo 1
        lista.add(new Video(
                "Patica Quemándose",
                "00:37 · Categoría Gemelos",
                "Top 6 quemadas del patica comiendo.",
                R.drawable.videos,
                "android.resource://com.andalucia_app/" + R.raw.video_patica
        ));
        // Vídeo 2
        lista.add(new Video(
                "Awana wana king kong",
                "1:14 · Categoría Pureza",
                "¿Hay algo más andaluz que ese video?",
                R.drawable.videos,
                "android.resource://com.andalucia_app/" + R.raw.video_awanakingkong
        ));

        // Vídeo 3
        lista.add(new Video(
                "Bellotas",
                "00:59 · Categoría Viejos",
                "Yo eh venio a por bellotas. A robar.",
                R.drawable.videos,
                "android.resource://com.andalucia_app/" + R.raw.video_bellotas
        ));

        // Vídeo 4
        lista.add(new Video(
                "Puestada",
                "00:34 · Categoría DROGADOS",
                "Un yonki esta drogado en su coche.",
                R.drawable.videos,
                "android.resource://com.andalucia_app/" + R.raw.video_puestada
        ));

        return lista;
    }
}