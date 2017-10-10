package com.zd.rxtest.rxjava;

/**
 * use to 成功的失败的回调
 * <p>
 * Created by zhangdong on 2017/10/10.
 *
 * @version 2.1
 */

interface CallBackInter<T> {

    void onError(String msg);

    void onNext(T model);
}
