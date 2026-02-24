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

    private List<Video> crearListaVideos() {
        List<Video> lista = new ArrayList<>();

        // Vídeo 1
        lista.add(new Video(
                "Iuuuuuuu",
                "02:13 · Categoría Viejos",
                "Este video si que deberia de estar en los libros de historia",
                R.drawable.videos,
                "android.resource://com.andalucia_app/" + R.raw.video_iuuu
        ));

        // Vídeo 2
        lista.add(new Video(
                "Pim Pam",
                "00:39 · Categoría España",
                "Que viva la orden y la ley",
                R.drawable.videos,
                "android.resource://com.andalucia_app/" + R.raw.video_pimpam
        ));

        // Vídeo 3
        lista.add(new Video(
                "Patica Quemándose",
                "00:37 · Categoría Gemelos",
                "Top 6 quemadas del patica comiendo.",
                R.drawable.videos,
                "android.resource://com.andalucia_app/" + R.raw.video_patica
        ));
        // Vídeo 4
        lista.add(new Video(
                "Awana wana king kong",
                "1:14 · Categoría Pureza",
                "¿Hay algo más andaluz que ese video?",
                R.drawable.videos,
                "android.resource://com.andalucia_app/" + R.raw.video_awanakingkong
        ));

        // Vídeo 5
        lista.add(new Video(
                "Bellotas",
                "00:59 · Categoría Viejos",
                "Yo eh venio a por bellotas. A robar.",
                R.drawable.videos,
                "android.resource://com.andalucia_app/" + R.raw.video_bellotas
        ));

        // Vídeo 6
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