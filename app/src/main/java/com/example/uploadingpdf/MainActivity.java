package com.example.uploadingpdf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

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
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    List<ListData> listDataList=new ArrayList<>();
                    ListData pos= (ListData) parent.getItemAtPosition(position);
                         MyAdapter1 myAdapter1= (MyAdapter1) parent.getAdapter();
                            listDataList=myAdapter1.getList();

                          ListData listItem=listDataList.get(position);
                          if (removeToItemList(listDataList,listItem)){
                              database=new Database(MainActivity.this);

                              boolean result=database.onDeleteData(listItem.getName());
                              if (result){

                                  view.invalidate();
                                  myAdapter1.notifyDataSetChanged();
                                  Toast.makeText(MainActivity.this,"Deleted",Toast.LENGTH_LONG).show();


                              }
                              else {
                                  Toast.makeText(MainActivity.this,"not Deleted",Toast.LENGTH_LONG).show();

                              }

                          }
                    //listDataList.remove(parent.getAdapter());
                    return true;
                }
            });
         myAdapter.notifyDataSetChanged();
    }

    private boolean removeToItemList(List<ListData> listDataList, ListData listItem) {
            return listDataList.remove(listItem);
    }

}