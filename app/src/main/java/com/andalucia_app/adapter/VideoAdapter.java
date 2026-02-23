package com.andalucia_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import androidx.recyclerview.widget.RecyclerView;

import com.andalucia_app.R;
import com.andalucia_app.entity.Video;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * ════════════════════════════════════════════════════════════════
 * VideoAdapter
 * ────────────────────────────────────────────────────────────────
 * Gestión inteligente de un único ExoPlayer activo a la vez:
 *   · Al pulsar Play en una tarjeta se libera el reproductor
 *     anterior y se inicializa uno nuevo para la tarjeta actual.
 *   · Al hacer scroll fuera de la vista el reproductor se pausa
 *     automáticamente (onViewRecycled / onViewDetachedFromWindow).
 *   · Se recuerda qué ViewHolder tiene el player activo para
 *     poder liberarlo correctamente.
 * ════════════════════════════════════════════════════════════════
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private final List<Video> videos;

    // ── Estado global del reproductor activo ──────────────────────
    private ExoPlayer       activePlayer      = null;
    private VideoViewHolder activeHolder      = null;
    // ─────────────────────────────────────────────────────────────

    public VideoAdapter(List<Video> videos) {
        this.videos = videos;
    }

    // ─────────────────────────────────────────────────────────────
    //  RecyclerView callbacks
    // ─────────────────────────────────────────────────────────────

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.bind(videos.get(position));
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    /** Cuando el ViewHolder sale del RecyclerView, liberar si es el activo */
    @Override
    public void onViewRecycled(@NonNull VideoViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder == activeHolder) {
            releaseActivePlayer();
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull VideoViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (holder == activeHolder) {
            if (activePlayer != null) {
                activePlayer.pause();
            }
        }
    }

    // ─────────────────────────────────────────────────────────────
    //  Gestión del reproductor global
    // ─────────────────────────────────────────────────────────────

    /**
     * Inicializa (o reutiliza) un ExoPlayer en el holder dado.
     * Si hay otro player activo, lo libera primero.
     */
    private void playVideo(Context context, VideoViewHolder newHolder, Video video) {

        // 1. Liberar el reproductor anterior si existe y es distinto
        if (activeHolder != null && activeHolder != newHolder) {
            releaseActivePlayer();
            // Restaurar UI del holder anterior
            activeHolder.showThumbnail();
        }

        // 2. Crear nuevo ExoPlayer
        ExoPlayer player = new ExoPlayer.Builder(context).build();
        player.setMediaItem(MediaItem.fromUri(video.getVideoUrl()));
        player.prepare();
        player.setPlayWhenReady(true);

        // 3. Vincular con la vista
        newHolder.playerView.setPlayer(player);
        newHolder.showPlayer();

        // 4. Listener para cuando termine
        player.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int state) {
                if (state == Player.STATE_ENDED) {
                    player.seekTo(0);
                    player.pause();
                    newHolder.showThumbnail();
                    releaseActivePlayer();
                }
            }
        });

        // 5. Guardar referencias activas
        activePlayer = player;
        activeHolder = newHolder;
    }

    /** Libera el reproductor activo y limpia referencias */
    public void releaseActivePlayer() {
        if (activePlayer != null) {
            activePlayer.release();
            activePlayer = null;
        }
        if (activeHolder != null) {
            activeHolder.playerView.setPlayer(null);
            activeHolder = null;
        }
    }

    // ─────────────────────────────────────────────────────────────
    //  ViewHolder
    // ─────────────────────────────────────────────────────────────

    class VideoViewHolder extends RecyclerView.ViewHolder {

        final PlayerView             playerView;
        final ImageView              ivThumbnail;
        final FloatingActionButton   fabPlay;
        final TextView               tvTitulo;
        final TextView               tvDuracion;
        final TextView               tvDescripcion;

        VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            playerView    = itemView.findViewById(R.id.player_view);
            ivThumbnail   = itemView.findViewById(R.id.iv_thumbnail);
            fabPlay       = itemView.findViewById(R.id.fab_play);
            tvTitulo      = itemView.findViewById(R.id.tv_video_titulo);
            tvDuracion    = itemView.findViewById(R.id.tv_video_duracion);
            tvDescripcion = itemView.findViewById(R.id.tv_video_descripcion);
        }

        void bind(Video video) {
            tvTitulo.setText(video.getTitulo());
            tvDuracion.setText(video.getDuracionCategoria());
            tvDescripcion.setText(video.getDescripcion());
            ivThumbnail.setImageResource(video.getThumbnailResId());

            // Asegurar estado inicial (thumbnail visible, player oculto)
            showThumbnail();

            // Click en FAB → reproducir
            fabPlay.setOnClickListener(v ->
                    playVideo(itemView.getContext(), this, video)
            );
        }

        /** Muestra thumbnail + botón play, oculta PlayerView */
        void showThumbnail() {
            ivThumbnail.setVisibility(View.VISIBLE);
            fabPlay.setVisibility(View.VISIBLE);
            playerView.setVisibility(View.GONE);
        }

        /** Oculta thumbnail, muestra PlayerView */
        void showPlayer() {
            ivThumbnail.setVisibility(View.GONE);
            fabPlay.setVisibility(View.GONE);
            playerView.setVisibility(View.VISIBLE);
        }
    }
}