/*
 * Copyright © Ricki Hirner (bitfire web engineering).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 */

package com.example.uploadingpdf;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.os.Build;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.RequiresApi;


import com.android.internal.telephony.ITelephony;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class CallReceiver extends BroadcastReceiver {
    private static final String TAG = "NoPhoneSpam";

    private static final int NOTIFY_REJECTED = 0;
    private static boolean AlreadyOnCall = false;
    Database database;
    ArrayList<ListData> listData;
    String incomingNumber;


    @Override
    public void onReceive(Context context, Intent intent) {
        database = new Database(context);

        listData = database.ViewData();

         incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

        if (incomingNumber == null)
            return;

        else {
            for (int i = 0; i < listData.size(); i++) {

                if (incomingNumber.startsWith(listData.get(i).getNumber()) && listData.get(i).getName().equals("Start with")){
                    breakCall(context);
                }
               else if (incomingNumber.endsWith(listData.get(i).getNumber()) && listData.get(i).getName().equals("End with")){
                    breakCall(context);
                }
               else  if (incomingNumber.contains(listData.get(i).getNumber()) && listData.get(i).getName().equals("Contains")){
                    breakCall(context);
                }
                else if(incomingNumber.equalsIgnoreCase(listData.get(i).getNumber())) {
                   breakCall(context);
                }

            }
        }
    }
    @SuppressWarnings({"rawtypes", "unchecked"})
    private void breakCall(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {

            TelecomManager telecomManager = (TelecomManager) context.getSystemService(Context.TELECOM_SERVICE);

            try {
                Log.d(TAG, "Invoked 'endCall' on TelecomManager");
                if (incomingNumber != null) {
                    telecomManager.endCall();
                }

            } catch (Exception e) {
                Log.e(TAG, "Couldn't end call with TelecomManager", e);

            }
        } else {
            TelephonyManager telephony = (TelephonyManager)
                    context.getSystemService(Context.TELEPHONY_SERVICE);
            try {
                Class c = Class.forName(telephony.getClass().getName());
                Method m = c.getDeclaredMethod("getITelephony");
                m.setAccessible(true);
                ITelephony telephonyService = (ITelephony) m.invoke(telephony);
                telephonyService.endCall();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}









    //   Cursor result = database.show(Integer.parseInt(editText.getText().toString()));
    //             textView.setText("");
    //           textView1.setText("");
    //         while (result.moveToNext()){
    //textView.setText(result.getString(1));
    //textView1.setText(result.getString(3));


/*
  Cursor cursor = db.query(constraintDatabase.TABLE_NAME ,null, constraintDatabase.num+"= ?",new String[]{"9163"}, null, null,null);
                    ContentValues values = new ContentValues();


                    while (cursor.moveToFirst()) {


                        name = cursor.getString(cursor.getColumnIndex(constraintDatabase.num));


                        if (incomingNumber.startsWith((String) values.get(constraintDatabase.num))) {

                            telephony.endCall();
                            break;
                        } else {
                            Toast.makeText(context, "not able to block the number", Toast.LENGTH_LONG).show();
                        }


 */
