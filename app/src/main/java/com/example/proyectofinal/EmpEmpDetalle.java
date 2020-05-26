package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.proyectofinal.clase.Emp;
import com.example.proyectofinal.clase.User;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class EmpEmpDetalle extends AppCompatActivity {

    private TextView name;
    private TextView number;
    private TextView email;
    private TextView adress;

    Integer temp;

    Emp emp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_emp_detalle);

        name=findViewById(R.id.textView20);
        number=findViewById(R.id.textView24);
        email=findViewById(R.id.textView25);
        adress=findViewById(R.id.textView26);

        Intent myIntent = getIntent();
        getIntent().getSerializableExtra("userJson");
        Gson gson = new Gson();

        final Emp emp = gson.fromJson(getIntent().getStringExtra("userJson"), Emp.class);

                name.setText(emp.getName());
                number.setText(Integer.toString(emp.getNumber()));
                email.setText(emp.getEmail());
                adress.setText(emp.getAdress());


    }
}
