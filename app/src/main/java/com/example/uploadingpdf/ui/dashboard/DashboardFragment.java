package com.example.uploadingpdf.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.uploadingpdf.Add;
import com.example.uploadingpdf.AddConstraint;
import com.example.uploadingpdf.Database;
import com.example.uploadingpdf.ListData;
import com.example.uploadingpdf.MyAdapter1;
import com.example.uploadingpdf.R;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {
    ListView listView;
    MyAdapter1 myAdapter;
    ArrayList<ListData> arrayList;
    Database database;
    FloatingActionButton button,button1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        listView=root.findViewById(R.id.listView);
        button=root.findViewById(R.id.btn_add);
        button1=root.findViewById(R.id.btn_addConstraint);
        database=new Database(getContext());
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddConstraint.class));

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Add.class));
            }
        });
        loadDataListView();


        return root;
    }

    private void loadDataListView() {
        arrayList=  database.ViewData();
        myAdapter=new MyAdapter1(getContext(),arrayList);
        listView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }
}