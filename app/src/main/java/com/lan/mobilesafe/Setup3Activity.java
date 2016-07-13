package com.lan.mobilesafe;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.ViewUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lan.mobilesafe.Engine.ContactEngine;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by lan on 2016/6/8.
 */
public class Setup3Activity extends SetupActivity {
    @ViewInject(R.id.setup3_et)
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
        x.view().inject(this);
    }

    @Override
    void ActivityNext() {
        Intent intent = new Intent(this, Setup4Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.anim_enter_next, R.anim.anim_exit_next);
    }

    void click(View v) {
       // 跳转到系统的联系人界面
        /*Intent intent = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);*/
        Intent intent = new Intent(this,ContactActivity.class);
        this.startActivityForResult(intent, 1);
    }

    @Override
    void ActivityPre() {
        Intent intent = new Intent(this, Setup2Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.anim_enter_pre, R.anim.anim_exit_pre);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                String phone = data.getStringExtra("phone");
                et.setText(phone);
            }
        }
    }
//网上找的，直接跳转到系统的联系人界面，也有错。。
  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor cursor = managedQuery(contactData, null, null, null,
                            null);
                    cursor.moveToFirst();
                    String num = ContactEngine.getContactPhone(cursor, this);
                    et.setText(num);
                }
                break;

            default:
                break;
        }
    }*/
}