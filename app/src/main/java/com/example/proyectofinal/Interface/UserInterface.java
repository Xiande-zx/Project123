package com.example.proyectofinal.Interface;

import com.example.proyectofinal.clase.Emp;
import com.example.proyectofinal.clase.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserInterface {
    @GET("/controlApp/Emp/")
    Call<ArrayList<Emp>> listEmp();
}

