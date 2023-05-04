package com.example.projet_repertoire;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recycle;
    FloatingActionButton addbtn;
    ImageView img;
    TextView t;
    //instancer of MyDataBaseHelper class to use db
    MyDataBaseHelper db;
    ArrayList<String> idPerson,Names,PhoneNumbers,Emails,Facebooks;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycle = findViewById(R.id.recycleview);
        img=findViewById(R.id.empty_image);
        t=findViewById(R.id.empty_text);
        addbtn= findViewById(R.id.addBtn);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddPerson.class);
                startActivity(i);
            }
        });
        //inisializ instance
        db=new MyDataBaseHelper(MainActivity.this);
        idPerson = new ArrayList<>();
        Names = new ArrayList<>();
        PhoneNumbers = new ArrayList<>();
        Emails = new ArrayList<>();
        Facebooks= new ArrayList<>();

        //replire les liste avec data
        DisplayData();

        //t5alini na3mel custtomiz ll child  mte3i eli bech nafichehom
        customAdapter=new CustomAdapter(MainActivity.this,this,idPerson,Names,PhoneNumbers,Emails,Facebooks);
        recycle.setAdapter(customAdapter);
        recycle.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    //refrech the main activity after update
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1){
            recreate();
        }
    }

    //get data from data base and put them in arrays
    void DisplayData (){
        Cursor c =db.GetAllPersons();
        if(c.getCount()==0){

            //if we dont have data we will show the image
            img.setVisibility(View.VISIBLE);
            t.setVisibility(View.VISIBLE);
            //Toast.makeText(this, "Ther is noo Books", Toast.LENGTH_SHORT).show();

        }else{
            while (c.moveToNext()){
                //na9ra el data mta3 2awel column
                idPerson.add(c.getString(0));
                Names.add(c.getString(1));
                PhoneNumbers.add(c.getString(2));
                Emails.add(c.getString(3));
                Facebooks.add(c.getString(4));
            }
            //if we have data we will hide items
            img.setVisibility(View.GONE);
            t.setVisibility(View.GONE);
        }
    }

    // add the menue to top bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.new_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    //verify wich item of menu get selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.DeleteAll){
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    // confrim dialog to delte all Persons
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to delete all Persons?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDataBaseHelper myDB = new MyDataBaseHelper(MainActivity.this);
                myDB.DeleteAllPersons();
                //Refresh Activity
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

}