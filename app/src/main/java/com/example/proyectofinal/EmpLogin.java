package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectofinal.Interface.EmpInterface;
import com.example.proyectofinal.Interface.UserInterface;
import com.example.proyectofinal.clase.Emp;
import com.example.proyectofinal.clase.Service;
import com.example.proyectofinal.clase.User;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmpLogin extends AppCompatActivity {
    Button verServicios,contact;

    EditText username;
    EditText password;
    String userNameStr;
    String passwordStr;
    private static Retrofit retrofit = null;
    ArrayList<Emp> list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_login);
            verServicios= (Button) findViewById(R.id.ELbutton21);
            verServicios.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    username=findViewById(R.id.userName);
                    password=findViewById(R.id.Epassword);
                    userNameStr=username.getText().toString();
                    passwordStr=password.getText().toString();

                    if (userNameStr.isEmpty()){
                        error("User name");
                    }else if (passwordStr.isEmpty()){
                        error("password");
                    }else {

                        if(retrofit==null){
                            retrofit = new Retrofit.Builder()
                                    .baseUrl(getString(R.string.url))
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                        }
                        EmpInterface user = retrofit.create(EmpInterface.class);

                        Call<ArrayList<Emp>> repos = user.listEmp();

                        repos.enqueue(new Callback<ArrayList<Emp>>() {
                            @Override
                            public void onResponse(Call<ArrayList<Emp>> call, Response<ArrayList<Emp>> response) {
                                list = response.body();
                                Boolean has = false;
                                for (int i = 0; i < list.size(); i++ ){
                                    if (userNameStr.equalsIgnoreCase(list.get(i).getUserName())){
                                        has=true;
                                        if (passwordStr.equalsIgnoreCase(list.get(i).getPassword())){
                                            Emp emp = list.get(i);
                                            Intent intent = new Intent(EmpLogin.this, EmpMenu.class);
                                            intent.putExtra("Emp", emp);
                                            startActivity(intent);
                                        }else{
                                            Context context = getApplicationContext();
                                            CharSequence text = "Contrasenya incorrecta!";
                                            int duration = Toast.LENGTH_SHORT;

                                            Toast toast = Toast.makeText(context, text, duration);
                                            toast.show();
                                        }
                                    }
                                }
                                if (has==false){
                                    Context context = getApplicationContext();
                                    CharSequence text = "No existeix l'usuari";
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();
                                }
                            }
                            @Override
                            public void onFailure(Call<ArrayList<Emp>> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), String.format("Time Out"), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });

            contact=findViewById(R.id.ELbutton20);
            contact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent email = new Intent(Intent.ACTION_SEND);
                    email.putExtra(Intent.EXTRA_EMAIL, new String[]{ "Xiandeqiu@gmail.com"});
                    email.putExtra(Intent.EXTRA_SUBJECT, "Create Company Account");
                    email.putExtra(Intent.EXTRA_TEXT, "");

                    //need this to prompts email client only
                    email.setType("message/rfc822");

                    startActivity(Intent.createChooser(email, "Choose an Email client :"));
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
