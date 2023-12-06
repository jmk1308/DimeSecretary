package com.example.dimeguard;

public class Usuario {
    String nombre;
    String Email;
    String contrasena;
    String uid;

    public Usuario() {
        // Constructor vacío necesario para Firebase
    }

    public Usuario(String uid, String nombre, String email, String contrasena) {
        this.nombre = nombre;
        Email = email;
        this.contrasena = contrasena;
        this.uid = uid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}