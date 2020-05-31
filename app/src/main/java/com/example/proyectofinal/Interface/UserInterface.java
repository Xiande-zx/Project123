package com.example.proyectofinal.Interface;

import com.example.proyectofinal.clase.Emp;
import com.example.proyectofinal.clase.User;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserInterface {

    @GET("/controlApp/User/")
    Call<ArrayList<User>> listEmp();

    @GET("/controlApp/User/{id}")
    Call<User> getUser(@Path("id") Long id);

    @POST("/controlApp/User/")
    Call<User> postUser(@Body User user);

    @DELETE("/controlApp/User/")
    Call<User> deleteUser(@Body User user);

    @PUT("/controlApp/User/")
    Call<User> putUser(@Body User user);

}
