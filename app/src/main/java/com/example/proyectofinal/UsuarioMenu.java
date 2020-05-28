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

import com.example.proyectofinal.UserAdapter.USAdapter;
import com.example.proyectofinal.clase.Service;
import com.example.proyectofinal.clase.User;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsuarioMenu extends AppCompatActivity {
    Button detalleUsuario, empresaServicios;
    EditText buscador;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_menu);

        buscador =(EditText) findViewById(R.id.editText7);

        detalleUsuario =(Button)findViewById(R.id.button7);
        empresaServicios =(Button)findViewById(R.id.button6);
        listView=findViewById(R.id.lista);

        Intent myIntent = getIntent();
        getIntent().getSerializableExtra("userJson");
        Gson gson = new Gson();
        final User user = gson.fromJson(getIntent().getStringExtra("userJson"), User.class);

        final String userStr = getIntent().getStringExtra("userJson");

        detalleUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UsuarioDetalle.class);
                intent.putExtra("userJson", userStr);
                startActivity(intent);
            }
        });

        empresaServicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EmpresaListado.class);
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

        final ArrayList<Service> list  = new ArrayList<Service>(Arrays.asList(new Gson().fromJson(json, Service[].class)));

        USAdapter usAdapter = new USAdapter(this,list);

        listView.setAdapter(usAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Service service = new Service(list.get(position).getId(),list.get(position).getType(),list.get(position).getDescription(),list.get(position).getIdEmp());
                Gson gson = new Gson();
                String serviceJson = gson.toJson(service);

                Intent activity2Intent = new Intent(getApplicationContext(), ServicioDetalle.class);
                activity2Intent.putExtra("serviceJson", serviceJson);
                startActivity(activity2Intent);

            }
        });
        buscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<Service> list1 = new ArrayList<>();
                for (int i = 0; i<list.size();i++){
                    if (list.get(i).getType().toLowerCase().contains(s.toString().toLowerCase())){
                        list1.add(list.get(i));
                    }
                }

                final USAdapter usAdapter1 = new USAdapter(UsuarioMenu.this, list1);

                listView.setAdapter(usAdapter1);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
