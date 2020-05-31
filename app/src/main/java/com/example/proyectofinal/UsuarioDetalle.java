package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectofinal.Interface.ServiceInterface;
import com.example.proyectofinal.Interface.UserInterface;
import com.example.proyectofinal.clase.Service;
import com.example.proyectofinal.clase.User;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsuarioDetalle extends AppCompatActivity {

    Button modificar, verServicios,out;

    TextView phone;
    TextView email;
    TextView userName;
    TextView inmune;
    private static Retrofit retrofit = null;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_detalle);

        modificar =(Button)findViewById(R.id.button5);
        verServicios =(Button)findViewById(R.id.button4);
        out=findViewById(R.id.log_Out);
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        final Long idUser = getIntent().getLongExtra("idUser",0);


        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Detail");

        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(getString(R.string.url))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        UserInterface service = retrofit.create(UserInterface.class);
        Call<User> repos = service.getUser(idUser);
        repos.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user = response.body();

                phone=findViewById(R.id.textView6);
                phone.setText(user.getTelefono());

                email=findViewById(R.id.textView4);
                email.setText(user.getEmail());

                userName=findViewById(R.id.textView2);
                userName.setText(user.getUserName());

                inmune=findViewById(R.id.textView10);
                inmune.setText("true");

            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), String.format("Time Out"), Toast.LENGTH_SHORT).show();
            }
        });

        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UsuarioRegistrar.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });

        verServicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UsuarioMenu.class);
                intent.putExtra("idUser", idUser);
                startActivity(intent);
            }
        });
    }
}
