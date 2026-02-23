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

    /**
     * ════════════════════════════════════════════════════════
     *  ¿CÓMO AÑADIR UN PERSONAJE NUEVO?
     *
     *  lista.add(new Personaje(
     *      "Nombre",
     *      "Año · Ciudad",
     *      "Descripción.",
     *      "Categoría",
     *      R.drawable.tu_foto,
     *      true    ← true  = rellena la cuadrícula (centerCrop)
     *              ← false = foto entera sin recorte (fitCenter)
     *  ));
     * ════════════════════════════════════════════════════════
     */
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
                "Gabriel 'Mellao'",
                "2000 · El Rubio (Sevilla)",
                "Desde chiquetillo mama me decia que dejara la juntiña. Pero?? Ando con Lua La L 3 chavo en los hoteles",
                "Pintura",
                R.drawable.foto_mellao,
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
                "Política",
                R.drawable.foto_juan,
                true
        ));

        lista.add(new Personaje(
                "Luis 'Comandante' Lara",
                "1976 · Jerez de la Frontera (Cádiz)",
                "¿Quién no conoce los show del Comandante Lara?",
                "Flamenco",
                R.drawable.foto_comandante,
                true
        ));

        lista.add(new Personaje(
                "Juan Manuel Cortés 'JC' Reyes",
                "1997 · Sevilla",
                "Sevillano de pro y figura indiscutible del grupo. Conocido por su carisma desbordante y por tener siempre la última palabra, JC es de esos que lo mismo te arregla un argumento filosófico que te monta una fiesta en cinco minutos.",
                "Crack Total",
                R.drawable.foto_jc,
                true
        ));

        lista.add(new Personaje(
                "Hermanos 'Midudan'",
                "1995 & 2003 · Sevilla",
                "Se les conoce por saber de todo. Fundadores del grupo 'Midudan'",
                "Crack Total",
                R.drawable.foto_hermanos_midudan,
                true
        ));

        return lista;
    }
}