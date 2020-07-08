package com.example.uploadingpdf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddConstraint extends AppCompatActivity {
Spinner spinner;
EditText editText;
Button button;
ArrayAdapter arrayAdapter;
Database database;
String item;   String s[]={"Start_With","End_With","contains"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_constraint);
        getSupportActionBar().setTitle("Constarint");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        database=new Database(this);
        spinner=findViewById(R.id.spinner_constarit);
        editText=findViewById(R.id.constarint_number);
        button=findViewById(R.id.btn_constraint);
        arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,s);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item=s[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setAdapter(arrayAdapter);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onloadData();
                viewData();
            }
        });
    }
    private void viewData() {
        startActivity(new Intent(AddConstraint.this,MainActivity.class));
    }
    private void onloadData() {
     boolean result= database.insertData(item,editText.getText().toString());
     if (result){
         Toast.makeText(this,"Upload Successfully",Toast.LENGTH_LONG).show();
     }
     else{
         Toast.makeText(this,"error in uploading",Toast.LENGTH_LONG).show();


     }

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}