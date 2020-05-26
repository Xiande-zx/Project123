package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.proyectofinal.clase.User;
import com.google.gson.Gson;

public class UsuarioDetalle extends AppCompatActivity {
    Button modificar, verServicios;

    TextView phone;
    TextView email;
    TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_detalle);

        modificar =(Button)findViewById(R.id.button5);
        verServicios =(Button)findViewById(R.id.button4);

        Intent myIntent = getIntent();
        getIntent().getSerializableExtra("userJson");
        Gson gson = new Gson();
        final User user = gson.fromJson(getIntent().getStringExtra("userJson"), User.class);

        final String userStr = getIntent().getStringExtra("userJson");



        phone=findViewById(R.id.textView6);
        phone.setText(user.getTelefono());


        email=findViewById(R.id.textView4);
        email.setText(user.getEmail());

        userName=findViewById(R.id.textView2);
        userName.setText(user.getUserName());

        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UsuarioRegistrar.class);
                startActivity(intent);
            }
        });

        verServicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UsuarioMenu.class);
                intent.putExtra("userJson", userStr);
                startActivity(intent);
            }
        });
    }
}
