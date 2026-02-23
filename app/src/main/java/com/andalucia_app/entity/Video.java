package com.andalucia_app.entity;

/**
 * Modelo de datos para un Vídeo de "El Compás".
 *
 * ─────────────────────────────────────────────────
 * ¿CÓMO AÑADIR UN VÍDEO NUEVO?
 *
 *   new Video(
 *       "Título",
 *       "Duración · Categoría",
 *       "Descripción breve.",
 *       R.drawable.mi_thumbnail,  // imagen previa (opcional, usa R.drawable.videos si no tienes)
 *       "https://url-del-video.mp4"  // URL pública o ruta raw: "android.resource://com.andalucia_app/" + R.raw.mi_video
 *   )
 * ─────────────────────────────────────────────────
 */
public class Video {

    private final String titulo;
    private final String duracionCategoria;   // ej. "3:24 · Flamenco"
    private final String descripcion;
    private final int    thumbnailResId;
    private final String videoUrl;            // URL https o android.resource://

    public Video(String titulo, String duracionCategoria, String descripcion,
                 int thumbnailResId, String videoUrl) {
        this.titulo             = titulo;
        this.duracionCategoria  = duracionCategoria;
        this.descripcion        = descripcion;
        this.thumbnailResId     = thumbnailResId;
        this.videoUrl           = videoUrl;
    }

    public String getTitulo()             { return titulo; }
    public String getDuracionCategoria()  { return duracionCategoria; }
    public String getDescripcion()        { return descripcion; }
    public int    getThumbnailResId()     { return thumbnailResId; }
    public String getVideoUrl()           { return videoUrl; }
}