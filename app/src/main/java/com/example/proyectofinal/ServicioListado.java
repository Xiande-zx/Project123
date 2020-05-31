package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.proyectofinal.Interface.EmpInterface;
import com.example.proyectofinal.Interface.ServiceInterface;
import com.example.proyectofinal.UserAdapter.USAdapter;
import com.example.proyectofinal.clase.Emp;
import com.example.proyectofinal.clase.Service;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServicioListado extends AppCompatActivity {

    EditText buscador;

    Long temp;

    ArrayList<Service> list;
    ArrayList<Service> list2;

    private static Retrofit retrofit = null;

    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio_listado);
        buscador =(EditText) findViewById(R.id.editText9);

        Intent intent = getIntent();
        temp = intent.getLongExtra("idEmp", 0);

        listView=findViewById(R.id.lista);

        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(getString(R.string.url))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        ServiceInterface service = retrofit.create(ServiceInterface.class);
        Call<ArrayList<Service>> repos = service.listService();

        repos.enqueue(new Callback<ArrayList<Service>>() {
            @Override
            public void onResponse(Call<ArrayList<Service>> call, Response<ArrayList<Service>> response) {
                Toast.makeText(getApplicationContext(), String.format("OK"), Toast.LENGTH_SHORT).show();
                list = response.body();
                final ArrayList<Service> list1 = new ArrayList<>();

                for (int i = 0; i < list.size(); i++){
                    if (list.get(i).getIdEmp()==temp){
                        list1.add(list.get(i));
                    }
                }

                USAdapter usAdapter = new USAdapter(ServicioListado.this,list1);

                listView.setAdapter(usAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Long idService = list1.get(position).getId();

                        Intent activity2Intent = new Intent(getApplicationContext(), ServicioDetalle.class);
                        activity2Intent.putExtra("idService", idService);
                        startActivity(activity2Intent);
                    }
                });
                buscador.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        list2 = new ArrayList<>();
                        for (int i = 0; i<list1.size();i++){
                            if (list1.get(i).getType().toLowerCase().contains(s.toString().toLowerCase())){
                                list2.add(list1.get(i));
                            }
                        }
                        final USAdapter usAdapter1 = new USAdapter(ServicioListado.this, list2);

                        listView.setAdapter(usAdapter1);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Long idService = list2.get(position).getId();
                                Intent activity2Intent = new Intent(getApplicationContext(), ServicioDetalle.class);
                                activity2Intent.putExtra("idService", idService);
                                startActivity(activity2Intent);
                            }
                        });
                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
            }
            @Override
            public void onFailure(Call<ArrayList<Service>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), String.format("KO"), Toast.LENGTH_SHORT).show();
            }

        });


    }
}
