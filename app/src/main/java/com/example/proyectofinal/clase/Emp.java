package com.example.proyectofinal.clase;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Emp {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("UserName")
    @Expose
    private String UserName;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("adress")
    @Expose
    private String adress;

    public Emp(){

    }

    public Emp(Integer id, String name, String description, Integer number, String email, String adress) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.number = number;
        this.email = email;
        this.adress = adress;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String direccion) {
        this.adress = direccion;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
