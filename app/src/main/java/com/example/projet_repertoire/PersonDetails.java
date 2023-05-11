package com.example.projet_repertoire;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.InetAddresses;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PersonDetails extends AppCompatActivity {
    AlertDialog dialog;
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

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(facebook.getText().toString().trim()));
                startActivity(i);
            }
        });
        phoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String x=phoneNumber.getText().toString().trim();
                String n= name.getText().toString().trim();
                choisir(x,n);
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail=email.getText().toString().trim();
                SendEmail(mail);
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
    //call or send a message
    void choisir(String x,String n){

        AlertDialog.Builder b = new AlertDialog.Builder(PersonDetails.this);

        b.setTitle("Call/SMS");
        b.setMessage("do you wana to call or to send SMS to "+n);
        b.setPositiveButton("Call", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent makecall = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+Uri.encode(x)));


                startActivity(makecall);
            }
        });
        b.setNegativeButton("SMS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //function make a customise dialg to send a message
                Sms(x ,n);
            }
        });
        b.create().show();
    }
    // sending message with customise AlertDialog
    public void Sms (String x,String n){
        AlertDialog.Builder b= new AlertDialog.Builder(this);
        //create the Customise dialog view using inflater
        View v = getLayoutInflater().inflate(R.layout.activity_custom_dialog,null);
        //set the new view to dialog
        b.setView(v);
        dialog = b.create();

        EditText message;
        TextView title;

        title=v.findViewById(R.id.SmsTitle);
        message=v.findViewById(R.id.MessageBody);

        title.setText("Send Message To "+n);
        b.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(TextUtils.isEmpty(message.getText().toString().trim())){
                    Toast.makeText(PersonDetails.this, "Pleas type your message", Toast.LENGTH_SHORT).show();
                }else{
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(x, null, message.getText().toString().trim(), null, null);
                    Toast.makeText( PersonDetails.this, "SMS send With succes", Toast.LENGTH_SHORT).show();
                }
            }
        });
        b.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });
        b.show();
    }


    public void SendEmail(String mail){
        AlertDialog.Builder b= new AlertDialog.Builder(this);
        //create the Customise dialog view using inflater
        View v = getLayoutInflater().inflate(R.layout.email_dialog,null);
        //set the new view to dialog
        b.setView(v);
        dialog = b.create();

        EditText EmailObject,EmailMessage;
        Button Cancel,SendEmail;

        EmailObject=v.findViewById(R.id.EmailObject);
        EmailMessage=v.findViewById(R.id.EmailMessage);
        Cancel= v.findViewById(R.id.Cancel);
        SendEmail=v.findViewById(R.id.SendEmail);

        String Objet=EmailObject.getText().toString().trim();
        String Message=SendEmail.getText().toString().trim();
        b.show();
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        SendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse("mailto:"+mail));
                i.putExtra(Intent.EXTRA_SUBJECT,Objet);
                i.putExtra(Intent.EXTRA_TEXT,Message);
                startActivity(i);
                finish();
            }
        });
    }

}