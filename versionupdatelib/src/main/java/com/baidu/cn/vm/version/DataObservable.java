package com.baidu.cn.vm.version;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangmeng on 16/5/12.
 */
public class DataObservable implements ProgressObservable {
    private List<ProgressObserver> mObservers = new ArrayList<ProgressObserver>();

    public void notifyChanged(int progress) {
        synchronized (mObservers) {
            for (int i = mObservers.size() - 1; i >= 0; i--) {
                mObservers.get(i).onChanged(progress);
            }
        }
    }

    @Override
    public void registerObserver(ProgressObserver observer) {
        if (observer == null) {
            throw new IllegalArgumentException("The observer is null.");
        }
        synchronized (mObservers) {
            if (mObservers.contains(observer)) {
                throw new IllegalStateException("Observer " + observer + " is already registered.");
            }
            mObservers.add(observer);
        }
    }

    @Override
    public void unregisterObserver(ProgressObserver observer) {
        if (observer == null) {
            throw new IllegalArgumentException("The observer is null.");
        }
        synchronized (mObservers) {
            int index = mObservers.indexOf(observer);
            if (index == -1) {
                throw new IllegalStateException("Observer " + observer + " was not registered.");
            }
            mObservers.remove(index);
        }
    }

    public void unregisterAll() {
        synchronized (mObservers) {
            mObservers.clear();
        }
    }
}
