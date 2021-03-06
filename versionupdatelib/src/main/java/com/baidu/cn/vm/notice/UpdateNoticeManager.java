package com.baidu.cn.vm.notice;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by yujiangtao on 16/5/18.
 */
public class UpdateNoticeManager implements UpdateSubject {

    private static UpdateNoticeManager instance = null;

    public static UpdateNoticeManager getInstance() {
        if (instance == null) {
            synchronized (UpdateNoticeManager.class) {
                if (instance == null) {
                    instance = new UpdateNoticeManager();
                }
            }
        }
        return instance;
    }


    private Set<UpdateObserver> observerSet = new HashSet<UpdateObserver>();

    @Override
    public void addObserver(UpdateObserver observer) {
        if (observerSet.contains(observer)) return;
        observerSet.add(observer);
    }

    @Override
    public void removeObserver(UpdateObserver observer) {
        if (observerSet.contains(observer)) observerSet.remove(observer);
    }

    @Override
    public void notifiyObserver(boolean isneedupdate) {
        for (UpdateObserver observer : observerSet) {
            observer.update(isneedupdate);
        }
    }
}
