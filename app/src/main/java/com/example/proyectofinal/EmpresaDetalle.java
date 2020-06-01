package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectofinal.Interface.EmpInterface;
import com.example.proyectofinal.Interface.UserInterface;
import com.example.proyectofinal.clase.Emp;
import com.example.proyectofinal.clase.User;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmpresaDetalle extends AppCompatActivity {

    private TextView name;
    private TextView number;
    private TextView email;
    private TextView adress,description;
    Long temp;
    Emp emp;
    Button verListadoServicios;
    private static Retrofit retrofit = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa_detalle);
        verListadoServicios = (Button)findViewById(R.id.button10);


        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Company detail");


        name=findViewById(R.id.textView20);
        number=findViewById(R.id.textView24);
        email=findViewById(R.id.textView25);
        adress=findViewById(R.id.textView26);
        description=findViewById(R.id.textView42);

        Intent intent = getIntent();
        temp = intent.getLongExtra("idEmp", 0);

        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(getString(R.string.url))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        EmpInterface service = retrofit.create(EmpInterface.class);
        Call<Emp> repos = service.getEmp(temp);
        repos.enqueue(new Callback<Emp>() {
            @Override
            public void onResponse(Call<Emp> call, Response<Emp> response) {
                emp = response.body();
                name.setText(emp.getName());
                number.setText(Integer.toString(emp.getNumber()));
                email.setText(emp.getEmail());
                adress.setText(emp.getAdress());
                description.setText(emp.getDescription());
            }
            @Override
            public void onFailure(Call<Emp> call, Throwable t) {
                Toast.makeText(getApplicationContext(), String.format("Time Out"), Toast.LENGTH_SHORT).show();
            }
        });

        verListadoServicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{emp.getEmail()});
                email.putExtra(Intent.EXTRA_SUBJECT, "");
                email.putExtra(Intent.EXTRA_TEXT, "");

                //need this to prompts email client only
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });
    }
}
