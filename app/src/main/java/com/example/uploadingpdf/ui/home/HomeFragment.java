package com.example.uploadingpdf.ui.home;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CallLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uploadingpdf.MyAdapter;

import com.example.uploadingpdf.MyModel;
import com.example.uploadingpdf.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    ImageView imageView;
    Cursor cursor;
    int number,type ,name ,date, duration;
    List<MyModel> list;
    int sum=0;
    String result,durations;
    ProgressBar progressBar;
    Handler handler;
    MyAdapter myAdapter;
    LinearLayoutManager linearLayoutManager;
    String callnumber,calltype,callername,calldate, dateString,callduration;
    int dir;
    String person,person1,call_type;
    Activity activity=getActivity();
    View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView =root.findViewById(R.id.recycleview);
        linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);
        synchronized (this) {
            myTask myTask = new myTask();
            myTask.execute();
        }
        return root;
    }

    class  myTask extends AsyncTask<String,String,List<MyModel>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<MyModel> doInBackground(String... strings) {
            Calendar calendar = Calendar.getInstance();

            calendar.set(2020, Calendar.MAY, 26);
            String fromDate = String.valueOf(calendar.getTimeInMillis());
            calendar.set(2020, Calendar.MAY, 27);
            String toDate = String.valueOf(calendar.getTimeInMillis());
            String[] whereValue = {fromDate,toDate};

            list = new ArrayList<>();

            Uri allCalls = Uri.parse("content://call_log/calls");
            cursor =getContext().getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
            number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
            type = cursor.getColumnIndex(CallLog.Calls.TYPE);
            name = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
            date = cursor.getColumnIndex(CallLog.Calls.DATE);
            duration = cursor.getColumnIndex(CallLog.Calls.DURATION);
            while (cursor.moveToNext()) {

                callnumber = cursor.getString(number);
                calltype = cursor.getString(type);


                callername = cursor.getString(name);

                callduration = cursor.getString(duration);
                calldate = cursor.getString(date);
                Date calldates = new Date(Long.valueOf(calldate));
                SimpleDateFormat simpleDateForma = new SimpleDateFormat("HH:mm aa");
                dateString = simpleDateForma.format(calldates);

                int dircode = Integer.parseInt(calltype);
//
                switch (dircode) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        dir = R.drawable.ic_call_made_black_24dp;
                        call_type="OUTGOING";
                        break;
                    case CallLog.Calls.INCOMING_TYPE:
                        dir = R.drawable.ic_call_received_black_24dp;
                        call_type="INCOMING";
                        break;

                    case CallLog.Calls.MISSED_TYPE:
                        dir = R.drawable.ic_call_missed_outgoing_black_24dp;
                        call_type="Missed";
                        break;
                    case CallLog.Calls.REJECTED_TYPE:
                        dir = R.drawable.ic_cancel_black_24dp;
                        call_type="Rejected";
                        break;
                }

                sum = Integer.parseInt(callduration);
                if (sum >= 0 && sum <= 60) {
                    durations = sum + "sec";
                } else if (sum > 60 && sum <= 3599) {
                    String result = String.valueOf(sum / 60);
                    String result1 = String.valueOf(sum % 60);
                    durations = result + " min " + result1 + " sec";

                } else {
                    durations = callduration;
                }
                if (callername == null) {
                    person1 = "?";
                    list.add(new MyModel(callnumber, dateString, durations, dir, person1,call_type));
                } else {
                    person = callername.charAt(0) + "";
                    list.add(new MyModel(callnumber,callername, dateString, durations, dir, person,call_type));
                }
            }

            return list;
        }

        @Override
        protected void onPostExecute(List<MyModel> list) {
            super.onPostExecute(list);
            MyAdapter myAdapter=new MyAdapter(getContext(),list);

            recyclerView.setAdapter(myAdapter);

        }
    }
}
