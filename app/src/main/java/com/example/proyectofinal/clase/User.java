package com.example.proyectofinal.clase;

import android.content.Intent;

public class User {
    private Integer id;

    private String userName;
    private String password;

    private String phone;
    private String email;
    private boolean inmune;


    public User(){

    }

    public User(Integer id, String userName, String password, String phone, String email, boolean inmune) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.inmune = inmune;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return phone;
    }

    public void setTelefono(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isInmune() {
        return inmune;
    }

    public void setInmune(boolean inmune) {
        this.inmune = inmune;
    }

}
