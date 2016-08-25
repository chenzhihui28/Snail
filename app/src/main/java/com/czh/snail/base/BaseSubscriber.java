package com.czh.snail.base;


import com.czh.snail.utils.L;

import rx.Subscriber;

/**
 * Created by chenzhihui on 16/8/8.
 */
public abstract class BaseSubscriber<T> extends Subscriber<T> {
    String TAG = getClass().getSimpleName();
    @Override
    public void onCompleted() {

    }


    @Override
    public void onError(Throwable e) {
        if (e != null) {
            L.e(TAG,"onError "+e.toString());
        }
    }

    @Override
    public void onNext(T o) {
        L.e(TAG,"onNext");
    }
}
