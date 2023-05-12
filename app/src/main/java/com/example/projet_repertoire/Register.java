package com.example.projet_repertoire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projet_repertoire.User.DatabaseHelperUser;
import com.example.projet_repertoire.User.User;

import java.util.List;

public class Register extends AppCompatActivity {
    //private DatabaseHelper databaseHelper;
    EditText username, email, password;
    TextView pass;
    Button register, login;
    ProgressBar progressBar;

    DatabaseHelperUser db = new DatabaseHelperUser(this);
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = findViewById(R.id.username);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.btnsignup);
        login = findViewById(R.id.btnsignin);
        //pass=findViewById(R.id.pass);
        //progressBar = findViewById(R.id.progressBar);
        String e = email.getText().toString().trim();
        String p = password.getText().toString().trim();
        String u=username.getText().toString().trim();
//        progressBar.setVisibility(View.INVISIBLE);

        // get all users from the database
        /*
        List<User> userList = db.getAllUsers();
        for (User user : userList) {
            Toast.makeText(this, user.getName() + "   " + user.getId(), Toast.LENGTH_SHORT).show();
        }*/

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //verify si les champs et vide et afficher message d'erreur fl edite text
                if (TextUtils.isEmpty(email.getText().toString().trim())) {
                    email.setError("Email is required");
                    email.requestFocus();
                }//verifier si l'email et valide ou nn
                else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()) {
                    email.setError("Valid Email is required");
                    email.requestFocus();
                } else if (TextUtils.isEmpty(email.getText().toString().trim())) {
                    username.setError("User Name is required");
                    username.requestFocus();
                } else if (TextUtils.isEmpty(password.getText().toString().trim())) {
                    password.setError("password is required");
                    password.requestFocus();

                } else {
                    user = new User(1, username.getText().toString(), email.getText().toString(),password.getText().toString());
                    Toast.makeText(Register.this, user.getEmail(), Toast.LENGTH_SHORT).show();
                    long res=db.addUser(email.getText().toString(),username.getText().toString(),password.getText().toString());
                    if(res==-1){
                        Toast.makeText(Register.this, "Faild to register", Toast.LENGTH_SHORT).show();
                    }else{
                        Intent i = new Intent(Register.this,Login.class);
                        startActivity(i);
                    }
                    //List<User> users = db.getAllUsers();
                    //registerUser(email.getText().toString().trim(),password.getText().toString().trim());
                }
            }

        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Register.this,Login.class);
                startActivity(i);
                finish();
            }
        });
        /*pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Register.this,MainActivity.class);
                startActivity(i);
            }
        });
    }*/

    }
}