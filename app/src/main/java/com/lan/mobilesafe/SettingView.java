package com.lan.mobilesafe;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by lan on 2016/6/6.
 */
public class SettingView extends RelativeLayout {

    private CheckBox setting_cb_update;
    private TextView setting_tv_des;
    private TextView setting_tv_title;
    private String des_off;
    private String des_on;

    public SettingView(Context context) {
        super(context);
        init();
    }

    public SettingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        String title = attrs.getAttributeValue("http://schemas.android.com/apk/com.lan.mobilesafe", "titles");
        des_on = attrs.getAttributeValue("http://schemas.android.com/apk/com.lan.mobilesafe", "des_on");
        des_off = attrs.getAttributeValue("http://schemas.android.com/apk/com.lan.mobilesafe", "des_off");
        init();
        setTitle(title);
    }

    public SettingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    public SettingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    void init() {
        View view = View.inflate(getContext(), R.layout.activity_littilesetting, this);
        setting_tv_title = (TextView) view.findViewById(R.id.setting_tv_title);
        setting_tv_des = (TextView) view.findViewById(R.id.setting_tv_des);
        setting_cb_update = (CheckBox) view.findViewById(R.id.setting_cb_update);
        setting_cb_update.setClickable(false);
        setting_cb_update.setFocusable(false);
    }

    void setTitle(String title) {
        setting_tv_title.setText(title);
    }

    void setdes(String des) {
        setting_tv_des.setText(des);
    }

    void setBoxChecked(boolean checked) {
        setting_cb_update.setChecked(checked);
        if (setting_cb_update.isChecked()) {
            setdes(des_on);
        } else {
            setdes(des_off);
        }
    }


    boolean boxisChecked() {
        return setting_cb_update.isChecked();
    }
}
