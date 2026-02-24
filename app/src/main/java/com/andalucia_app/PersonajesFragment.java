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
import com.andalucia_app.adapter.PersonajeAdapter;
import com.andalucia_app.entity.Personaje;
import java.util.ArrayList;
import java.util.List;

public class PersonajesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_personajes, container, false);

        RecyclerView rv = view.findViewById(R.id.rv_personajes);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        rv.setAdapter(new PersonajeAdapter(crearListaPersonajes()));

        return view;
    }

    // método con la lista de personajes
    private List<Personaje> crearListaPersonajes() {
        List<Personaje> lista = new ArrayList<>();

        lista.add(new Personaje(
                "Juan de la Palmilla",
                "1978 · La Palmilla (Málaga)",
                "Figura icónica de Málaga. Conocido por meter la mano en la comia y la gente que lo conoce por no pagarle lo que vale el piso.",
                "Artista",
                R.drawable.foto_juanpalmilla,
                true
        ));

        lista.add(new Personaje(
                "'Faliyo' de San Roque",
                "2000 · San Roque (Cádiz)",
                "Esta persona es uno de los mayores personajes de cádiz y autor de 'Caramelo'",
                "Personaje",
                R.drawable.foto_faliyo,
                true
        ));

        lista.add(new Personaje(
                "José Ángel 'Patica'",
                "1996 · Cacín (Granada)",
                "Con un poco pan y su freeway fesquita siempre operativa alegrando a la gente.",
                "Historia",
                R.drawable.foto_patica,
                true
        ));

        lista.add(new Personaje(
                "Juan Alberto 'LMDShow'",
                "1994 · Fuengirola (Málaga)",
                "Se le conoce por trabajar tela tela.",
                "Streamer",
                R.drawable.foto_juan,
                true
        ));

        lista.add(new Personaje(
                "Luis 'Comandante' Lara",
                "1976 · Jerez de la Frontera (Cádiz)",
                "¿Quién no conoce los show del Comandante Lara?",
                "Comediante",
                R.drawable.foto_comandante,
                true
        ));

        lista.add(new Personaje(
                "Juan Manuel Cortés 'JC' Reyes",
                "1997 · Sevilla",
                "Cantante, compositor y rapero español, nacido en Sevilla, reconocido por su" +
                        " estilo distintivo que fusiona trap, flamenco y reguetón.",
                "Cantante",
                R.drawable.foto_jc,
                true
        ));

        lista.add(new Personaje(
                "Hermanos 'Midudan'",
                "1995 & 2003 · Sevilla",
                "Se les conoce por saber de todo. Fundadores del grupo 'Midudan'",
                "Descubridores / Geólogos",
                R.drawable.foto_hermanos_midudan,
                true
        ));

        return lista;
    }
}