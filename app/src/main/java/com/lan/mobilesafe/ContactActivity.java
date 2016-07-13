package com.lan.mobilesafe;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lan.mobilesafe.Engine.ContactEngine;
import com.lan.mobilesafe.Util.MyAsyncTask;

import org.w3c.dom.Text;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.HashMap;
import java.util.List;


/**
 * Created by lan on 2016/5/26.
 */
public class ContactActivity extends Activity {
    ListView contact_lv;
    /*Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            contact_lv.setAdapter(new MyAdapter());
            load.setVisibility(View.INVISIBLE);
        }
    };*/
    private List<HashMap<String, String>> contactInfo;
    private ProgressBar load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        contact_lv = (ListView) findViewById(R.id.contact_lv);
        load = (ProgressBar) findViewById(R.id.contact_loading);
        /*new Thread() {
            @Override
            public void run() {
                contactInfo = ContactEngine.getContactInfo(getApplicationContext());
                Message msg = Message.obtain();
                handler.sendMessage(msg);
            }
        }.start();*/
        //自己写的异步加载
        new MyAsyncTask(){

            @Override
            protected void doInBackground() {
                contactInfo = ContactEngine.getContactInfo(ContactActivity.this);
            }

            @Override
            protected void onPreExecute() {

            }

            @Override
            protected void onPostExecute() {
                contact_lv.setAdapter(new MyAdapter());
                load.setVisibility(View.INVISIBLE);
            }
        }.execute();
        //安卓自带的异步加载
        /*new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... params) {
                contactInfo = ContactEngine.getContactInfo(ContactActivity.this);
                return null;
            }

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                contact_lv.setAdapter(new MyAdapter());
                load.setVisibility(View.INVISIBLE);
                super.onPostExecute(aVoid);
            }
        }.execute();*/



        contact_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = getIntent();
                intent.putExtra("phone", contactInfo.get(position).get("phone"));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // System.out.println("-------******--------"+contactInfo.size());
            return contactInfo.size();
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
            convertView = View.inflate(getApplicationContext(), R.layout.activity_littilecontact, null);
            TextView name = (TextView) convertView.findViewById(R.id.contact_name);
            TextView phone = (TextView) convertView.findViewById(R.id.contact_phone);
            name.setText(contactInfo.get(position).get("name"));
            phone.setText(contactInfo.get(position).get("phone").replaceAll("-",""));
            return convertView;
        }
    }
}
