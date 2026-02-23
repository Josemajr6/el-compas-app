package com.andalucia_app.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.andalucia_app.R;
import com.andalucia_app.entity.Sonido;
import java.util.List;

/**
 * ════════════════════════════════════════════════════════════════
 * SonidoAdapter
 * ────────────────────────────────────────────────────────────────
 * Gestiona un único MediaPlayer activo a la vez:
 *   · Al pulsar Play en una tarjeta se detiene la anterior.
 *   · La SeekBar se actualiza en tiempo real con un Handler.
 *   · El usuario puede arrastrar la SeekBar para saltar.
 *   · Al terminar la canción la tarjeta vuelve al estado inicial.
 * ════════════════════════════════════════════════════════════════
 */
public class SonidoAdapter extends RecyclerView.Adapter<SonidoAdapter.SonidoViewHolder> {

    private final List<Sonido> sonidos;

    // ── Estado global del reproductor activo ─────────────────────
    private MediaPlayer      activePlayer = null;
    private SonidoViewHolder activeHolder = null;
    private final Handler    handler      = new Handler(Looper.getMainLooper());
    private Runnable         progressRunnable;
    // ─────────────────────────────────────────────────────────────

    public SonidoAdapter(List<Sonido> sonidos) {
        this.sonidos = sonidos;
    }

    // ─────────────────────────────────────────────────────────────
    //  RecyclerView callbacks
    // ─────────────────────────────────────────────────────────────

    @NonNull
    @Override
    public SonidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sonido, parent, false);
        return new SonidoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SonidoViewHolder holder, int position) {
        holder.bind(sonidos.get(position));
    }

    @Override
    public int getItemCount() {
        return sonidos.size();
    }

    @Override
    public void onViewRecycled(@NonNull SonidoViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder == activeHolder) releaseActivePlayer();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull SonidoViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (holder == activeHolder && activePlayer != null) {
            activePlayer.pause();
            holder.setPlayIcon();
        }
    }

    // ─────────────────────────────────────────────────────────────
    //  Gestión del reproductor global
    // ─────────────────────────────────────────────────────────────

    private void playAudio(Context context, SonidoViewHolder newHolder, Sonido sonido) {

        // 1. Si la misma tarjeta ya está activa → alternar play/pausa
        if (activeHolder == newHolder && activePlayer != null) {
            if (activePlayer.isPlaying()) {
                activePlayer.pause();
                newHolder.setPlayIcon();
                stopProgressUpdate();
            } else {
                activePlayer.start();
                newHolder.setPauseIcon();
                startProgressUpdate(newHolder);
            }
            return;
        }

        // 2. Liberar el reproductor anterior
        if (activeHolder != null && activeHolder != newHolder) {
            activeHolder.resetUI();
        }
        releaseActivePlayer();

        // 3. Crear nuevo MediaPlayer
        MediaPlayer player = MediaPlayer.create(context, sonido.getAudioResId());
        if (player == null) return; // recurso no encontrado

        // 4. Configurar SeekBar máximo
        newHolder.seekBar.setMax(player.getDuration());

        // 5. Reproducir
        player.start();
        newHolder.setPauseIcon();

        // 6. Al terminar
        player.setOnCompletionListener(mp -> {
            newHolder.resetUI();
            releaseActivePlayer();
        });

        // 7. Guardar referencias
        activePlayer = player;
        activeHolder = newHolder;

        // 8. Iniciar actualización de progreso
        startProgressUpdate(newHolder);
    }

    /** Actualiza la SeekBar cada 500 ms mientras reproduce */
    private void startProgressUpdate(SonidoViewHolder holder) {
        stopProgressUpdate();
        progressRunnable = new Runnable() {
            @Override
            public void run() {
                if (activePlayer != null && activePlayer.isPlaying()) {
                    int pos = activePlayer.getCurrentPosition();
                    holder.seekBar.setProgress(pos);
                    holder.tvTiempoActual.setText(formatTime(pos));
                    handler.postDelayed(this, 500);
                }
            }
        };
        handler.post(progressRunnable);
    }

    private void stopProgressUpdate() {
        if (progressRunnable != null) {
            handler.removeCallbacks(progressRunnable);
            progressRunnable = null;
        }
    }

    /** Libera el reproductor activo y limpia referencias */
    public void releaseActivePlayer() {
        stopProgressUpdate();
        if (activePlayer != null) {
            if (activePlayer.isPlaying()) activePlayer.stop();
            activePlayer.release();
            activePlayer = null;
        }
        activeHolder = null;
    }

    /** Formatea milisegundos a "m:ss" */
    private String formatTime(int millis) {
        int seconds = (millis / 1000) % 60;
        int minutes = millis / 1000 / 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    // ─────────────────────────────────────────────────────────────
    //  ViewHolder
    // ─────────────────────────────────────────────────────────────

    class SonidoViewHolder extends RecyclerView.ViewHolder {

        final ImageView  ivPortada;
        final TextView   tvTitulo;
        final TextView   tvArtista;
        final TextView   tvCategoria;
        final TextView   tvDuracion;
        final TextView   tvTiempoActual;
        final ImageButton btnPlayPausa;
        final ImageButton btnStop;
        final SeekBar    seekBar;

        SonidoViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPortada      = itemView.findViewById(R.id.iv_portada);
            tvTitulo       = itemView.findViewById(R.id.tv_sonido_titulo);
            tvArtista      = itemView.findViewById(R.id.tv_sonido_artista);
            tvCategoria    = itemView.findViewById(R.id.tv_sonido_categoria);
            tvDuracion     = itemView.findViewById(R.id.tv_sonido_duracion);
            tvTiempoActual = itemView.findViewById(R.id.tv_tiempo_actual);
            btnPlayPausa   = itemView.findViewById(R.id.btn_play_pausa);
            btnStop        = itemView.findViewById(R.id.btn_stop);
            seekBar        = itemView.findViewById(R.id.seekBar);
        }

        void bind(Sonido sonido) {
            ivPortada.setImageResource(sonido.getPortadaResId());
            tvTitulo.setText(sonido.getTitulo());
            tvArtista.setText(sonido.getArtista());
            tvCategoria.setText(sonido.getCategoria());
            tvDuracion.setText(sonido.getDuracion());

            // Asegurar estado inicial
            resetUI();

            // Botón Play/Pausa
            btnPlayPausa.setOnClickListener(v ->
                    playAudio(itemView.getContext(), this, sonido)
            );

            // Botón Stop
            btnStop.setOnClickListener(v -> {
                if (activeHolder == this) {
                    resetUI();
                    releaseActivePlayer();
                }
            });

            // SeekBar arrastrable
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser && activeHolder == SonidoViewHolder.this && activePlayer != null) {
                        activePlayer.seekTo(progress);
                        tvTiempoActual.setText(formatTime(progress));
                    }
                }
                @Override public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override public void onStopTrackingTouch(SeekBar seekBar) {}
            });
        }

        void setPlayIcon() {
            btnPlayPausa.setImageResource(android.R.drawable.ic_media_play);
        }

        void setPauseIcon() {
            btnPlayPausa.setImageResource(android.R.drawable.ic_media_pause);
        }

        void resetUI() {
            setPlayIcon();
            seekBar.setProgress(0);
            tvTiempoActual.setText("0:00");
        }
    }
}