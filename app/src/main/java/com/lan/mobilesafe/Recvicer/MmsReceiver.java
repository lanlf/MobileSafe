package com.lan.mobilesafe.Recvicer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MmsReceiver extends BroadcastReceiver {
    public MmsReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //系统自带的，不删除的话接收到广播就报错
        throw new UnsupportedOperationException("Not yet implemented");
    }
}