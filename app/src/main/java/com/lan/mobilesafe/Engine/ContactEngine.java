package com.lan.mobilesafe.Engine;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lan on 2016/6/15.
 */
public class ContactEngine {
    //视频里的方法导出的联系人和手机里真正存在的联系人有差异，而且单元测试报错，在下能力有限，暂时无法解决
    public static List<HashMap<String, String>> getContactInfo(Context context) {
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        //得到内容解析者
        ContentResolver contentResolver = context.getContentResolver();
        Uri raw_uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri date_uri = Uri.parse("content://com.android.contacts/data");
        Cursor c = contentResolver.query(raw_uri, new String[]{"contact_id"}, null, null, null);
        while (c.moveToNext()) {
            String contact_id = c.getString(0);
            //cursor.getString(cursor.getColumnIndex("contact_id"));//getColumnIndex : 查询字段在cursor中索引值,一般都是用在查询字段比较多的时候
            //7.根据contact_id查询view_data表中的数据
            //selection : 查询条件
            //selectionArgs :查询条件的参数
            //sortOrder : 排序
            Cursor cursor = contentResolver.query(date_uri, new String[]{"data1", "mimetype"}, "raw_contact_id=?", new String[]{contact_id}, null);
            HashMap<String, String> map = new HashMap<>();
            if (contact_id != null) {
                while (cursor.moveToNext()) {
                    String data1 = cursor.getString(cursor.getColumnIndex("data1"));
                    String mimetype = cursor.getString(cursor.getColumnIndex("mimetype"));
                    //根据类型去判断获取的data1数据并保存
                    if (mimetype.equals("vnd.android.cursor.item/phone_v2") && !mimetype.isEmpty()) {
                        //电话
                       // System.out.println("----" + data1);
                        map.put("phone", data1);
                    } else if (mimetype.equals("vnd.android.cursor.item/name") && !mimetype.isEmpty()) {
                        //姓名
                       // System.out.println("``````" + data1);
                        map.put("name", data1);
                    }
                }
            }
            if (!map.isEmpty())
                list.add(map);
            if (!cursor.isClosed())
                cursor.close();
        }
        if (!c.isClosed())
            c.close();
        return list;
    }

    /*public  static String getContactPhone(Cursor cursor,Context context) {
phoneColumn 有时会报错
        int phoneColumn = cursor
                .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
        int phoneNum = cursor.getInt(phoneColumn);
        String result = "";
        if (phoneNum > 0) {
            // 获得联系人的ID号
            int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            String contactId = cursor.getString(idColumn);
            // 获得联系人电话的cursor
            Cursor phone = context.getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="
                            + contactId, null, null);
            if (phone.moveToFirst()) {
                for (; !phone.isAfterLast(); phone.moveToNext()) {
                    int index = phone
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    int typeindex = phone
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
                    int phone_type = phone.getInt(typeindex);
                    String phoneNumber = phone.getString(index);
                    result = phoneNumber;
//                  switch (phone_type) {//此处请看下方注释
//                  case 2:
//                      result = phoneNumber;
//                      break;
//
//                  default:
//                      break;
//                  }
                }
                if (!phone.isClosed()) {
                    phone.close();
                }
            }
        }
        return result;
    }*/
}
