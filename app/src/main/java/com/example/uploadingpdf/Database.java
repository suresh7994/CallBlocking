package com.example.uploadingpdf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
   private String TABLE_NAME="call_blocking_details";
    public Database( Context context) {
        super(context, "call.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+TABLE_NAME +"(name varchar(30),number varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    public boolean insertData(String name ,String number ){
        SQLiteDatabase database=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",name);
        contentValues.put("number",number);
        long i=database.insert(TABLE_NAME,null,contentValues);
        if (i==-1)
            return false;
        else
            return true;

    }
    public ArrayList<ListData> ViewData(){
        ArrayList<ListData> arrayList=new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        String query="select * from "+TABLE_NAME;
        Cursor cursor=sqLiteDatabase.rawQuery(query,null);
        while (cursor.moveToNext()){
         String name=cursor.getString(0);
         String number=cursor.getString(1);
         ListData listData=new ListData(name,number);
           arrayList.add(listData);

        }
        return arrayList;

    }
    public boolean onDeleteData(String name){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        int i= sqLiteDatabase.delete(TABLE_NAME,"name=?",new String[]{name});
         if (i==-1)
             return false;
         else
             return true;
    }

}
