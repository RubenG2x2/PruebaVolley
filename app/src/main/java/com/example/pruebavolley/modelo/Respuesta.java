package com.example.pruebavolley.modelo;

public class Respuesta {
    private String mensaje;

    private int id;

    public Respuesta(String mensaje, int id) {
        this.mensaje = mensaje;
        this.id = id;
    }

    public Respuesta() {
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
