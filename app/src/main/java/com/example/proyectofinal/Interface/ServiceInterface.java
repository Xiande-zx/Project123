package com.example.proyectofinal.Interface;

import com.example.proyectofinal.clase.Service;
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

public interface ServiceInterface {

    @GET("/controlApp/Service/")
    Call<ArrayList<User>> listService();

    @GET("/controlApp/Service/{id}")
    Call<User> getService(@Path("id") Long id);

    @POST("/controlApp/Service/")
    Call<ResponseBody> postService(@Body Service service);

    @DELETE("/controlApp/Service/")
    Call<ResponseBody> deleteService(@Body Service service);

    @PUT("/controlApp/Service/")
    Call<ResponseBody> putService(@Body Service service);

}
