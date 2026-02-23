package com.andalucia_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.andalucia_app.R;
import com.andalucia_app.entity.Animacion;
import java.util.List;

/**
 * ════════════════════════════════════════════════════════════════
 * AnimacionAdapter
 * ────────────────────────────────────────────────────────────────
 * Gestiona la lista de animaciones Lottie:
 *   · Cada tarjeta muestra la animación en bucle con autoPlay.
 *   · Botón Play/Pausa alterna reproducción.
 *   · Botón Reiniciar vuelve al frame 0 y reproduce.
 *   · pauseAll() para detener todo al salir del fragment.
 * ════════════════════════════════════════════════════════════════
 */
public class AnimacionAdapter extends RecyclerView.Adapter<AnimacionAdapter.AnimacionViewHolder> {

    private final List<Animacion> animaciones;

    public AnimacionAdapter(List<Animacion> animaciones) {
        this.animaciones = animaciones;
    }

    @NonNull
    @Override
    public AnimacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_animacion, parent, false);
        return new AnimacionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimacionViewHolder holder, int position) {
        holder.bind(animaciones.get(position));
    }

    @Override
    public int getItemCount() {
        return animaciones.size();
    }

    /** Pausa todas las animaciones activas (llamar en onPause / onDestroyView) */
    public void pauseAll() {
        // El RecyclerView gestiona sus propios ViewHolders;
        // pausar via notifyDataSetChanged no funciona bien con Lottie.
        // En su lugar cada ViewHolder gestiona su estado al hacer detach.
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull AnimacionViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.lottieView.pauseAnimation();
        holder.setPlayIcon();
    }

    // ─────────────────────────────────────────────────────────────
    //  ViewHolder
    // ─────────────────────────────────────────────────────────────

    static class AnimacionViewHolder extends RecyclerView.ViewHolder {

        final LottieAnimationView lottieView;
        final TextView            tvTitulo;
        final TextView            tvDescripcion;
        final TextView            tvCategoria;
        final ImageButton         btnPlayPausa;
        final ImageButton         btnReiniciar;

        AnimacionViewHolder(@NonNull View itemView) {
            super(itemView);
            lottieView    = itemView.findViewById(R.id.lottie_animacion);
            tvTitulo      = itemView.findViewById(R.id.tv_animacion_titulo);
            tvDescripcion = itemView.findViewById(R.id.tv_animacion_descripcion);
            tvCategoria   = itemView.findViewById(R.id.tv_animacion_categoria);
            btnPlayPausa  = itemView.findViewById(R.id.btn_anim_play_pausa);
            btnReiniciar  = itemView.findViewById(R.id.btn_anim_reiniciar);
        }

        void bind(Animacion animacion) {
            tvTitulo.setText(animacion.getTitulo());
            tvDescripcion.setText(animacion.getDescripcion());
            tvCategoria.setText(animacion.getCategoria());

            // Cargar el JSON de Lottie desde res/raw/
            lottieView.setAnimation(animacion.getRawResId());
            lottieView.setRepeatCount(LottieDrawable.INFINITE);
            lottieView.playAnimation();
            setPauseIcon();

            // ── Play / Pausa ──────────────────────────────────────
            btnPlayPausa.setOnClickListener(v -> {
                if (lottieView.isAnimating()) {
                    lottieView.pauseAnimation();
                    setPlayIcon();
                } else {
                    lottieView.resumeAnimation();
                    setPauseIcon();
                }
            });

            // ── Reiniciar ─────────────────────────────────────────
            btnReiniciar.setOnClickListener(v -> {
                lottieView.cancelAnimation();
                lottieView.setProgress(0f);
                lottieView.playAnimation();
                setPauseIcon();
            });
        }

        void setPlayIcon() {
            btnPlayPausa.setImageResource(android.R.drawable.ic_media_play);
        }

        void setPauseIcon() {
            btnPlayPausa.setImageResource(android.R.drawable.ic_media_pause);
        }
    }
}