package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectofinal.clase.Service;
import com.google.gson.Gson;

public class AddServicio extends AppCompatActivity {

    EditText type;
    EditText description;

    String ttype;
    String ddespriprion;

    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_servicio);

        type=findViewById(R.id.editText10);
        description=findViewById(R.id.editText11);
        add=findViewById(R.id.button15);

        final Intent intent = getIntent();
        if(intent!=null){
            getIntent().getSerializableExtra("serviceJson");
            Gson gson = new Gson();
            final Service service = gson.fromJson(getIntent().getStringExtra("serviceJson"), Service.class);
            //type.setText(service.getType());
            //description.setText(service.getDescription());
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ttype=type.getText().toString();
                ddespriprion=description.getText().toString();

                Service service = new Service(1,ttype,ddespriprion,2);

                Intent intent1 = new Intent(AddServicio.this, EmpServicioDetalle.class);
                startActivity(intent);
            }
        });

    }

    public void error(String str){
        Context context = getApplicationContext();
        CharSequence text = "El camp "+str+" no pot estar buit!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
