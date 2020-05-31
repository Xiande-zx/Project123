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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

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

public class EmpMenu extends AppCompatActivity {


    Button detalleUsuario;
    Button add;

    private ListView listView;
    ArrayList<Service> list;
    ArrayList<Service> list2;
    USAdapter usAdapter;
    ArrayList<Service> list1;
    private static Retrofit retrofit = null;
    EditText search;
    Long idEmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_menu);

        detalleUsuario =findViewById(R.id.button11);
        search=findViewById(R.id.buscador1);
        listView=findViewById(R.id.lista1);

        final Emp emp = (Emp) getIntent().getSerializableExtra("Emp");
        idEmp = emp.getId();

        add=findViewById(R.id.button16);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(EmpMenu.this,AddServicio.class);
                intent.putExtra("Emp", emp);
                startActivity(intent);
            }
        });

        detalleUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EmpEmpDetalle.class);
                intent.putExtra("idEmp", emp.getId());
                startActivity(intent);
            }
        });



        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(getString(R.string.url))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        ServiceInterface service1 = retrofit.create(ServiceInterface.class);

        Call<ArrayList<Service>> repos = service1.listService();

        repos.enqueue(new Callback<ArrayList<Service>>() {
            @Override
            public void onResponse(Call<ArrayList<Service>> call, Response<ArrayList<Service>> response) {
                list = response.body();
                list2 = new ArrayList<>();

                for (int z = 0; z < list.size(); z++) {
                    if (list.get(z).getIdEmp().equals(idEmp)) {
                        list2.add(list.get(z));
                    }
                }
                    usAdapter = new USAdapter(EmpMenu.this, list2);

                    listView.setAdapter(usAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Long idService = list2.get(position).getId();
                        Intent activity2Intent = new Intent(getApplicationContext(), EmpServicioDetalle.class);
                        activity2Intent.putExtra("idService", idService);
                        activity2Intent.putExtra("Emp",emp);
                        startActivity(activity2Intent);
                    }
                });

                    search.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            list1 = new ArrayList<>();
                            for (int i = 0; i < list2.size(); i++) {
                                if (list2.get(i).getType().toLowerCase().contains(s.toString().toLowerCase())) {
                                    list1.add(list2.get(i));
                                }
                            }
                            final USAdapter usAdapter1 = new USAdapter(EmpMenu.this, list1);
                            listView.setAdapter(usAdapter1);

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Long idService = list1.get(position).getId();
                                    Intent activity2Intent = new Intent(getApplicationContext(), EmpServicioDetalle.class);
                                    activity2Intent.putExtra("idService", idService);
                                    activity2Intent.putExtra("Emp",emp);
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
                Toast.makeText(getApplicationContext(), String.format("Time Out"), Toast.LENGTH_SHORT).show();
            }
        });
        }
    }

