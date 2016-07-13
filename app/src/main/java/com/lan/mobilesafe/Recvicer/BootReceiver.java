package com.lan.mobilesafe.Recvicer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

public class BootReceiver extends BroadcastReceiver {
    public BootReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        System.out.println("------重启了了了了了了~");
        SmsManager sms = SmsManager.getDefault();
       /* sms.sendTextMessage();  发送短信*/
        System.out.println("拿到smsmanager了啦啦啦啦啦啦啦");
        //throw new UnsupportedOperationException("Not yet implemented");
    }
}
