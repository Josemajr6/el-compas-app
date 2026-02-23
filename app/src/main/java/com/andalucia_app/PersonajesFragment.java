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

    private List<Personaje> crearListaPersonajes() {
        List<Personaje> lista = new ArrayList<>();

        lista.add(new Personaje(
                "Juan de la Palmilla",
                "1978 · La Palmilla (Málaga)",
                "Figura icónica de Málaga. Conocido por meter la mano en la comia y la gente que lo conoce por no pagarle lo que vale el piso.",
                "Artista",
                R.drawable.foto_juanpalmilla
        ));

        lista.add(new Personaje(
                "Gabriel 'Mellao'",
                "2000 · El Rubio (Sevilla)",
                "Desde chiquetillo mama me decia que dejara la juntiña. Pero?? Ando con Lua La L 3 chavo en los hoteles",
                "Pintura",
                R.drawable.foto_mellao
        ));

        lista.add(new Personaje(
                "José Ángel 'Patica'",
                "1996 · Cacín (Granada)",
                "Con un poco pan y su freeway fesquita siempre operativa alegrando a la gente.",
                "Historia",
                R.drawable.foto_patica
        ));

        lista.add(new Personaje(
                "Juan Alberto 'LMDShow'",
                "1994 · Fuengirola (Málaga)",
                "Se le conoce por trabajar tela tela.",
                "Política",
                R.drawable.foto_juan
        ));

        lista.add(new Personaje(
                "Luis 'Comandante' Lara",
                "1976 · Jerez de la Frontera (Cádiz)",
                "¿Quién no conoce los show del Comandante Lara?",
                "Flamenco",
                R.drawable.foto_comandante
        ));

        lista.add(new Personaje(
                "Juan Manuel Cortés 'JC' Reyes",
                "1997 · Sevilla",
                "Filósofo, médico y jurista cordobés del período islámico. Sus comentarios sobre Aristóteles fueron fundamentales para transmitir el pensamiento griego clásico a la Europa medieval.",
                "Filosofía",
                R.drawable.foto_jc
        ));

        return lista;
    }
}