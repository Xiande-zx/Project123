package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectofinal.Interface.EmpInterface;
import com.example.proyectofinal.Interface.UserInterface;
import com.example.proyectofinal.clase.Emp;
import com.example.proyectofinal.clase.User;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsuarioRegistrar extends AppCompatActivity {
    private String userName;
    private String password;
    private String phone;
    private String email;
    private EditText Rphone;
    private EditText Remail;
    private EditText RuserName;
    private EditText Rpassword;

    private static Retrofit retrofit = null;
    User user1;
    User user;
    CheckBox checkBox;
    private TextView createOrEdit;

    Button detalleUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_registrar);

        RuserName=findViewById(R.id.editText3);
        Rpassword=findViewById(R.id.editText5);
        Rphone=findViewById(R.id.editText6);
        Remail=findViewById(R.id.editText4);
        createOrEdit=findViewById(R.id.textView35);

        checkBox = findViewById(R.id.checkBox);
        detalleUsuario = (Button)findViewById(R.id.button3);

        detalleUsuario.setEnabled(false);

        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(getString(R.string.url))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }


        user = (User) getIntent().getSerializableExtra("user");
        if(user!=null){
            createOrEdit.setText("Edit account");
            detalleUsuario.setText("Edit");
            RuserName.setText(user.getUserName());

            Rphone.setText(user.getTelefono());
            Remail.setText(user.getEmail());
        }

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()){
                    detalleUsuario.setEnabled(true);
                }else{
                    detalleUsuario.setEnabled(false);
                }
            }
        });

        detalleUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName=RuserName.getText().toString();
                password=Rpassword.getText().toString();
                phone=Rphone.getText().toString();
                email=Remail.getText().toString();

                if (userName.isEmpty()){
                    error("username");
                }else if (password.isEmpty()){
                    error("password");
                }else if (phone.isEmpty()){
                    error("telefon");
                }else if(email.isEmpty()){
                    error("email");
                }else{
                    UserInterface service = retrofit.create(UserInterface.class);
                    User user2 = new User(userName,password,phone,email,true);

                    if (detalleUsuario.getText().toString().equalsIgnoreCase("create")){
                        Call<User> repos = service.postUser(user2);
                        repos.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                user1 = response.body();
                                Intent intent = new Intent(UsuarioRegistrar.this,UsuarioDetalle.class);
                                intent.putExtra("idUser",user1.getId());
                                startActivity(intent);
                            }
                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), String.format("Timer Out"), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        user.setUserName(userName);
                        user.setPassword(password);
                        user.setTelefono(phone);
                        user.setEmail(email);
                        Call<User> repos = service.putUser(user);
                        repos.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                user1 = response.body();
                                Intent intent = new Intent(UsuarioRegistrar.this,UsuarioDetalle.class);
                                intent.putExtra("idUser",user.getId());
                                startActivity(intent);
                            }
                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), String.format("No connection"), Toast.LENGTH_SHORT).show();
                            }
                        });
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
