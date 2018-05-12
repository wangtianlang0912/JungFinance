package com.baidu.cn.vm.service;

/**
 * Created by yujiangtao on 16/5/10.
 */
public interface ProgressInterface {
    /**
     * 下载结束
     */
    void end();

    /**
     * 下载进度
     * @param p
     */
    void changgeProgress(int p);

    /**
     * 下载错误
     */
    void error();
}
