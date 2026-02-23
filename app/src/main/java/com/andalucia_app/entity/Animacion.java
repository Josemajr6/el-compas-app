package com.andalucia_app.entity;

/**
 * Modelo de datos para una Animación Lottie.
 *
 * ─────────────────────────────────────────────────
 * ¿CÓMO AÑADIR UNA ANIMACIÓN NUEVA?
 *
 *   new Animacion(
 *       "Título",
 *       "Descripción breve.",
 *       "Categoría",
 *       R.raw.mi_animacion_json   ← fichero .json de Lottie en res/raw/
 *   )
 * ─────────────────────────────────────────────────
 */
public class Animacion {

    private final String titulo;
    private final String descripcion;
    private final String categoria;
    private final int    rawResId;     // Recurso .json de Lottie en res/raw/

    public Animacion(String titulo, String descripcion, String categoria, int rawResId) {
        this.titulo      = titulo;
        this.descripcion = descripcion;
        this.categoria   = categoria;
        this.rawResId    = rawResId;
    }

    public String getTitulo()      { return titulo; }
    public String getDescripcion() { return descripcion; }
    public String getCategoria()   { return categoria; }
    public int    getRawResId()    { return rawResId; }
}