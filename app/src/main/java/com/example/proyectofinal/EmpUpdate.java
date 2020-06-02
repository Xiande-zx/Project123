package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectofinal.Interface.EmpInterface;
import com.example.proyectofinal.Interface.UserInterface;
import com.example.proyectofinal.clase.Emp;
import com.example.proyectofinal.clase.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmpUpdate extends AppCompatActivity {

    private static Retrofit retrofit = null;

    EditText nom,username,password,phone,description,email,adress;
    String nom1,username1,password1,phone1,description1,email1,adress1;
    Button confirm;
    Emp user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_update);

        nom=findViewById(R.id.textView20);
        password=findViewById(R.id.textView39);
        phone=findViewById(R.id.textView24);
        description=findViewById(R.id.textView42);
        email=findViewById(R.id.textView25);
        adress=findViewById(R.id.textView26);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Update Ccompany");

        confirm=findViewById(R.id.button10);

        user = (Emp) getIntent().getSerializableExtra("Emp");

        nom.setText(user.getName());
        phone.setText(user.getNumber().toString());
        description.setText(user.getDescription());
        email.setText(user.getEmail());
        adress.setText(user.getAdress());

        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(getString(R.string.url))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nom1=nom.getText().toString();
                password1=password.getText().toString();
                phone1=phone.getText().toString();
                description1=description.getText().toString();
                email1=email.getText().toString();
                adress1=adress.getText().toString();

                user.setName(nom1);
                user.setPassword(password1);
                user.setNumber(Integer.valueOf(phone1));
                user.setDescription(description1);
                user.setEmail(email1);
                user.setAdress(adress1);

                EmpInterface service = retrofit.create(EmpInterface.class);
                Call<Emp> repos = service.postEmp(user);
                repos.enqueue(new Callback<Emp>() {
                    @Override
                    public void onResponse(Call<Emp> call, Response<Emp> response) {
                        Emp user1 = response.body();
                        Intent intent = new Intent(EmpUpdate.this,EmpEmpDetalle.class);
                        intent.putExtra("idEmp",user.getId());
                        intent.putExtra("Emp",user);
                        startActivity(intent);
                    }
                    @Override
                    public void onFailure(Call<Emp> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), String.format("No connection"), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }
}
