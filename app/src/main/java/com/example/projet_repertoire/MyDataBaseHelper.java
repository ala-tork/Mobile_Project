package com.example.projet_repertoire;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.sql.Connection;
import java.util.ArrayList;

public class MyDataBaseHelper extends SQLiteOpenHelper {
    private Context context;
    private final static String DataBaseName="Repertoir.db";
    private final static int DataBaseVersion =1;
    private final static String TableName="repertoir";
    private final static String Column_ID="id";
    private final static String Column_Name ="person_name";
    private final static String Column_PhoneNumber ="phone_number";
    private final static String Column_Email="email";
    private final static String Column_Facebook="facebook";


    public MyDataBaseHelper(@Nullable Context context) {

        super(context, DataBaseName, null, DataBaseVersion);
        this.context=context;
    }

    //create the data base
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query=
                "CREATE TABLE " + TableName +
                        " ( " + Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Column_Name + " TEXT, " +
                        Column_PhoneNumber + " INTEGER ," +
                        Column_Email + " TEXT ," +
                         Column_Facebook + " TEXT );";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TableName);
        onCreate(db);
    }
    //methode to save a Person into data base
    public long SavePerson (String Name, int PhoneNumber , String Email , String Facebook) {
        SQLiteDatabase db = this.getWritableDatabase();
        //collect data
        ContentValues data = new ContentValues();
        data.put(Column_Name,Name);
        data.put(Column_PhoneNumber,PhoneNumber);
        data.put(Column_Email,Email);
        data.put(Column_Facebook,Facebook);

        //save data
        long res =db.insert(TableName,null, data);
        return res;
    }

    //Get All persons
    Cursor GetAllPersons(){
        String query = "SELECT * FROM " + TableName;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    //update data
    public long updatePerson(String id , String Name, int PhoneNumber , String Email , String Facebook){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues c =new ContentValues();
        c.put(Column_Name,Name);
        c.put(Column_PhoneNumber,PhoneNumber);
        c.put(Column_Email,Email);
        c.put(Column_Facebook,Facebook);
        long res=db.update(TableName,c,"id=?", new String[]{id});
        return res;
    }
    void deleteOnPerson(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        long res = db.delete(TableName,"id=?",new String[]{id});
        if(res==-1){
            Toast.makeText(context, "Failde to delete person", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "person with id : "+id+" deleted successfuly", Toast.LENGTH_SHORT).show();
        }
    }
    void DeleteAllPersons(){
        SQLiteDatabase db =this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TableName );
    }
    /*
    Cursor getOnePerson(String id){
        Cursor c = null;
        SQLiteDatabase db =this.getReadableDatabase();
        String q = " SELECT * FROM " + TableName + "WHERE id == "+id ;
        if(db!=null){
            c= db.rawQuery(q,null);
        }
        return  c;

    }*/
}

