package com.example.projet_repertoire;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PersonDetails extends AppCompatActivity {
    TextView name,phoneNumber,email,facebook;
    Button update,persondelete;
    String Did,Dname,Dphone,Demail,Dfacebook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_details);
        name=findViewById(R.id.DName);
        phoneNumber=findViewById(R.id.DPhone);
        email=findViewById(R.id.Demail);
        facebook=findViewById(R.id.DFacebook);

        update=findViewById(R.id.Dupdate);
        persondelete =findViewById(R.id.Dpdelete);

        //we should get data first
        getDataFromIntent();

        ActionBar bar=getSupportActionBar();
        if(bar!=null){
            bar.setTitle(Dname);
        }
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent for Update view
                Intent i = new Intent(PersonDetails.this,UpdatePerson.class);
                //send data to update view
                i.putExtra("id",Did);
                i.putExtra("Name",Dname);
                i.putExtra("PhoneNumber",Dphone);
                i.putExtra("Email",Demail);
                i.putExtra("Facebook",Dfacebook);
                //return result to main activity to refrech data
                startActivityForResult(i  ,1);
                finish();
            }
        });
        persondelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Confirm();
            }
        });

    }

    private void getDataFromIntent() {
        if(getIntent().hasExtra("id") && getIntent().hasExtra("Name") &&
                getIntent().hasExtra("PhoneNumber") && getIntent().hasExtra("Email")
                && getIntent().hasExtra("Facebook")){

            Did=getIntent().getStringExtra("id");
            Dname=getIntent().getStringExtra("Name");
            Dphone=getIntent().getStringExtra("PhoneNumber");
            Demail=getIntent().getStringExtra("Email");
            Dfacebook=getIntent().getStringExtra("Facebook");
            //set update view data
            name.setText(Dname);
            email.setText(Demail);
            phoneNumber.setText(Dphone);
            facebook.setText(Dfacebook);
        }else {
            Toast.makeText(this, "Ther is No data to update", Toast.LENGTH_SHORT).show();
        }
    }


    void Confirm(){
        AlertDialog.Builder build= new AlertDialog.Builder(this);
        build.setTitle("Delete " +name.getText().toString()+" ?");
        build.setMessage("Are you sure you want to delete this Person "+name.getText().toString());
        build.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDataBaseHelper db = new MyDataBaseHelper(PersonDetails.this);
                db.deleteOnPerson(Did);
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
}