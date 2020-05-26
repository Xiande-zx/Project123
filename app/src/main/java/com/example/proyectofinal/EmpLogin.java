package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectofinal.clase.Emp;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class EmpLogin extends AppCompatActivity {
    Button verServicios;

    EditText username;
    EditText password;
    String userNameStr;
    String passwordStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_login);

            verServicios= (Button) findViewById(R.id.button);
            verServicios.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Boolean has = false;

                    username=findViewById(R.id.userName);
                    password=findViewById(R.id.Epassword);

                    userNameStr=username.getText().toString();
                    passwordStr=password.getText().toString();

                    if (userNameStr.isEmpty()){
                        error("User name");
                    }else if (passwordStr.isEmpty()){
                        error("password");
                    }else {
                        String json ="";

                        try {
                            InputStream stream = getAssets().open("Emp.json");
                            int size = stream.available();
                            byte[] buffer = new byte[size];
                            stream.read(buffer);
                            stream.close();
                            json  = new String(buffer);
                        } catch (Exception e) { }

                        ArrayList<Emp> listUser  = new ArrayList<Emp>(Arrays.asList(new Gson().fromJson(json, Emp[].class)));

                        for (int i = 0; i < listUser.size(); i++ ){
                            if (userNameStr.equalsIgnoreCase(listUser.get(i).getUserName())){
                                has=true;
                                if (passwordStr.equalsIgnoreCase(listUser.get(i).getPassword())){

                                    Emp emp  = listUser.get(i);

                                    Gson gson = new Gson();
                                    String userJson = gson.toJson(emp);

                                    Intent intent = new Intent(EmpLogin.this, EmpMenu.class);
                                    intent.putExtra("userJson", userJson);
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
