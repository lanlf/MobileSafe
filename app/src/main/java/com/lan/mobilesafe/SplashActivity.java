package com.lan.mobilesafe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.*;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class SplashActivity extends Activity {
    private static final int SPLASH_UPDATE_DIALOG = 1;
    private static final int SPLASH_ENTERHOME = 2;
    private static final int SPLASH_URL_ERROR = 3;
    private static final int SPLASH_IO_ERROR = 4;
    private static final int SPLASH_JSONE_ERROR = 5 ;
    private static final int SPLASH_SERVER_ERROR = 6;
    private TextView tv_splash_versionname;
    private TextView getTv_splash_update;
    private String versionName;
    private String des;
    private String apkurl;
    private String code;
    private SharedPreferences sp;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SPLASH_UPDATE_DIALOG:
                    showDialog();
                    break;
                case SPLASH_ENTERHOME:
                    enterHome();
                    break;
                case SPLASH_URL_ERROR:
                Toast.makeText(getApplicationContext(), "出错鸟~错误码：" + SPLASH_URL_ERROR,Toast.LENGTH_SHORT).show();
                    enterHome();
                break;
                case SPLASH_IO_ERROR:
                    Toast.makeText(getApplicationContext(), "网络不给力~" ,Toast.LENGTH_SHORT).show();
                    enterHome();
                    break;
                case SPLASH_JSONE_ERROR:
                    Toast.makeText(getApplicationContext(), "出错鸟~错误码：" + SPLASH_JSONE_ERROR,Toast.LENGTH_SHORT).show();
                    enterHome();
                    break;
                case SPLASH_SERVER_ERROR:
                    Toast.makeText(getApplicationContext(), "服务器异常~",Toast.LENGTH_SHORT).show();
                    enterHome();
                    break;
            }
        }
    };

    void enterHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    protected void showDialog() {
        AlertDialog.Builder builer = new AlertDialog.Builder(this);
        builer.setCancelable(false);
        builer.setTitle("Mobilesafe" + code);
        builer.setIcon(R.mipmap.ic_launcher);
        builer.setMessage(des);
        builer.setPositiveButton("升级", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                download();
            }
        });
        builer.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enterHome();
            }
        });
        builer.show();
    }

    private void install() {
        /**
         *  安装新版本
         *  <intent-filter>
         <action android:name="android.intent.action.VIEW" />
         <category android:name="android.intent.category.DEFAULT" />
         <data android:scheme="content" /> //content : 从内容提供者中获取数据  content://
         <data android:scheme="file" /> // file : 从文件中获取数据
         <data android:mimeType="application/vnd.android.package-archive" />
         </intent-filter>
         */
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath() + "/mobliesafe_2.apk")), "application/vnd.android.package-archive");
        startActivityForResult(intent, 0);
    }
    //调用startActivityForResult（）方法开启Activity时会调用onActivityResult方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        enterHome();
    }

    private void download() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //用xutils下载新版本
            RequestParams params = new RequestParams(apkurl);
            params.setAutoResume(true);
            params.setSaveFilePath(Environment.getExternalStorageDirectory().getPath() + "/mobliesafe_2.apk");
            x.http().get(params, new Callback.ProgressCallback<File>() {

                @Override
                public void onSuccess(File result) {
                    System.out.println("------成功");
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    System.out.println("------出错");
                }

                @Override
                public void onCancelled(CancelledException cex) {
                    System.out.println("------取消~");
                }

                @Override
                public void onFinished() {
                    System.out.println("--------完成");
                    install();
                }

                @Override
                public void onWaiting() {
                    System.out.println("------等待~");
                }

                @Override
                public void onStarted() {
                    System.out.println("-------开始下载");
                    getTv_splash_update.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoading(long total, long current, boolean isDownloading) {
                    System.out.println("-------正在下载");
                    getTv_splash_update.setText(total + "/" + current);
                }
            });
        } else {
            System.out.print("sd卡没挂载~");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sp = getSharedPreferences("config",MODE_PRIVATE);
        boolean update = sp.getBoolean("update", true);
        versionName = getVersionName();
        tv_splash_versionname = (TextView) findViewById(R.id.tv_splash_versionname);
        getTv_splash_update = (TextView) findViewById(R.id.tv_splash_update) ;
        String version = "Mobilesafe" + versionName;
        tv_splash_versionname.setText(version);
        if(update){
            update();
        }else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(3000);
                    enterHome();
                }
            }).start();
        }
    }

    void update() {
        //检查有没有新版本
        new Thread() {

            @Override
            public void run() {
                Message msg = Message.obtain();
                int start = (int) System.currentTimeMillis();
                try {
                    URL url = new URL("http://192.168.1.4:8080/ms.html");
                    HttpURLConnection cnn = (HttpURLConnection) url.openConnection();
                    cnn.setConnectTimeout(5000);
                    cnn.setRequestMethod("GET");
                    int responseCode = cnn.getResponseCode();
                    if (responseCode == 200) {
                        System.out.println("链接成功鸟~");
                        InputStream is = cnn.getInputStream();
                        String s = StreamUtil.parseInputStream(is);
                        JSONObject jsonObject = new JSONObject(s);
                        code = jsonObject.getString("code");
                        apkurl = jsonObject.getString("apkurl");
                        des = jsonObject.getString("des");
                        if (code.equals(getVersionName())) {
                            msg.what = SPLASH_ENTERHOME;
                        } else {
                            msg.what = SPLASH_UPDATE_DIALOG;
                        }
                    } else {
                        System.out.println("失败了");
                        msg.what = SPLASH_SERVER_ERROR;
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    msg.what = SPLASH_URL_ERROR;
                } catch (IOException e) {
                    e.printStackTrace();
                    msg.what = SPLASH_IO_ERROR;
                } catch (JSONException e) {
                    e.printStackTrace();
                    msg.what = SPLASH_JSONE_ERROR;
                } finally {
                    int end = (int) System.currentTimeMillis();
                    int total = end - start;
                    if (total < 2000) {
                        SystemClock.sleep(2000 - total);
                    }
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }


    private String getVersionName() {
        //获取当前版本名
        PackageManager pm = getPackageManager();

        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
