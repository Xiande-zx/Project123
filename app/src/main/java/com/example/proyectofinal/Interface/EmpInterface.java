package com.example.proyectofinal.Interface;

import com.example.proyectofinal.clase.Emp;
import com.example.proyectofinal.clase.User;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EmpInterface {

    @GET("/controlApp/Emp/")
    Call<ArrayList<Emp>> listEmp();

    @GET("/controlApp/Emp/{id}")
    Call<Emp> getEmp(@Path("id") Long id);

    @POST("/controlApp/Emp/")
    Call<ResponseBody> postEmp(@Body Emp emp);

    @DELETE("/controlApp/Emp/")
    Call<ResponseBody> deleteEmp(@Body Emp emp);

}

