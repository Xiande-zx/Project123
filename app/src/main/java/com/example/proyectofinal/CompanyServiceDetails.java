package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.proyectofinal.clase.Service;
import com.google.gson.Gson;

public class CompanyServiceDetails extends AppCompatActivity {

    Service service;

    private TextView type;
    private TextView description;
    Button editService, deleteService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_service_details);

        deleteService=findViewById(R.id.button14);
        editService=findViewById(R.id.button13);

        editService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(CompanyServiceDetails.this, CompanyAddService.class);
                startActivity(intent);
            }
        });

        deleteService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CompanyServiceDetails.this, CompanyHome.class);
                startActivity(i);
            }
        });
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
