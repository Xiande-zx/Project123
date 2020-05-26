package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.proyectofinal.clase.Emp;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class EmpresaDetalle extends AppCompatActivity {

    private TextView name;
    private TextView number;
    private TextView email;
    private TextView adress;

    Integer temp;

    Emp emp;

    Button verListadoServicios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa_detalle);
        verListadoServicios = (Button)findViewById(R.id.button6);

        name=findViewById(R.id.textView20);
        number=findViewById(R.id.textView24);
        email=findViewById(R.id.textView25);
        adress=findViewById(R.id.textView26);

        Intent intent = getIntent();
        temp = intent.getIntExtra("idEmp", 0);

        String json ="";

        try {
            InputStream stream = getAssets().open("Emp.json");
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            json  = new String(buffer);
        } catch (Exception e) { }

        ArrayList<Emp> listEmp  = new ArrayList<Emp>(Arrays.asList(new Gson().fromJson(json, Emp[].class)));

        for (int i = 0; i < listEmp.size(); i++ ) {
            if (temp == listEmp.get(i).getId()) {
                emp=listEmp.get(i);
                name.setText(emp.getName());
                number.setText(Integer.toString(emp.getNumber()));
                email.setText(emp.getEmail());
                adress.setText(emp.getAdress());
            }
        }


        verListadoServicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ServicioListado.class);
                intent.putExtra("idEmp",temp);
                startActivity(intent);
            }
        });
    }
}
