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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

/**
 * ════════════════════════════════════════════════════════════════
 * SonidoAdapter — versión con Anterior, Siguiente y Volumen
 * ────────────────────────────────────────────────────────────────
 * · Botón Anterior  → reproduce la canción de la tarjeta anterior
 * · Botón Siguiente → reproduce la canción de la tarjeta siguiente
 * · SeekBar Volumen → ajusta el volumen solo del player activo
 * · Un único MediaPlayer activo a la vez
 * · SeekBar de progreso arrastrable
 * · Al terminar la canción la tarjeta vuelve al estado inicial
 * ════════════════════════════════════════════════════════════════
 */
public class SonidoAdapter extends RecyclerView.Adapter<SonidoAdapter.SonidoViewHolder> {

    private final List<Sonido>   sonidos;
    private       RecyclerView   recyclerView; // referencia para navegar a otra tarjeta

    // ── Estado global ─────────────────────────────────────────────
    private MediaPlayer      activePlayer     = null;
    private SonidoViewHolder activeHolder     = null;
    private int              activePosition   = Math.toIntExact(RecyclerView.NO_ID);
    private final Handler    handler          = new Handler(Looper.getMainLooper());
    private Runnable         progressRunnable;
    // ─────────────────────────────────────────────────────────────

    public SonidoAdapter(List<Sonido> sonidos) {
        this.sonidos = sonidos;
    }

    // Guardar la referencia al RecyclerView para poder hacer scroll y acceder a holders
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView rv) {
        super.onAttachedToRecyclerView(rv);
        this.recyclerView = rv;
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView rv) {
        super.onDetachedFromRecyclerView(rv);
        this.recyclerView = null;
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
        holder.bind(sonidos.get(position), position);
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
            stopProgressUpdate();
        }
    }

    // ─────────────────────────────────────────────────────────────
    //  Lógica de reproducción
    // ─────────────────────────────────────────────────────────────

    /**
     * Inicia o alterna play/pausa en la posición indicada.
     * Si hay otra canción activa, la detiene primero.
     */
    private void playAudio(Context context, SonidoViewHolder newHolder,
                           Sonido sonido, int position) {

        // Misma tarjeta activa → alternar play / pausa
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

        // Liberar reproductor anterior
        if (activeHolder != null) activeHolder.resetUI();
        releaseActivePlayer();

        // Crear nuevo MediaPlayer
        MediaPlayer player = MediaPlayer.create(context, sonido.getAudioResId());
        if (player == null) return;

        // Configurar SeekBar de progreso
        newHolder.seekBar.setMax(player.getDuration());
        newHolder.tvDuracionTotal.setText(formatTime(player.getDuration()));

        // Aplicar volumen actual del slider de esta tarjeta
        float vol = newHolder.seekBarVolumen.getProgress() / 100f;
        player.setVolume(vol, vol);

        // Reproducir
        player.start();
        newHolder.setPauseIcon();

        // Al terminar
        player.setOnCompletionListener(mp -> {
            newHolder.resetUI();
            releaseActivePlayer();
        });

        // Guardar estado
        activePlayer   = player;
        activeHolder   = newHolder;
        activePosition = position;

        startProgressUpdate(newHolder);
    }

    /**
     * Navega a la posición indicada: hace scroll, espera a que el holder
     * esté en pantalla y arranca la reproducción.
     */
    private void navegarA(int newPosition, Context context) {
        if (newPosition < 0 || newPosition >= sonidos.size()) return;
        if (recyclerView == null) return;

        // Liberar el actual antes de navegar
        if (activeHolder != null) activeHolder.resetUI();
        releaseActivePlayer();

        // Scroll suave a la nueva tarjeta
        recyclerView.smoothScrollToPosition(newPosition);

        // Pequeño retardo para que el RecyclerView infle el holder de destino
        handler.postDelayed(() -> {
            if (recyclerView == null) return;
            RecyclerView.ViewHolder vh =
                    recyclerView.findViewHolderForAdapterPosition(newPosition);
            if (vh instanceof SonidoViewHolder) {
                playAudio(context, (SonidoViewHolder) vh,
                        sonidos.get(newPosition), newPosition);
            }
        }, 350);
    }

    // ─────────────────────────────────────────────────────────────
    //  Helpers de progreso y liberación
    // ─────────────────────────────────────────────────────────────

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

    public void releaseActivePlayer() {
        stopProgressUpdate();
        if (activePlayer != null) {
            if (activePlayer.isPlaying()) activePlayer.stop();
            activePlayer.release();
            activePlayer   = null;
        }
        activeHolder   = null;
        activePosition = Math.toIntExact(RecyclerView.NO_ID);
    }

    private String formatTime(int millis) {
        int s = (millis / 1000) % 60;
        int m = millis / 1000 / 60;
        return String.format("%d:%02d", m, s);
    }

    // ─────────────────────────────────────────────────────────────
    //  ViewHolder
    // ─────────────────────────────────────────────────────────────

    class SonidoViewHolder extends RecyclerView.ViewHolder {

        final ImageView              ivPortada;
        final TextView               tvTitulo;
        final TextView               tvArtista;
        final TextView               tvCategoria;
        final TextView               tvDuracion;
        final TextView               tvTiempoActual;
        final TextView               tvDuracionTotal;
        final FloatingActionButton   btnPlayPausa;
        final ImageButton            btnAnterior;
        final ImageButton            btnSiguiente;
        final SeekBar                seekBar;
        final SeekBar                seekBarVolumen;

        SonidoViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPortada       = itemView.findViewById(R.id.iv_portada);
            tvTitulo        = itemView.findViewById(R.id.tv_sonido_titulo);
            tvArtista       = itemView.findViewById(R.id.tv_sonido_artista);
            tvCategoria     = itemView.findViewById(R.id.tv_sonido_categoria);
            tvDuracion      = itemView.findViewById(R.id.tv_sonido_duracion);
            tvTiempoActual  = itemView.findViewById(R.id.tv_tiempo_actual);
            tvDuracionTotal = itemView.findViewById(R.id.tv_sonido_duracion_total);
            btnPlayPausa    = itemView.findViewById(R.id.btn_play_pausa);
            btnAnterior     = itemView.findViewById(R.id.btn_anterior);
            btnSiguiente    = itemView.findViewById(R.id.btn_siguiente);
            seekBar         = itemView.findViewById(R.id.seekBar);
            seekBarVolumen  = itemView.findViewById(R.id.seekBarVolumen);
        }

        void bind(Sonido sonido, int position) {
            ivPortada.setImageResource(sonido.getPortadaResId());
            tvTitulo.setText(sonido.getTitulo());
            tvArtista.setText(sonido.getArtista());
            tvCategoria.setText(sonido.getCategoria());
            tvDuracion.setText(sonido.getDuracion());

            resetUI();

            // ── Play / Pausa ──────────────────────────────────────
            btnPlayPausa.setOnClickListener(v ->
                    playAudio(itemView.getContext(), this, sonido, position)
            );

            // ── Anterior ─────────────────────────────────────────
            btnAnterior.setOnClickListener(v -> {
                int target = position - 1;
                if (target >= 0) {
                    navegarA(target, itemView.getContext());
                }
                // Si ya es la primera canción el botón no hace nada
            });

            // ── Siguiente ────────────────────────────────────────
            btnSiguiente.setOnClickListener(v -> {
                int target = position + 1;
                if (target < sonidos.size()) {
                    navegarA(target, itemView.getContext());
                }
                // Si ya es la última canción el botón no hace nada
            });

            // ── SeekBar de progreso (arrastrable) ─────────────────
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar sb, int progress, boolean fromUser) {
                    if (fromUser && activeHolder == SonidoViewHolder.this
                            && activePlayer != null) {
                        activePlayer.seekTo(progress);
                        tvTiempoActual.setText(formatTime(progress));
                    }
                }
                @Override public void onStartTrackingTouch(SeekBar sb) {}
                @Override public void onStopTrackingTouch(SeekBar sb) {}
            });

            // ── SeekBar de volumen ────────────────────────────────
            seekBarVolumen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar sb, int progress, boolean fromUser) {
                    if (fromUser && activeHolder == SonidoViewHolder.this
                            && activePlayer != null) {
                        float vol = progress / 100f;
                        activePlayer.setVolume(vol, vol);
                    }
                }
                @Override public void onStartTrackingTouch(SeekBar sb) {}
                @Override public void onStopTrackingTouch(SeekBar sb) {}
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
            tvDuracionTotal.setText("0:00");
        }
    }
}