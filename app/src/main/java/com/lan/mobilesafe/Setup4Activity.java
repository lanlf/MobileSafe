package com.lan.mobilesafe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by lan on 2016/6/8.
 */
public class Setup4Activity extends SetupActivity {
   /* @ViewInject(R.id.setup4_cb)*/
    CheckBox cb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup4);
        cb = (CheckBox) findViewById(R.id.setup4_cb);
        if(sp.getBoolean("safe",true)){
            cb.setChecked(true);
            cb.setText("开启了");
        }else {
            cb.setChecked(false);
            cb.setText("没开启");
        }
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            SharedPreferences.Editor editor = sp.edit();
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    editor.putBoolean("safe",true);
                    cb.setText("开启了");
                }else {
                    editor.putBoolean("safe",false);
                    cb.setText("没开启");
                }editor.apply();
            }
        });
    }

    @Override
    void ActivityNext() {
        Intent intent = new Intent(this,FindLostActivity.class);
        startActivity(intent);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean("first",false);
        edit.commit();
        finish();
    }

    @Override
    void ActivityPre() {
        Intent intent = new Intent(this, Setup3Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.anim_enter_pre,R.anim.anim_exit_pre);
    }
}
