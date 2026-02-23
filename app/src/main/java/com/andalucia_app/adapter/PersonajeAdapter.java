package com.andalucia_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.andalucia_app.R;
import com.andalucia_app.entity.Personaje;
import com.google.android.material.chip.Chip;
import java.util.List;

public class PersonajeAdapter extends RecyclerView.Adapter<PersonajeAdapter.PersonajeViewHolder> {

    private final List<Personaje> personajes;

    public PersonajeAdapter(List<Personaje> personajes) {
        this.personajes = personajes;
    }

    @NonNull
    @Override
    public PersonajeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_personaje, parent, false);
        return new PersonajeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonajeViewHolder holder, int position) {
        holder.bind(personajes.get(position));
    }

    @Override
    public int getItemCount() {
        return personajes.size();
    }

    static class PersonajeViewHolder extends RecyclerView.ViewHolder {

        private final ImageView ivPersonaje;
        private final TextView  tvNombre;
        private final TextView  tvEpoca;
        private final TextView  tvDescripcion;
        private final Chip      chipCategoria;

        PersonajeViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPersonaje   = itemView.findViewById(R.id.iv_personaje);
            tvNombre      = itemView.findViewById(R.id.tv_nombre);
            tvEpoca       = itemView.findViewById(R.id.tv_epoca);
            tvDescripcion = itemView.findViewById(R.id.tv_descripcion);
            chipCategoria = itemView.findViewById(R.id.chip_categoria);
        }

        void bind(Personaje p) {
            ivPersonaje.setImageResource(p.getImagenResId());
            tvNombre.setText(p.getNombre());
            tvEpoca.setText(p.getEpoca());
            tvDescripcion.setText(p.getDescripcion());
            chipCategoria.setText(p.getCategoria());
        }
    }
}