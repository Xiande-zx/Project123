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
import com.example.proyectofinal.clase.Emp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmpEmpDetalle extends AppCompatActivity {

    private TextView name,userName,description;
    private TextView number;
    private TextView email;
    private TextView adress;
    private static Retrofit retrofit = null;
    Emp emp;
    Button update,out, back;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_emp_detalle);

        name=findViewById(R.id.EEtextView20);
        userName=findViewById(R.id.EEtextView);
        description=findViewById(R.id.EEtextView42);
        number=findViewById(R.id.EEtextView24);
        email=findViewById(R.id.EEtextView25);
        adress=findViewById(R.id.EEtextView26);
        update=findViewById(R.id.button10);
        back=findViewById(R.id.button5);

        out=findViewById(R.id.log_Out);
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("COMPANY DETAIL");

        Long idEmp = getIntent().getLongExtra("idEmp",0);

        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(getString(R.string.url))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        EmpInterface user = retrofit.create(EmpInterface.class);

        Call<Emp> repos = user.getEmp(idEmp);

        repos.enqueue(new Callback<Emp>() {
            @Override
            public void onResponse(Call<Emp> call, Response<Emp> response) {
                emp = response.body();

                name.setText(emp.getName());
                userName.setText(emp.getUserName());
                description.setText(emp.getDescription());
                number.setText(Integer.toString(emp.getNumber()));
                email.setText(emp.getEmail());
                adress.setText(emp.getAdress());
            }
            @Override
            public void onFailure(Call<Emp> call, Throwable t) {
                Toast.makeText(getApplicationContext(), String.format("Time Out"), Toast.LENGTH_SHORT).show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmpEmpDetalle.this, EmpUpdate.class);
                intent.putExtra("Emp",emp);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmpEmpDetalle.this, EmpMenu.class);
                intent.putExtra("Emp",emp);
                startActivity(intent);
            }
        });


    }
}
