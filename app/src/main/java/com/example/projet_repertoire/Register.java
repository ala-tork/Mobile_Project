package com.example.projet_repertoire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    EditText email,password;
    TextView pass;
    Button register;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        register=findViewById(R.id.registerbtn);
        pass=findViewById(R.id.pass);
        progressBar = findViewById(R.id.progressBar);
        String e=email.getText().toString().trim();
        String p=password.getText().toString().trim();
        progressBar.setVisibility(View.INVISIBLE);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //verify si les champs et vide et afficher message d'erreur fl edite text
                if(TextUtils.isEmpty(email.getText().toString().trim())){
                    email.setError("Email is required");
                    email.requestFocus();
                }//verifier si l'email et valide ou nn
                else if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()){
                    email.setError("Valid Email is required");
                    email.requestFocus();
                } else if (TextUtils.isEmpty(password.getText().toString().trim())) {
                    password.setError("password is required");
                    password.requestFocus();

                }else{

                    registerUser(email.getText().toString().trim(),password.getText().toString().trim());
                }
            }

        });
        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Register.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
    private void registerUser(String e, String p) {

    }
}