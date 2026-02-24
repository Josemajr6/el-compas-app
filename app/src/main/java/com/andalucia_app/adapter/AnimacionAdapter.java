package com.andalucia_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.andalucia_app.R;
import com.andalucia_app.entity.Animacion;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.widget.TextView;
import java.util.List;

/**
 * ════════════════════════════════════════════════════════════════
 * AnimacionAdapter — Control simplificado: solo Play/Pausa
 * ────────────────────────────────────────────────────────────────
 * · Un único botón FAB Play/Pausa.
 * · Al pulsar Play: la animación continúa desde donde estaba.
 * · Al pulsar Pausa: la animación se detiene con fade-out suave.
 * · Animación de rebote al cambiar estado.
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

    /** Pausa todas las animaciones activas */
    public void pauseAll() {
        // Gestionado en onViewDetachedFromWindow
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
        final FloatingActionButton btnPlayPausa;

        AnimacionViewHolder(@NonNull View itemView) {
            super(itemView);
            lottieView    = itemView.findViewById(R.id.lottie_animacion);
            tvTitulo      = itemView.findViewById(R.id.tv_animacion_titulo);
            tvDescripcion = itemView.findViewById(R.id.tv_animacion_descripcion);
            tvCategoria   = itemView.findViewById(R.id.tv_animacion_categoria);
            btnPlayPausa  = itemView.findViewById(R.id.btn_anim_play_pausa);
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
                animateButtonPress(v);
                if (lottieView.isAnimating()) {
                    lottieView.pauseAnimation();
                    setPlayIcon();
                } else {
                    lottieView.resumeAnimation();
                    setPauseIcon();
                }
            });
        }

        private void animateButtonPress(View v) {
            v.animate()
                    .scaleX(0.85f)
                    .scaleY(0.85f)
                    .setDuration(80)
                    .withEndAction(() ->
                            v.animate()
                                    .scaleX(1f)
                                    .scaleY(1f)
                                    .setDuration(200)
                                    .setInterpolator(new OvershootInterpolator(3f))
                                    .start()
                    ).start();
        }

        void setPlayIcon() {
            btnPlayPausa.setImageResource(android.R.drawable.ic_media_play);
        }

        void setPauseIcon() {
            btnPlayPausa.setImageResource(android.R.drawable.ic_media_pause);
        }
    }
}