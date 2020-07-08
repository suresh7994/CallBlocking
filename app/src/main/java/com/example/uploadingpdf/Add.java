package com.example.uploadingpdf;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Add extends AppCompatActivity {
EditText edit_name,edit_number;
Button btn_block;
TextView text_add;
    Database database;
    ArrayList<String> listItem;
    ArrayAdapter arrayAdapter;
    Intent intent;
    private static final int PERMISSION_REQUEST_READ_PHONE_STATE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().setTitle("Block Number");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE, Manifest.permission.READ_CALL_LOG,Manifest.permission.WRITE_CALL_LOG,Manifest.permission.CALL_PHONE,Manifest.permission.ANSWER_PHONE_CALLS,Manifest.permission.MODIFY_PHONE_STATE};
                requestPermissions(permissions, PERMISSION_REQUEST_READ_PHONE_STATE);
            }
        }

        database=new Database(getApplicationContext());

        edit_name=findViewById(R.id.name);
        edit_number=findViewById(R.id.number);
        btn_block=findViewById(R.id.blocked_number);
        text_add=findViewById(R.id.selecting_contact);
        listItem=new ArrayList<String>();
       text_add.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               loadContact();
           }
       });
        btn_block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSendData();
                viewData();

            }
        });
        }

    private void viewData() {
        startActivity(new Intent(Add.this,MainActivity.class));
    }@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_READ_PHONE_STATE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted: " + PERMISSION_REQUEST_READ_PHONE_STATE, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission NOT granted: " + PERMISSION_REQUEST_READ_PHONE_STATE, Toast.LENGTH_SHORT).show();
                }

                return;
            }
        }
    }

    private void onSendData() {
       boolean result= database.insertData(edit_name.getText().toString(),edit_number.getText().toString());
       if (result){
           Toast.makeText(Add.this,"data inserted",Toast.LENGTH_LONG).show();
       }
       else
           Toast.makeText(Add.this,"data not inserted",Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
    public void loadContact(){
        Uri contactUri= ContactsContract.Contacts.CONTENT_URI;
        intent=new Intent(Intent.ACTION_PICK,contactUri);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
        startActivityForResult(intent,1);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK){
            Cursor cursor=getContentResolver().query(data.getData(),null,null,null,null);
            if (cursor.moveToNext()){
                int id=cursor.getColumnIndex(ContactsContract.Contacts._ID);
                int name=cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                String name_man=cursor.getString(name);
                String contact_id=cursor.getString(id);
                int hasNo=cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
                String contact_Has_Number=cursor.getString(hasNo);
                if(Integer.valueOf(contact_Has_Number)>0){
                    Cursor num=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID+" = "+contact_id,null,null);
                    while (num.moveToNext()){

                        String phone_number=num.getString(num.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        edit_name.setText(name_man+"");
                        if (phone_number.startsWith("+91")){
                            edit_number.setText(phone_number+"");
                        }
                        else {
                            edit_name.setText("+91"+phone_number);

                        }
                    }
                }else {
                    Toast.makeText(this, "No Mobile Number Selected ! ", Toast.LENGTH_SHORT).show();
                }

            }else {
                Toast.makeText(this, "No Data !", Toast.LENGTH_SHORT).show();

            }

        }
    }

}