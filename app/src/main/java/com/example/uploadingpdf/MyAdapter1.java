package com.example.uploadingpdf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter1 extends BaseAdapter {

    Database database;
    Context context;
    ArrayList<ListData> arrayList;
    public MyAdapter1(Context context, ArrayList<ListData> arrayList){
        this.context=context;
        this.arrayList=arrayList;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view=inflater.from(parent.getContext()).inflate(R.layout.model1,parent,false);
        final TextView text_name=view.findViewById(R.id.text_name);
        TextView text_number=view.findViewById(R.id.text_number);

        final ListData listData=arrayList.get(position);
        text_name.setText(listData.getName());
        text_number.setText(listData.getNumber());
        notifyDataSetChanged();
//        view.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                    String name=arrayList.get(position).getName();
//                database=new Database(context);
//
//                boolean result=database.onDeleteData(name);
//                if (result){
//                    Toast.makeText(context,"Deleted",Toast.LENGTH_LONG).show();
//                    MyAdapter1.this.notifyDataSetChanged();
//
//
//                }
//                else {
//                    Toast.makeText(context,"not Deleted",Toast.LENGTH_LONG).show();
//
//                }

//                 return true;
//            }
//        });
        return view;
    }
        public List<ListData> getList(){
        return arrayList;
        }
}
