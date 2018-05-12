package com.baidu.cn.vm.version;

/**
 * Created by zhangmeng on 16/5/12.
 */
public interface ProgressObservable {
    void registerObserver(ProgressObserver observer);

    void unregisterObserver(ProgressObserver observer);
}
