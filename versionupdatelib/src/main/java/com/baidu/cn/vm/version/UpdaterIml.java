package com.baidu.cn.vm.version;


/**
 *
 */
public class UpdaterIml implements Updater, ProgressObservable {
    private DataObservable mObservable = new DataObservable();

    @Override
    public void update(int process) {
        mObservable.notifyChanged(process);
        if (process == 100) {
            mObservable.unregisterAll();
        }
    }

    @Override
    public void registerObserver(ProgressObserver observer) {
        mObservable.registerObserver(observer);
    }

    @Override
    public void unregisterObserver(ProgressObserver observer) {
        mObservable.unregisterObserver(observer);
    }

}
