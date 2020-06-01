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
import com.example.proyectofinal.Interface.ServiceInterface;
import com.example.proyectofinal.UserAdapter.USAdapter;
import com.example.proyectofinal.clase.Service;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsuarioMenu extends AppCompatActivity {
    Button detalleUsuario;
    EditText buscador;
    private ListView listView;
    ArrayList<Service> list;
    ArrayList<Service> list1;
    private static Retrofit retrofit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_menu);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Service List");

        buscador =(EditText) findViewById(R.id.editText7);

        listView=findViewById(R.id.lista);

        final Long idUser = getIntent().getLongExtra("idUser",0);
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setSelectedItemId(R.id.home1);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.empresa1:
                        Intent intent = new Intent(getApplicationContext(), EmpresaListado.class);
                        intent.putExtra("idUser", idUser);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home1:
                        return true;
                }
                return false;
            }
        });
        detalleUsuario=findViewById(R.id.button7);

        detalleUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UsuarioDetalle.class);
                intent.putExtra("idUser", idUser);
                startActivity(intent);
            }
        });



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
                list = response.body();
                USAdapter usAdapter = new USAdapter(UsuarioMenu.this,list);

                listView.setAdapter(usAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Long idService = list.get(position).getId();
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
                        list1 = new ArrayList<>();
                        for (int i = 0; i<list.size();i++){
                            if (list.get(i).getType().toLowerCase().contains(s.toString().toLowerCase())){
                                list1.add(list.get(i));
                            }
                        }
                        final USAdapter usAdapter1 = new USAdapter(UsuarioMenu.this, list1);
                        listView.setAdapter(usAdapter1);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Long idService = list1.get(position).getId();
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
