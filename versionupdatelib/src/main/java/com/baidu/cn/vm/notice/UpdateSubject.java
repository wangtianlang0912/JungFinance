package com.baidu.cn.vm.notice;

/**
 * Created by yujiangtao on 16/5/18.
 */
public interface UpdateSubject {
    /**
     * 添加监听对象
     * @param observer
     */
    void addObserver(UpdateObserver observer);

    /**
     * 删除监听对象
     * @param observer
     */
    void removeObserver(UpdateObserver observer);

    /**
     * 通知所有监听对象
     * @param isneedupdate
     */
    void notifiyObserver(boolean isneedupdate);
}
