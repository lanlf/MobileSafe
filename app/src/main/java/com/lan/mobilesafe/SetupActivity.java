package com.lan.mobilesafe;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by lan on 2016/6/8.
 */
public abstract class SetupActivity extends Activity {

    private GestureDetector gestureDetector;
    protected SharedPreferences sp;
//GestureDetector：手势识别器
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        gestureDetector = new GestureDetector(this, new MyOnGestureListener());
    }
    //一定要注册手势识别器，它才会生效
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class MyOnGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float x1 = e1.getX();
            float y1 = e1.getY();
            float x2 = e2.getX();
            float y2 = e2.getY();
            /*if (Math.abs(y1 - y2) > 50) {
                return false;
            }*/
            if (x1 - x2 > 50) {
                System.out.println("youyou");
                ActivityNext();
            }
            if (x2 - x1 > 50) {
                System.out.println("zouzou");
                ActivityPre();
            }
            return true;
        }
    }

    void pre(View v) {
        ActivityPre();
    }

    void next(View v) {
        ActivityNext();
    }


    abstract void ActivityNext();

    abstract void ActivityPre();
}
