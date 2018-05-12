package com.baidu.cn.vm.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by yujiangtao on 16/5/13.
 */
public class HttpUtil {
    public static final int ERRORCODE_SERVER=0;//服务器与客户端不存在此数据通道
    public static final int ERRORCODE_VISIT=1;//访问服务器失败，可能是地址错误，也可是服务器为开启
    public static final int ERRORCODE_URL = 2;//url格式不正确，或者存在中文。
    public static final int ERRORCODE_OTHER=3;//IO异常或者其他

    private int ConnectTimeOut = 15 * 1000;
    private int ReadTimeOut = 10 * 1000;

    public interface RequestCallback{
        void onsuccess(String data);
        void onfialed(int errorcode);
    }

    /**
     * get请求
     * @param apkurl
     * @param callback
     */
    public void request_get(String apkurl,RequestCallback callback) {
        try {
            URL url = new URL(apkurl);
            HttpURLConnection conn = null;
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(ConnectTimeOut);
            conn.setReadTimeout(ReadTimeOut);
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                InputStream input = conn.getInputStream();
                if (input != null) {
                    //拿到流后处理
                    String response = read_is(input);
                    callback.onsuccess(response);
                } else {
                    callback.onfialed(ERRORCODE_SERVER);
                }
            } else {
                callback.onfialed(ERRORCODE_VISIT);
            }
        } catch (MalformedURLException e) {
            callback.onfialed(ERRORCODE_URL);
        } catch (IOException e) {
            callback.onfialed(ERRORCODE_OTHER);
        }
    }

    /**
     * 将inputstream转化为字符串
     * @param stream
     * @return
     */
    private String read_is(InputStream stream) {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            String line = null;
            reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
        } finally {
            if (reader != null)
                CloseUtil.close(reader);
        }
        return sb.toString();
    }

}
