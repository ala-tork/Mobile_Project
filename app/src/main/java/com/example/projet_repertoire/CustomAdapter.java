package com.example.projet_repertoire;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    Activity activity;
    ArrayList id_Persons,Names,PhoneNumbers,Emails,Facebooks;

    CustomAdapter(Context context,Activity activity,ArrayList id_Persons,ArrayList Names,
                  ArrayList PhoneNumbers,ArrayList Emails , ArrayList Facebooks){
        this.context=context;
        this.activity=activity;
        this.id_Persons=id_Persons;
        this.Names=Names;
        this.PhoneNumbers=PhoneNumbers;
        this.Emails=Emails;
        this.Facebooks=Facebooks;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_one_person,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.id.setText(String.valueOf(id_Persons.get(position)));
        holder.name.setText(String.valueOf(Names.get(position)));
        holder.phonenumber.setText(String.valueOf(PhoneNumbers.get(position)));

        //redirect to detail whene click on the item
       holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent for detail view
                Intent i = new Intent(context,PersonDetails.class);
                //send data to update view
                i.putExtra("id",String.valueOf(id_Persons.get(position)));
                i.putExtra("Name",String.valueOf(Names.get(position)));
                i.putExtra("PhoneNumber",String.valueOf(PhoneNumbers.get(position)));
                i.putExtra("Email",String.valueOf(Emails.get(position)));
                i.putExtra("Facebook",String.valueOf(Facebooks.get(position)));
                //return result to main activity to refrech data
                activity.startActivityForResult(i  ,1);
            }
        });
    }

    @Override
    public int getItemCount() {

        return id_Persons.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView id,name,phonenumber;
        FloatingActionButton call;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.idPerson);
            name=itemView.findViewById(R.id.PersonName);
            phonenumber=itemView.findViewById(R.id.PhoneNumber);
            call=itemView.findViewById(R.id.call);
            mainLayout = itemView.findViewById(R.id.mainLayout);

            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String x=phonenumber.getText().toString().trim();
                    Toast.makeText(activity, x+"", Toast.LENGTH_SHORT).show();
                    Intent makecall = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+Uri.encode(x)));


                    activity.startActivity(makecall);
                }
            });

        }
    }
}
