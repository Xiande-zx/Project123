package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectofinal.Interface.EmpInterface;
import com.example.proyectofinal.Interface.ServiceInterface;
import com.example.proyectofinal.clase.Emp;
import com.example.proyectofinal.clase.Service;
import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddServicio extends AppCompatActivity {

    EditText type;
    EditText description;

    String ttype;
    String ddespriprion;

    Button add;
    Emp emp;
    Long idEmp;
    Service service2;
    Service serviceNew;
    TextView addSer;

    private static Retrofit retrofit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_servicio);

        type=findViewById(R.id.editText10);
        description=findViewById(R.id.editText11);
        add=findViewById(R.id.button15);
        addSer=findViewById(R.id.textView39);

        emp = (Emp)getIntent().getSerializableExtra("Emp");
        idEmp = emp.getId();

        serviceNew = (Service) getIntent().getSerializableExtra("servicio");
        if (serviceNew!=null){
            add.setText("UPDATE");
            addSer.setText("Update a service");
            type.setText(serviceNew.getType());
            description.setText(serviceNew.getDescription());
        }


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ttype=type.getText().toString();
                ddespriprion=description.getText().toString();
                Service service = new Service(ttype,ddespriprion,idEmp);

                service.setEmp(emp);
                if(retrofit==null){
                    retrofit = new Retrofit.Builder()
                            .baseUrl(getString(R.string.url))
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
                ServiceInterface service1 = retrofit.create(ServiceInterface.class);

                if (!add.getText().toString().equalsIgnoreCase("UPDATE")){
                    Call<Service> repos = service1.postService(service);

                    repos.enqueue(new Callback<Service>() {
                        @Override
                        public void onResponse(Call<Service> call, Response<Service> response) {
                            service2= response.body();
                            Intent intent1 = new Intent(AddServicio.this,EmpServicioDetalle.class);
                            intent1.putExtra("Emp",emp);
                            intent1.putExtra("idService",service2.getId());
                            startActivity(intent1);
                        }
                        @Override
                        public void onFailure(Call<Service> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), String.format("KO"), Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    serviceNew.setType(ttype);
                    serviceNew.setDescription(ddespriprion);
                    Call<Service> repos = service1.putService(serviceNew);

                    repos.enqueue(new Callback<Service>() {
                        @Override
                        public void onResponse(Call<Service> call, Response<Service> response) {
                            service2= response.body();
                            Intent intent1 = new Intent(AddServicio.this,EmpServicioDetalle.class);
                            intent1.putExtra("idService",serviceNew.getId());
                            startActivity(intent1);
                        }
                        @Override
                        public void onFailure(Call<Service> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), String.format("KO"), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
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
