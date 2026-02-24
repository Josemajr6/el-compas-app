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
import com.andalucia_app.adapter.AnimacionAdapter;
import com.andalucia_app.entity.Animacion;
import java.util.ArrayList;
import java.util.List;


public class AnimacionesFragment extends Fragment {

    private AnimacionAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_animaciones, container, false);

        RecyclerView rv = view.findViewById(R.id.rv_animaciones);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new AnimacionAdapter(crearListaAnimaciones());
        rv.setAdapter(adapter);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (adapter != null) adapter.pauseAll();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (adapter != null) adapter.pauseAll();
    }

    // Lista de las 5 animaciones
    private List<Animacion> crearListaAnimaciones() {
        List<Animacion> lista = new ArrayList<>();

        lista.add(new Animacion(
                "Beso Apasionado",
                "Beso de hermanos.",
                "Amor",
                R.raw.beso_apasionado
        ));


        lista.add(new Animacion(
                "Ecentia",
                "Ecentia Manda.",
                "Poder",
                R.raw.ecentia_splash
        ));

        lista.add(new Animacion(
                "Tripaloski",
                "Video mítico. El fundador es de Andalucía",
                "Pureza",
                R.raw.tripaloski
        ));

        lista.add(new Animacion(
                "Salchipapa",
                "¿Qué decir de este video?",
                "Baile",
                R.raw.salchipapa
        ));

        lista.add(new Animacion(
                "Danza Dorada",
                "Danza que realizo mi tío abuelo ganador de un premio",
                "Baile",
                R.raw.epstein_dancing
        ));


        return lista;
    }
}