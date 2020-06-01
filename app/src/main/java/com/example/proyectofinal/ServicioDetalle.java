package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectofinal.Interface.ServiceInterface;
import com.example.proyectofinal.Interface.UserInterface;
import com.example.proyectofinal.clase.Service;
import com.example.proyectofinal.clase.User;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServicioDetalle extends AppCompatActivity {

    Service service1;

    private TextView type;
    private TextView description;

    Button detalleEmp;
    private static Retrofit retrofit = null;
    Long idEmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio_detalle);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(getString(R.string.detailService));

        Long idService = getIntent().getLongExtra("idService",0);

        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(getString(R.string.url))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        ServiceInterface service = retrofit.create(ServiceInterface.class);

        Call<Service > repos = service.getService(idService);

        repos.enqueue(new Callback<Service>() {
            @Override
            public void onResponse(Call<Service> call, Response<Service> response) {

                service1 = response.body();
                idEmp = service1.getIdEmp();
                type=findViewById(R.id.textView12);
                type.setText(service1.getType());
                description=findViewById(R.id.textView16);
                description.setText(service1.getDescription());
            }
            @Override
            public void onFailure(Call<Service> call, Throwable t) {
                Toast.makeText(getApplicationContext(), String.format("KO"), Toast.LENGTH_SHORT).show();
            }
        });

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
