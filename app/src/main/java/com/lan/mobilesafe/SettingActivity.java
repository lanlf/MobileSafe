package com.lan.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by lan on 2016/5/26.
 */
public class SettingActivity extends Activity {

    private SharedPreferences sp;
    private SettingView sv_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        sv_setting = (SettingView) findViewById(R.id.setting_sv);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        boolean update = sp.getBoolean("update", true);
        if (update) {
            sv_setting.setBoxChecked(true);
        } else {
            sv_setting.setBoxChecked(false);

        }
        sv_setting.setOnClickListener(new View.OnClickListener() {
            SharedPreferences.Editor edit = sp.edit();

            @Override
            public void onClick(View v) {
                if (sv_setting.boxisChecked()) {
                    sv_setting.setBoxChecked(false);
                    edit.putBoolean("update", false);
                } else {
                    sv_setting.setBoxChecked(true);
                    edit.putBoolean("update", true);
                }
                edit.apply();
            }
        });
    }

}
