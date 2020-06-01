package com.example.proyectofinal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectofinal.Interface.ServiceInterface;
import com.example.proyectofinal.clase.Emp;
import com.example.proyectofinal.clase.Service;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmpServicioDetalle extends AppCompatActivity {

    Service service1;
    Button delete, update,back;

    private TextView type;
    private TextView description;
    private static Retrofit retrofit = null;
    Emp emp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_servicio_detalle);
        delete=findViewById(R.id.button14);
        update=findViewById(R.id.button13);
        back=findViewById(R.id.button12);

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(getString(R.string.detailService));

        final Long idService = getIntent().getLongExtra("idService",0);
        emp = (Emp) getIntent().getSerializableExtra("Emp");

        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(getString(R.string.url))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        final ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<Service> repos = service.getService(idService);
        repos.enqueue(new Callback<Service>() {
            @Override
            public void onResponse(Call<Service> call, Response<Service> response) {
                service1 = response.body();
                type=findViewById(R.id.textView18);
                type.setText(service1.getType());
                description=findViewById(R.id.textView27);
                description.setText(service1.getDescription());
            }
            @Override
            public void onFailure(Call<Service> call, Throwable t) {
                Toast.makeText(getApplicationContext(), String.format("Time Out"), Toast.LENGTH_SHORT).show();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogButtonClicked();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmpServicioDetalle.this,AddServicio.class);
                intent.putExtra("servicio",service1);
                intent.putExtra("Emp",emp);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmpServicioDetalle.this,EmpMenu.class);
                intent.putExtra("Emp",emp);
                startActivity(intent);
            }
        });
    }

    //show alert when you want delete a service
    public void showAlertDialogButtonClicked() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("DELETE SERVICE");
        builder.setMessage("Do you want to delete this service?");
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final ServiceInterface service = retrofit.create(ServiceInterface.class);
                Call<Void> repos = service.deleteService(service1);
                repos.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Intent intent = new Intent(EmpServicioDetalle.this,EmpMenu.class);
                        intent.putExtra("Emp",emp);
                        startActivity(intent);
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), String.format("Time Out"), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
