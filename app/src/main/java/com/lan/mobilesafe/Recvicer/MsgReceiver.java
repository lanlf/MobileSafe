package com.lan.mobilesafe.Recvicer;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MsgReceiver extends BroadcastReceiver {
    public MsgReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        System.out.println("收到短信了。。。。");
        //获取intent参数
        Bundle bundle = intent.getExtras();
        SmsMessage msg = null;

        if (null != bundle) {
            //取pdus内容,转换为Object[]
            Object[] smsObj = (Object[]) bundle.get("pdus");
            //解析短信
            for (Object object : smsObj) {
                msg = SmsMessage.createFromPdu((byte[]) object);
                Date date = new Date(msg.getTimestampMillis());//时间
                //将时间转换成年月日的格式
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String receiveTime = format.format(date);
                String body = msg.getDisplayMessageBody();

                System.out.println("number:" + msg.getOriginatingAddress()
                        + "   body:" + body + "  time:"
                        + receiveTime);
                //4.4版本以后，只有默认的短信应用才可以阻止短信接收了
                //abortBroadcast();
                //系统自带的，不删除的话接收到广播就报错
                // throw new UnsupportedOperationException("Not yet implemented");
                if ("gps".equals(body)) {
                    LocationManager locationManager = (android.location.LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                    List<String> providers = locationManager.getProviders(true);
                    for (String pro : providers) {
                        System.out.println(pro);
                    }
                    Criteria criteria = new Criteria();
                    criteria.setAltitudeRequired(true);
                    String bestpro = locationManager.getBestProvider(criteria, true);
                    System.out.println(bestpro);
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    locationManager.requestLocationUpdates(bestpro, 0, 0, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            System.out.println("jing:" +latitude +"wei"+longitude);
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {

                        }

                        @Override
                        public void onProviderEnabled(String provider) {

                        }

                        @Override
                        public void onProviderDisabled(String provider) {

                        }
                    });
                }else if("clam".equals(body)){

                }else if("del".equals(body)){

                }else if("lock".equals(body)){

                }
            }
        }
    }
}