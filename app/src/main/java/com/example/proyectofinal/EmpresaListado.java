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

import com.example.proyectofinal.UserAdapter.EmpAdapter;
import com.example.proyectofinal.clase.Emp;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class EmpresaListado extends AppCompatActivity {

    Button verDetalleUsuario;
    ListView listView;
    EditText buscador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa_listado);
        verDetalleUsuario=(Button)findViewById(R.id.button3);
        buscador =(EditText) findViewById(R.id.editText8);
        listView=findViewById(R.id.listEmp);

        String json ="";

        try {
            InputStream stream = getAssets().open("Emp.json");
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            json  = new String(buffer);
        } catch (Exception e) { }

        final ArrayList<Emp> list  = new ArrayList<Emp>(Arrays.asList(new Gson().fromJson(json, Emp[].class)));

        EmpAdapter empAdapter = new EmpAdapter(this,list);

        listView.setAdapter(empAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Integer temp = list.get(position).getId();

                Intent activity2Intent = new Intent(getApplicationContext(), EmpresaDetalle.class);
                activity2Intent.putExtra("idEmp", temp);
                startActivity(activity2Intent);

            }
        });

        buscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<Emp> list1 = new ArrayList<>();
                for (int i = 0; i<list.size();i++){
                    if (list.get(i).getName().contains(s.toString())){
                        list1.add(list.get(i));
                    }
                }

                final EmpAdapter EmpAdapter1 = new EmpAdapter(EmpresaListado.this, list1);

                listView.setAdapter(EmpAdapter1);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        verDetalleUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UsuarioDetalle.class);
                startActivity(intent);
            }
        });
    }
}
