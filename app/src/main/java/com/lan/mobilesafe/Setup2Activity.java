package com.lan.mobilesafe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.CheckBox;

/**
 * Created by lan on 2016/6/8.
 */
public class Setup2Activity extends SetupActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);
        final SettingView sv = (SettingView) findViewById(R.id.setup2_sv);
        if(sp.getString("sim","").isEmpty()){
            sv.setBoxChecked(false);
        }else {
            sv.setBoxChecked(true);
        }
        sv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor edit = sp.edit();
                if (sv.boxisChecked()) {
                    sv.setBoxChecked(false);
                    edit.putString("sim","");
                } else {
                    sv.setBoxChecked(true);
                    TelephonyManager tel = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                    String sim = tel.getSimSerialNumber();
                    edit = sp.edit();
                    edit.putString("sim", sim);
                }
                edit.commit();
            }
        });
    }


    @Override
    void ActivityNext() {
        Intent intent = new Intent(this, Setup3Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.anim_enter_next, R.anim.anim_exit_next);
    }

    @Override
    void ActivityPre() {
        Intent intent = new Intent(this, Setup1Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.anim_enter_pre, R.anim.anim_exit_pre);
    }
}
