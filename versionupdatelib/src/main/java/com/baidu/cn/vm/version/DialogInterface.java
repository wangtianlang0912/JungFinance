package com.baidu.cn.vm.version;

/**
 * Created by yujiangtao on 16/5/10.
 */
public interface DialogInterface {
    void download();
    void install();
    void cancel(Type type);

    enum Type{
        DEF_CANCEL,UPDATE_CANCEL,INSTALL_CANCEL
    }
}
