package com.lan.mobilesafe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lan.mobilesafe.Util.MD5Util;


/**
 * Created by lan on 2016/5/26.
 */
public class HomeActivity extends Activity{
    int[] res_icon = new int[]{
            R.drawable.safe,R.drawable.connect, R.drawable.appmanger, R.drawable.progress, R.drawable.internet, R.drawable.kill, R.drawable.clear, R.drawable.tool, R.drawable.setting
    };
    String [] str_icon = new String[]{
            "手机防盗","通讯卫士","应用管理","进程管理","流量统计","手机杀毒","清理垃圾", "高级工具", "系统设置"
    };
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sp = getSharedPreferences("config",MODE_PRIVATE);

        GridView gv_home = (GridView) findViewById(R.id.gv_home_gridview);
        gv_home.setAdapter(new GridViewAdapter());
        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        if(sp.getString("password", "").isEmpty()){
                        showsetdialog();
                        }else {
                            showpwdialog();
                        }
                        break;
                    case 8:
                        Intent intent = new Intent(HomeActivity.this, SettingActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    private void showpwdialog() {
        final AlertDialog.Builder builder= new AlertDialog.Builder(this);
        View view = View.inflate(this,R.layout.activity_password,null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        final EditText et_pw = (EditText) view.findViewById(R.id.setpassword);
        Button bt_ok = (Button) view.findViewById(R.id.setpassword_ok);
        Button bt_cancle = (Button) view.findViewById(R.id.setpassword_cancle);
        final ImageView pw_v = (ImageView) view.findViewById(R.id.pw_v);
        final boolean[] visable = {false};
        pw_v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setInputType 可以更改 TextView 的输入方式：Contact、Email、Date、Time、Short Message、Normal Text、Password 等。还可以指定各种更正选项，如 单词首字母大写、句子首字母大写、自动更正等。
                //setTransformationMethod 则可以支持将输入的字符转换，包括清除换行符、转换为掩码。使用方法：

                /*textView.setTransformationMethod(PasswordTransformationMethod.getInstance());
                综合来说，如果需要实现自己的转换，可以通过实现 TransformationMethod 接口来达到你的目的（比如让输入的所有字符都变成 a，或者输入 a 显示 z，输入 z 显示 a 等）。*/
                //这里用setTransformationMethod更合适，用setInputType显示密码时，光标会看不见，输入法会自动切换
                if(visable[0]){
                    pw_v.setImageResource(R.drawable.pw_v);
                    et_pw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    et_pw.setSelection(et_pw.getText().toString().trim().length());
                    visable[0] = false;
                }else {
                    pw_v.setImageResource(R.drawable.pw_inv);
                    et_pw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    et_pw.setSelection(et_pw.getText().toString().trim().length());
                    visable[0] = true;
                }
            }
        });
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pw = et_pw.getText().toString().trim();
                String pw_md5 = MD5Util.md5(pw);

                if(pw.isEmpty()){
                    Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }else if(pw_md5.equals(sp.getString("password","").trim())){
                    Toast.makeText(getApplicationContext(), "密码正确", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    Intent intent = new Intent(HomeActivity.this,FindLostActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "密码不正确", Toast.LENGTH_SHORT).show();
                }
            }
        });
        bt_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void showsetdialog() {
        final AlertDialog.Builder builder= new AlertDialog.Builder(this);
        View view = View.inflate(this,R.layout.activity_setpassword,null);
        builder.setView(view);
        final EditText et_pw = (EditText) view.findViewById(R.id.setpassword);
        final EditText et_pwc = (EditText) view.findViewById(R.id.confirmpassword);
        Button bt_ok = (Button) view.findViewById(R.id.setpassword_ok);
        Button bt_cancle = (Button) view.findViewById(R.id.setpassword_cancle);

        final AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pw = et_pw.getText().toString().trim();
                String pwc =et_pwc.getText().toString().trim();

                if(pw.isEmpty()){
                    Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pw.equals(pwc)){
                    SharedPreferences.Editor edit = sp.edit();
                    String pw_md5 = MD5Util.md5(pw);
                    edit.putString("password", pw_md5);
                    edit.apply();
                    Toast.makeText(getApplicationContext(), "密码设置成功", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }else {
                    Toast.makeText(getApplicationContext(), "密码不一致", Toast.LENGTH_SHORT).show();
                }
            }
        });
        bt_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private class GridViewAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 9;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = View.inflate(getApplicationContext(),R.layout.activity_littlehome,null);
            ImageView iv = (ImageView) convertView.findViewById(R.id.icon_iv);
            TextView tv = (TextView) convertView.findViewById(R.id.icon_tv);
            iv.setImageResource(res_icon[position]);
            tv.setText(str_icon[position]);
            return convertView;
        }
    }
}
