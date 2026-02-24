package com.andalucia_app.adapter;


import android.content.Context;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
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

import java.util.HashMap;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private final List<Video> videos;
    private ExoPlayer activePlayer = null;
    private VideoViewHolder activeHolder = null;

    public VideoAdapter(List<Video> videos) {
        this.videos = videos;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.bind(videos.get(position));
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    @Override
    public void onViewRecycled(@NonNull VideoViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder == activeHolder && activePlayer != null) {
            activePlayer.pause();
            holder.playerView.setPlayer(null);
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull VideoViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (holder == activeHolder && activePlayer != null) {
            activePlayer.pause();
            holder.setPlayIcon();
        }
    }

    // guardado ANTES de nullificar activeHolder
    void playVideo(Context context, VideoViewHolder newHolder, Video video) {

        // Guardar referencia anterior en variable LOCAL antes de tocar nada
        VideoViewHolder previousHolder = activeHolder;

        // Liberar player anterior
        if (activePlayer != null) {
            activePlayer.stop();
            activePlayer.release();
            activePlayer = null;
        }

        // restaurar UI anterior usando la variable local (no null)
        if (previousHolder != null && previousHolder != newHolder) {
            previousHolder.playerView.setPlayer(null);
            previousHolder.showThumbnail();
        }
        activeHolder = null;

        // Nuevo player
        ExoPlayer player = new ExoPlayer.Builder(context).build();
        player.setMediaItem(MediaItem.fromUri(video.getVideoUrl()));
        player.prepare();
        player.setPlayWhenReady(true);

        // Volumen inicil al 100%
        float vol = newHolder.seekBarVolumen.getProgress() / 100f;
        player.setVolume(vol);

        // Vincular vista
        newHolder.playerView.setPlayer(player);
        newHolder.showPlayer();
        newHolder.setPauseIcon();

        // Listener de fin de reproduccion
        final ExoPlayer playerRef = player;
        final VideoViewHolder holderRef = newHolder;
        player.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int state) {
                if (state == Player.STATE_ENDED && activePlayer == playerRef) {
                    playerRef.seekTo(0);
                    playerRef.pause();
                    holderRef.showThumbnail();
                    holderRef.setPlayIcon();
                }
            }
        });

        // Guardamos estado
        activePlayer = player;
        activeHolder = newHolder;
    }

    public void releaseActivePlayer() {
        if (activePlayer != null) {
            activePlayer.stop();
            activePlayer.release();
            activePlayer = null;
        }
        if (activeHolder != null) {
            activeHolder.playerView.setPlayer(null);
            activeHolder.showThumbnail();
            activeHolder.setPlayIcon();
            activeHolder = null;
        }
    }

    // ViewHolder como clase publica estatica para evitar warning de visibilidad
    public class VideoViewHolder extends RecyclerView.ViewHolder {

        PlayerView playerView;
        ImageView ivThumbnail;
        FloatingActionButton fabPlay;
        TextView tvTitulo;
        TextView tvDuracion;
        TextView tvDescripcion;
        LinearLayout llVolumen;
        SeekBar seekBarVolumen;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            playerView      = itemView.findViewById(R.id.player_view);
            ivThumbnail     = itemView.findViewById(R.id.iv_thumbnail);
            fabPlay         = itemView.findViewById(R.id.fab_play);
            tvTitulo        = itemView.findViewById(R.id.tv_video_titulo);
            tvDuracion      = itemView.findViewById(R.id.tv_video_duracion);
            tvDescripcion   = itemView.findViewById(R.id.tv_video_descripcion);
            llVolumen       = itemView.findViewById(R.id.ll_volumen);
            seekBarVolumen  = itemView.findViewById(R.id.seekBarVolumen);
        }

        public void bind(Video video) {
            tvTitulo.setText(video.getTitulo());
            tvDuracion.setText(video.getDuracionCategoria());
            tvDescripcion.setText(video.getDescripcion());

            showThumbnail();

            // miniatura
            ivThumbnail.setImageResource(R.drawable.videos);
            new ThumbnailTask(ivThumbnail, itemView.getContext()).execute(video.getVideoUrl());

            // Volumen al 100%
            seekBarVolumen.setMax(100);
            seekBarVolumen.setProgress(100);
            seekBarVolumen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar sb, int progress, boolean fromUser) {
                    if (fromUser && activeHolder == VideoViewHolder.this && activePlayer != null) {
                        activePlayer.setVolume(progress / 100f);
                    }
                }
                @Override public void onStartTrackingTouch(SeekBar sb) {}
                @Override public void onStopTrackingTouch(SeekBar sb) {}
            });

            fabPlay.setOnClickListener(v -> playVideo(itemView.getContext(), this, video));
        }

        void setPlayIcon()  { fabPlay.setImageResource(android.R.drawable.ic_media_play); }
        void setPauseIcon() { fabPlay.setImageResource(android.R.drawable.ic_media_pause); }

        void showThumbnail() {
            ivThumbnail.setVisibility(View.VISIBLE);
            fabPlay.setVisibility(View.VISIBLE);
            playerView.setVisibility(View.GONE);
            llVolumen.setVisibility(View.GONE);
        }

        void showPlayer() {
            ivThumbnail.setVisibility(View.GONE);
            fabPlay.setVisibility(View.GONE);
            playerView.setVisibility(View.VISIBLE);
            llVolumen.setVisibility(View.VISIBLE);
        }
    }

    // Thumbnail en background thread
    private static class ThumbnailTask extends AsyncTask<String, Void, Bitmap> {

        private final ImageView target;
        private final Context ctx;

        ThumbnailTask(ImageView iv, Context context) {
            this.target = iv;
            this.ctx = context;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            try {
                if (url.startsWith("android.resource://")) {
                    retriever.setDataSource(ctx, Uri.parse(url));
                } else {
                    retriever.setDataSource(url, new HashMap<>());
                }
                return retriever.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
            } catch (Exception e) {
                return null;
            } finally {
                try {
                    retriever.release();
                } catch (Exception ignored) {}
            }
        }

        @Override
        protected void onPostExecute(Bitmap bmp) {
            if (bmp != null && target != null) {
                target.setImageBitmap(bmp);
            }
        }
    }
}