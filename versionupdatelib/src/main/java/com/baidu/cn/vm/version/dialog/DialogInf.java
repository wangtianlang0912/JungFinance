package com.baidu.cn.vm.version.dialog;

import com.baidu.cn.vm.version.VersionInfo;

/**
 *
 */
public interface DialogInf {
    DialogInf createDialog(VersionInfo info, int theme);

    void show();
}
