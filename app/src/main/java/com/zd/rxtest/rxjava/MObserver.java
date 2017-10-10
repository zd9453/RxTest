package com.zd.rxtest.rxjava;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * use to
 * <p>
 * Created by zhangdong on 2017/10/10.
 *
 * @version 2.1
 */

class MObserver<T> implements Observer<T> {

    private CallBackInter<T> mD;

    private MObserver() {
    }

    MObserver(CallBackInter<T> mD) {
        this.mD = mD;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull T t) {
        mD.onNext(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        mD.onError(e.getMessage());
    }

    @Override
    public void onComplete() {

    }
}
