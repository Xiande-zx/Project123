package com.example.proyectofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectofinal.Interface.EmpInterface;
import com.example.proyectofinal.UserAdapter.EmpAdapter;
import com.example.proyectofinal.clase.Emp;
import com.example.proyectofinal.clase.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

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

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Company List");

        final Long idUser = getIntent().getLongExtra("idUser",0);

        //bottom naviga
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setSelectedItemId(R.id.empresa1);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.empresa1:
                        return true;
                    case R.id.home1:
                        Intent intent = new Intent(getApplicationContext(), UsuarioMenu.class);
                        intent.putExtra("idUser", idUser);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(getString(R.string.url))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        EmpInterface service = retrofit.create(EmpInterface.class);

        Call<ArrayList<Emp>> repos = service.listEmp();

        repos.enqueue(new Callback<ArrayList<Emp>>() {
            @Override
            public void onResponse(Call<ArrayList<Emp>> call, Response<ArrayList<Emp>> response) {
                list = response.body();

                EmpAdapter empAdapter = new EmpAdapter(EmpresaListado.this,list);

                listView.setAdapter(empAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Long temp = list.get(position).getId();
                        intent(temp);
                    }
                });

                buscador.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        final ArrayList<Emp> list1 = new ArrayList<>();
                        for (int i = 0; i<list.size();i++){
                            if (list.get(i).getName().toLowerCase().contains(s.toString().toLowerCase())){
                                list1.add(list.get(i));
                            }
                        }
                        final EmpAdapter EmpAdapter1 = new EmpAdapter(EmpresaListado.this, list1);
                        listView.setAdapter(EmpAdapter1);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Long temp = list1.get(position).getId();
                                intent(temp);
                            }
                        });
                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
            }
            @Override
            public void onFailure(Call<ArrayList<Emp>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), String.format("No connection"), Toast.LENGTH_SHORT).show();
            }
        });

        verDetalleUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UsuarioDetalle.class);
                intent.putExtra("idUser", idUser);
                startActivity(intent);
            }
        });
    }

    private void intent(Long temp){
        Intent activity2Intent = new Intent(getApplicationContext(), EmpresaDetalle.class);
        activity2Intent.putExtra("idEmp", temp);
        startActivity(activity2Intent);
    }
}
