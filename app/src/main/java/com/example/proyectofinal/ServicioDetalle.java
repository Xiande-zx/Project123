package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.proyectofinal.clase.Service;
import com.google.gson.Gson;

public class ServicioDetalle extends AppCompatActivity {

    Service service;

    private TextView type;
    private TextView description;

    Button detalleEmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio_detalle);

        Intent myIntent = getIntent();
        getIntent().getSerializableExtra("serviceJson");
        Gson gson = new Gson();
        service =gson.fromJson(getIntent().getStringExtra("serviceJson"), Service.class);

        type=findViewById(R.id.textView12);
        type.setText(service.getType());

        description=findViewById(R.id.textView16);
        description.setText(service.getDescription());

        final Integer idEmp = service.getIdEmp();

        detalleEmp=findViewById(R.id.button9);
        detalleEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServicioDetalle.this,EmpresaDetalle.class);
                intent.putExtra("idEmp",idEmp);
                startActivity(intent);
            }
        });

    }
}
