package com.example.uploadingpdf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
ListView listView;
MyAdapter1 myAdapter;
ArrayList<ListData> arrayList;
Database database;
FloatingActionButton button,button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=findViewById(R.id.listView);
        button=findViewById(R.id.btn_add);
        button1=findViewById(R.id.btn_addConstraint);
        database=new Database(getApplicationContext());
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AddConstraint.class));

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Add.class));
            }
        });
        loadDataListView();


    }

    private void loadDataListView() {
         arrayList=  database.ViewData();
         myAdapter=new MyAdapter1(getApplicationContext(),arrayList);
         listView.setAdapter(myAdapter);
         myAdapter.notifyDataSetChanged();
    }

}