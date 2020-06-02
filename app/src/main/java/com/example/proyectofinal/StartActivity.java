package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectofinal.Interface.UserInterface;
import com.example.proyectofinal.clase.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StartActivity extends AppCompatActivity {

    Button verServicios;
    Button registrar;
    EditText username;
    EditText password;
    String userNameStr;
    String passwordStr;

    private static Retrofit retrofit = null;
    ArrayList<User> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registrar=findViewById(R.id.button2);


        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StartActivity.this,UsuarioRegistrar.class);
                startActivity(i);
            }
        });
        verServicios= (Button) findViewById(R.id.button);
        verServicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username=findViewById(R.id.editText);
                password=findViewById(R.id.editText2);
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
                    UserInterface user = retrofit.create(UserInterface.class);
                    Call<ArrayList<User>> repos = user.listEmp();

                    repos.enqueue(new Callback<ArrayList<User>>() {
                        @Override
                        public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                            list = response.body();
                            //boolean to know if exist this user
                            Boolean has = false;
                            for (int i = 0; i < list.size(); i++ ){
                                if (userNameStr.equalsIgnoreCase(list.get(i).getUserName())){
                                    //if find, true
                                    has=true;
                                    if (passwordStr.equalsIgnoreCase(list.get(i).getPassword())){
                                        Long idUser = list.get(i).getId();
                                        Intent intent = new Intent(StartActivity.this, UsuarioMenu.class);
                                        intent.putExtra("idUser", idUser);
                                        startActivity(intent);
                                    }else{
                                        Context context = getApplicationContext();
                                        String incorrect = getString(R.string.incorrecta);
                                        CharSequence text = incorrect;
                                        int duration = Toast.LENGTH_SHORT;
                                        Toast toast = Toast.makeText(context, text, duration);
                                        toast.show();
                                    }
                                }
                            }
                            //if false show non exixst
                            if (has==false){
                                Context context = getApplicationContext();
                                String incorrect = getString(R.string.noExiste);
                                CharSequence text = incorrect;
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                            }
                        }
                        @Override
                        public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), String.format("No connection"), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }


    public void error(String str){
        Context context = getApplicationContext();
        CharSequence text = ""+str+" can not be empty!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
