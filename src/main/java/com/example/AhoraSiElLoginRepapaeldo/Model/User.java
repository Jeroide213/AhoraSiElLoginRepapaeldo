package com.example.AhoraSiElLoginRepapaeldo.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usuarios")
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private Rol rol;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public User() {
    }

    public User(String id, String username, String password, Rol rol) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.rol = rol;
    }
}
