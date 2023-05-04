package com.example.projet_repertoire;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdatePerson extends AppCompatActivity {
    EditText id,name,phoneNumber,email,facebook;
    Button update;
    String pid,pname,pphone,pemail,pfacebook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_person);
        name=findViewById(R.id.UPName);
        phoneNumber=findViewById(R.id.UPPhone);
        email=findViewById(R.id.UPEmail);
        facebook=findViewById(R.id.UPFacebook);

        update=findViewById(R.id.UpdatePerson);

        //we should get data first
        getDataFromIntent();

        ActionBar bar=getSupportActionBar();
        if(bar!=null){
            bar.setTitle(pname);
        }

        //update data
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //update data using the methode from MyDataBaseHelper class
                MyDataBaseHelper db=new MyDataBaseHelper(UpdatePerson.this);
                long res =db.updatePerson(pid, name.getText().toString().trim(),
                        Integer.valueOf(phoneNumber.getText().toString().trim()),
                        email.getText().toString().trim(),facebook.getText().toString().trim());
                if (res==-1){
                    Toast.makeText(UpdatePerson.this, "Failde to Update Person", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(UpdatePerson.this, "Person update successful ", Toast.LENGTH_SHORT).show();
                    finish();

                }
            }
        });
/*
        //delete person
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call the dialog message to confirm
                Confirm();
            }
        });
*/
    }

    // get data sendit with intent
    void getDataFromIntent (){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("Name") &&
                getIntent().hasExtra("PhoneNumber") && getIntent().hasExtra("Email")
                && getIntent().hasExtra("Facebook")){
            pid=getIntent().getStringExtra("id");
            pname=getIntent().getStringExtra("Name");
            pphone=getIntent().getStringExtra("PhoneNumber");
            pemail=getIntent().getStringExtra("Email");
            pfacebook=getIntent().getStringExtra("Facebook");
            //set update view data
            name.setText(pname);
            email.setText(pemail);
            phoneNumber.setText(pphone);
            facebook.setText(pfacebook);
        }else {
            Toast.makeText(this, "Ther is No data to update", Toast.LENGTH_SHORT).show();
        }
    }
/*
    //show poopup dialog to confirm the delete
    void Confirm(){
        AlertDialog.Builder build= new AlertDialog.Builder(this);
        build.setTitle("Delete " +name.getText().toString()+" ?");
        build.setMessage("Are you sure you want to delete this Person "+name.getText().toString());
        build.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDataBaseHelper db = new MyDataBaseHelper(UpdatePerson.this);
                db.deleteOnPerson(pid);
                //close main activty and return to previos activity
                finish();
            }
        });
        build.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        build.create().show();

    }
*/
}