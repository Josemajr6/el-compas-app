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


    private List<Sonido> crearListaSonidos() {
        List<Sonido> lista = new ArrayList<>();

        // ── CANCIÓN 1 ──────────────────────────────────────────
        lista.add(new Sonido(
                "Estamos a Martes",
                "Gordo Master",
                "4:05",
                "Break",
                R.drawable.gordomaster,
                R.raw.estamosamartes
        ));

        // ── CANCIÓN 2 ──────────────────────────────────────────
        lista.add(new Sonido(
                "Fardos Hardtech",
                "Jc Reyes",
                "2:00",
                "HardTech",
                R.drawable.fardos_jc,
                R.raw.jc_reyes_fardos_hardtech_mashup
        ));

        // ── CANCIÓN 3 ──────────────────────────────────────────
        lista.add(new Sonido(
                "El Patio x Ayer 2",
                "Pepe y Vizio & Anuel AA",
                "7:21",
                "Mashup",
                R.drawable.pepeyvizio_anuel,
                R.raw.el_patio_x_ayer_2_pepe_y_vizio
        ));

        // ── CANCIÓN 4 ──────────────────────────────────────────
        lista.add(new Sonido(
                "Estilo Gitano",
                "Angeliyo el Blanco",
                "2:04",
                "Flamenco",
                R.drawable.angeliyo_blanco,
                R.raw.estilogitano_angeliyoelblanco
        ));

        // ── CANCIÓN 5 ──────────────────────────────────────────
        lista.add(new Sonido(
                "Diabla x Bandoleros",
                "Los Diozes & Don Omar",
                "6:18",
                "Mashup",
                R.drawable.diabla_losdioze,
                R.raw.los_diozes_x_don_omar_diabla_x_bandoleros
        ));

        // ── CANCIÓN 6 ──────────────────────────────────────────
        lista.add(new Sonido(
                "Maricarmen",
                "La Pegatina",
                "2:21",
                "Tech",
                R.drawable.la_pegatina,
                R.raw.la_pegatina_maricarmen_tech
        ));

        // ── CANCIÓN 7 ──────────────────────────────────────────
        lista.add(new Sonido(
                "La Esperanza de María",
                "Virgen de los Reyes",
                "4:37",
                "Marcha Procesional",
                R.drawable.virgendelosreyes,
                R.raw.laesperanzademaria
        ));

        return lista;
    }
}