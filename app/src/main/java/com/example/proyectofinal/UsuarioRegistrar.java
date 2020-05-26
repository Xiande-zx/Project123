package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectofinal.clase.User;
import com.google.gson.Gson;

public class UsuarioRegistrar extends AppCompatActivity {
    private String userName;
    private String password;

    private String name;
    private String surname;
    private String phone;
    private Integer age;
    private String poblation;
    private String email;
    private boolean inmune;

    private EditText Rname;
    private EditText Rsurname;
    private EditText Rphone;
    private EditText Rage;
    private EditText Rpoblation;
    private EditText Remail;
    private EditText Rinmune;

    private EditText RuserName;
    private EditText Rpassword;

    Gson gson = new Gson();

    Button detalleUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_registrar);

        RuserName=findViewById(R.id.editText3);
        Rpassword=findViewById(R.id.editText5);
        Rphone=findViewById(R.id.editText6);
        Remail=findViewById(R.id.editText4);

        detalleUsuario = (Button)findViewById(R.id.button3);

        User user = gson.fromJson(getIntent().getStringExtra("userJson"), User.class);
        if(user!=null){
            getIntent().getSerializableExtra("userJson");
            user = gson.fromJson(getIntent().getStringExtra("userJson"), User.class);
            RuserName.setText(user.getUserName());
            Rpassword.setText(user.getPassword());
            Rphone.setText(user.getTelefono());
            Remail.setText(user.getEmail());
        }

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
                    User user = new User(1,userName,password,phone,email,true);
                    Intent intent = new Intent(UsuarioRegistrar.this,UsuarioDetalle.class);
                    startActivity(intent);
                }
            }
        });
    }public void error(String str){
        Context context = getApplicationContext();
        CharSequence text = "El camp "+str+" no pot estar buit!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}
