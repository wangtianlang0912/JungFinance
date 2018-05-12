package com.baidu.cn.vm.util;

import java.io.Closeable;
import java.io.IOException;


/**
 * Created by yujiangtao on 16/5/13.
 */
public class CloseUtil {
    public static void close(Closeable closecls){
        try {
            closecls.close();
        } catch (IOException e) {
        }
    }
}
