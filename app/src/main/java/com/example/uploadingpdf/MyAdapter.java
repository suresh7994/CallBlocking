package com.example.uploadingpdf;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
    Context context;
    List<MyModel> arrayList;

    public MyAdapter(Context context, List<MyModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.model, parent, false);
        MyHolder myHolder = new MyHolder(v);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        final MyModel myModel = arrayList.get(position);
        holder.name.setText(myModel.getName());
        holder.person.setText(myModel.getPerson());
        holder.date.setText(myModel.getDate());
        holder.duration.setText(myModel.getDuration());
        holder.Incoming.setImageResource(myModel.getIncoming());
        holder.call_type.setText(myModel.getCall_type());
        holder.imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);

                intent.setData(Uri.parse("tel:" + myModel.getNumber()));

                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                context.startActivity(intent);
            }
        });
       holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
           //    Intent intent=new Intent(context, Main2Activity.class);
             //  intent.putExtra("name",myModel.getName());
               //intent.putExtra("person",myModel.getPerson());
               //intent.putExtra("number",myModel.getNumber());
               //context.startActivity(intent);
           }
       });
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    class MyHolder extends RecyclerView.ViewHolder{
         TextView name,date,duration,person,call_type;
         ImageView imageView,Incoming,imageView1;
         RelativeLayout relativeLayout;
        public MyHolder( View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.number);
            date=itemView.findViewById(R.id.date);
            duration=itemView.findViewById(R.id.duration);
            Incoming=itemView.findViewById(R.id.Incoming);
            person=itemView.findViewById(R.id.person_img);
            call_type=itemView.findViewById(R.id.call_type);
            imageView1=itemView.findViewById(R.id.call_img);
            relativeLayout=itemView.findViewById(R.id.relativeLayout);
        }
    }


}
