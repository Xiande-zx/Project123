package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.proyectofinal.clase.Service;
import com.google.gson.Gson;

public class EmpServicioDetalle extends AppCompatActivity {

    Service service;

    private TextView type;
    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_servicio_detalle);

        Intent myIntent = getIntent();
        getIntent().getSerializableExtra("serviceJson");
        Gson gson = new Gson();
        service =gson.fromJson(getIntent().getStringExtra("serviceJson"), Service.class);

        type=findViewById(R.id.textView18);
        type.setText(service.getType());

        description=findViewById(R.id.textView27);
        description.setText(service.getDescription());
    }
}
