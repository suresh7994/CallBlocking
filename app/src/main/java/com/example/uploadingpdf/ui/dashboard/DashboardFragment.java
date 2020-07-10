package com.example.uploadingpdf.ui.dashboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.uploadingpdf.Add;
import com.example.uploadingpdf.AddConstraint;
import com.example.uploadingpdf.Database;
import com.example.uploadingpdf.ListData;
import com.example.uploadingpdf.MainActivity;
import com.example.uploadingpdf.MyAdapter1;
import com.example.uploadingpdf.R;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {
    ListView listView;
    MyAdapter1 myAdapter;
    ArrayList<ListData> arrayList;
    Database database;
    FloatingActionButton button,button1;
    AlertDialog.Builder builder;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        listView=root.findViewById(R.id.listView);
        button=root.findViewById(R.id.btn_add);
        button1=root.findViewById(R.id.btn_addConstraint);
        database=new Database(getContext());
        builder=new AlertDialog.Builder(getContext());
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

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, long id) {

                List<ListData> listDataList=new ArrayList<>();
                ListData pos= (ListData) parent.getItemAtPosition(position);
                final MyAdapter1 myAdapter1= (MyAdapter1) parent.getAdapter();
                listDataList=myAdapter1.getList();

                final ListData listItem=listDataList.get(position);
                if (removeToItemList(listDataList,listItem)){
                    database=new Database(getContext());
                    final boolean result=database.onDeleteData(listItem.getName());
                    builder.setMessage("Are you sure you want to delete the number??")
                            .setIcon(R.drawable.ic_baseline_delete_forever_24)
                            .setTitle("Delete Number")
                            .setCancelable(false)
                     .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                             if (result){
                                 view.invalidate();
                                 myAdapter1.notifyDataSetChanged();
                                 Toast.makeText(getContext(),"Deleted",Toast.LENGTH_LONG).show();


                             }
                             else {
                                 Toast.makeText(getContext(),"not Deleted",Toast.LENGTH_LONG).show();

                             }

                         }
                     })
                     .setNegativeButton("No",null);
                    AlertDialog alertDialog=builder.create();
                    alertDialog.show();


                }


                return true;
            }

    });
      //  myAdapter.notifyDataSetChanged();
         }

    private boolean removeToItemList(List<ListData> listDataList, ListData listItem) {

        return listDataList.remove(listItem);
    }

}
/**/