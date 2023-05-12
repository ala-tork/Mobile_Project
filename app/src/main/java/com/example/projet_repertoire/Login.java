package com.example.projet_repertoire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.projet_repertoire.User.DatabaseHelperUser;
import com.example.projet_repertoire.User.User;

public class Login extends AppCompatActivity {

    EditText username, password;
    TextView register;
    Button btnlogin;
    User user ;
    DatabaseHelperUser db = new DatabaseHelperUser(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        register=findViewById(R.id.register);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        btnlogin = (Button) findViewById(R.id.btnsignin1);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    user= db.VerifUser(username.getText().toString(),password.getText().toString());
                    if (user==null){
                        Toast.makeText(Login.this, "Ther is no user with this name ", Toast.LENGTH_SHORT).show();
                    }else{
                        Intent i = new Intent(Login.this,MainActivity.class);
                        startActivity(i);
                    }

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this,Register.class);
                startActivity(i);

            }
        });
    }
}