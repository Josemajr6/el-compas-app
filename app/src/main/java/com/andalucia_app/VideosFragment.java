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

        lista.add(new Video(
                "El Duende del Flamenco",
                "3:24 · Flamenco",
                "Un recorrido visual por los tablaos más emblemáticos de Sevilla y Jerez, "
                        + "donde el zapateao y el cante jondo se funden con la magia del sur.",
                R.drawable.videos,
                // TODO: sustituir por URL real o R.raw.video_flamenco
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
        ));

        lista.add(new Video(
                "Romería del Rocío",
                "5:10 · Tradiciones",
                "Las hermandades en procesión, el paso por las marismas del Guadalquivir y "
                        + "la emoción de llegar a la aldea. Una fiesta que une a toda Andalucía.",
                R.drawable.videos,
                // TODO: sustituir por URL real
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"
        ));

        lista.add(new Video(
                "Semana Santa en Málaga",
                "4:47 · Religión y Cultura",
                "Tronos, saetas y el olor a incienso de las noches malagueñas. "
                        + "Una Semana Santa única que mezcla devoción, arte y emoción popular.",
                R.drawable.videos,
                // TODO: sustituir por URL real
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4"
        ));

        lista.add(new Video(
                "Feria de Abril",
                "2:58 · Fiestas",
                "Trajes de flamenca, caballos, sevillanas y el ambiente inconfundible del Real "
                        + "de la Feria. La alegría del sur concentrada en una semana.",
                R.drawable.videos,
                // TODO: sustituir por URL real
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4"
        ));

        return lista;
    }
}