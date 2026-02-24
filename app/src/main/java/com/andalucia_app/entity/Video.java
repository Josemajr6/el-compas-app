package com.andalucia_app.entity;
public class Video {

    private final String titulo;
    private final String duracionCategoria;
    private final String descripcion;
    private final int thumbnailResId;
    private final String videoUrl;

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