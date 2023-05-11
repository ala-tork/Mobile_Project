package com.example.projet_repertoire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPerson extends AppCompatActivity {

    EditText name, phonenumber,email,facebook;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
        name =findViewById(R.id.UPName);
        phonenumber = findViewById(R.id.UPPhone);
        email = findViewById(R.id.UPEmail);
        facebook=findViewById(R.id.UPFacebook);
        save = findViewById(R.id.UpdatePerson);

        //add a book
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //instance of the class that help me to manipulate data
                MyDataBaseHelper db = new MyDataBaseHelper(AddPerson.this);
                if(TextUtils.isEmpty(name.getText().toString().trim())){
                    name.setError("name is required");
                    name.requestFocus();
                }else if(TextUtils.isEmpty(phonenumber.getText().toString().trim())){
                    phonenumber.setError("Phone Number Is required");
                    phonenumber.requestFocus();
                } else if (!TextUtils.isDigitsOnly(phonenumber.getText().toString())) {
                    phonenumber.setError("Phone Number should have only Digit ");
                    phonenumber.requestFocus();
                }else{
                    long res = -1;
                    //execute Save From DataBaseHelper class
                    if(TextUtils.isEmpty(facebook.getText().toString().trim())){
                        res=db.SavePerson(
                                name.getText().toString().trim()
                                ,Integer.valueOf(phonenumber.getText().toString().trim()),
                                email.getText().toString().trim(),
                                facebook.getText().toString().trim()
                        );
                    }else{

                        res=db.SavePerson(
                                name.getText().toString().trim()
                                ,Integer.valueOf(phonenumber.getText().toString().trim()),
                                email.getText().toString().trim(),
                                "https://www.facebook.com/"+facebook.getText().toString().trim()
                        );
                    }

                    if(res==-1){
                        Toast.makeText(AddPerson.this, "Failde To Save", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(AddPerson.this, "Sucessful Save", Toast.LENGTH_SHORT).show();
                        //finish();
                        Intent i = new Intent(AddPerson.this,MainActivity.class);
                        startActivity(i);

                    }
                }

            }
        });
    }
}