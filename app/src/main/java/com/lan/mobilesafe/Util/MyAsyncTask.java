package com.lan.mobilesafe.Util;


import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

/**
 * Created by lan on 2016/7/6.
 */
public abstract class MyAsyncTask {
    Handler handler = new  Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            onPostExecute();
        }
    };

    //线程中

    protected abstract void doInBackground();

    //前
    protected abstract void onPreExecute();

    //后
    protected abstract void onPostExecute();

    public void execute(){
      onPreExecute();
        new Thread(){
            @Override
            public void run() {
                doInBackground();
                SystemClock.sleep(3333);
                handler.sendEmptyMessage(0);
            }
        }.start();
    }
}
