package com.lan.mobilesafe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Telephony;
import android.provider.Telephony.Sms;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.lan.mobilesafe.Util.MD5Util;

/**
 * Created by lan on 2016/6/8.
 */
public class FindLostActivity extends Activity {

    private SharedPreferences sp;
    //String myPackageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        if (sp.getBoolean("first", true)) {
            Intent intent = new Intent(this, Setup1Activity.class);
            startActivity(intent);
            finish();

        } else {
            setContentView(R.layout.activity_findlost);
            ImageView fl_lock = (ImageView) findViewById(R.id.fl_lock);
            if (!sp.getString("sim", "").isEmpty())
                fl_lock.setImageResource(R.drawable.locked);
            /*myPackageName = getPackageName();
            if (!Sms.getDefaultSmsPackage(this).equals(myPackageName)){
                showdialog();
            }*/
        }
    }

    void reset(View v) {
        Intent intent = new Intent(this, Setup1Activity.class);
        startActivity(intent);
        finish();
    }
    //设置成默认短信应用，但是那个activity开启不了
/*
   private void showdialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.activity_defaultsms, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        Button bt_yes = (Button) view.findViewById(R.id.setpassword_yes);
        Button bt_no = (Button) view.findViewById(R.id.setpassword_no);
        bt_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("fsdfs");
              Intent intent = new Intent(Sms.Intents.ACTION_CHANGE_DEFAULT);
                intent.putExtra(Sms.Intents.EXTRA_PACKAGE_NAME,myPackageName);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        bt_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
*/
}
