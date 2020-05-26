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

import com.example.proyectofinal.UserAdapter.USAdapter;
import com.example.proyectofinal.clase.Emp;
import com.example.proyectofinal.clase.Service;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class EmpMenu extends AppCompatActivity {


    Button detalleUsuario;
    Button add;

    private ListView listView;
    ArrayList<Service> list;
    ArrayList<Service> list2;
    USAdapter usAdapter;

    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_menu);

        detalleUsuario =findViewById(R.id.button11);
        search=findViewById(R.id.buscador1);
        listView=findViewById(R.id.lista1);

        add=findViewById(R.id.button16);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(EmpMenu.this,AddServicio.class);
                startActivity(intent);
            }
        });


        Intent myIntent = getIntent();
        getIntent().getSerializableExtra("userJson");
        Gson gson = new Gson();
        final Emp emp = gson.fromJson(getIntent().getStringExtra("userJson"), Emp.class);

        final String userStr = getIntent().getStringExtra("userJson");

        detalleUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EmpEmpDetalle.class);
                intent.putExtra("userJson", userStr);
                startActivity(intent);
            }
        });

        String json ="";

        try {
            InputStream stream = getAssets().open("ServiceList.json");
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            json  = new String(buffer);
        } catch (Exception e) { }

        list  = new ArrayList<Service>(Arrays.asList(new Gson().fromJson(json, Service[].class)));
        list2 = new ArrayList<>();
        for (int z = 0 ; z<list.size();z++) {
            if (list.get(z).getIdEmp()==emp.getId()){
                list2.add(list.get(z));
            }


            usAdapter = new USAdapter(this, list2);

            listView.setAdapter(usAdapter);

            final String finalJson = json;
            search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    ArrayList<Service> list1 = new ArrayList<>();
                    for (int i = 0; i<list2.size();i++){
                        if (list2.get(i).getType().toLowerCase().contains(s.toString().toLowerCase())){
                            list1.add(list2.get(i));
                        }
                    }

                    final USAdapter usAdapter1 = new USAdapter(EmpMenu.this, list1);

                    listView.setAdapter(usAdapter1);

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Service service = new Service(list.get(position).getId(),list.get(position).getType(),list.get(position).getDescription(),list.get(position).getIdEmp());
                    Gson gson = new Gson();
                    String serviceJson = gson.toJson(service);

                    Intent activity2Intent = new Intent(getApplicationContext(), EmpServicioDetalle.class);
                    activity2Intent.putExtra("serviceJson", serviceJson);
                    startActivity(activity2Intent);

                }
            });

        }
    }
}
