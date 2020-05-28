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

import com.example.proyectofinal.Interface.UserInterface;
import com.example.proyectofinal.UserAdapter.EmpAdapter;
import com.example.proyectofinal.clase.Emp;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmpresaListado extends AppCompatActivity {

    Button verDetalleUsuario;
    ListView listView;
    EditText buscador;

    ArrayList<Emp> list;

    private static Retrofit retrofit = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa_listado);
        verDetalleUsuario=(Button)findViewById(R.id.button8);
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

        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://ec2-54-160-74-68.compute-1.amazonaws.com:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        UserInterface service = retrofit.create(UserInterface.class);

        Call<ArrayList<Emp>> repos = service.listEmp();

        repos.enqueue(new Callback<ArrayList<Emp>>() {
            @Override
            public void onResponse(Call<ArrayList<Emp>> call, Response<ArrayList<Emp>> response) {
                Toast.makeText(getApplicationContext(), String.format("OK"), Toast.LENGTH_SHORT).show();
                list = response.body();

            }

            @Override
            public void onFailure(Call<ArrayList<Emp>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), String.format("KO"), Toast.LENGTH_SHORT).show();
            }


        });

        //final ArrayList<Emp> list  = new ArrayList<Emp>(Arrays.asList(new Gson().fromJson(json, Emp[].class)));


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
                    if (list.get(i).getName().toLowerCase().contains(s.toString().toLowerCase())){
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
