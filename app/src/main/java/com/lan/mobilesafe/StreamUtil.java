package com.lan.mobilesafe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

/**
 * Created by lan on 2016/5/25.
 */
public class StreamUtil {
    public static String parseInputStream(InputStream is) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(is));
        StringWriter sw = new StringWriter();
        String str;
        while((str = bf.readLine()) != null){
            sw.write(str);
        }
        return sw.toString();
    }
}
