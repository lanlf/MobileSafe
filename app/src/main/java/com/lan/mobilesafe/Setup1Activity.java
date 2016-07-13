package com.lan.mobilesafe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by lan on 2016/6/8.
 */
public class Setup1Activity extends SetupActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup1);
    }

    @Override
    void next(View v) {
        super.next(v);
    }


    @Override
    void ActivityNext() {
        Intent intent = new Intent(this, Setup2Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.anim_enter_next,R.anim.anim_exit_next);
    }

    @Override
    void ActivityPre() {

    }
}
