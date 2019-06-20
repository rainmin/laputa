package com.rainmin.common.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class AbsBasePresenter<T extends BaseView> implements BasePresenter {

    protected T mView;
    private CompositeDisposable mCompositeDisposable;

    public void attachView(T view) {
        mView = view;
    }

    public void detachView() {
        mView = null;
        clearRx();
    }

    private void clearRx() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    public void registerRx(Disposable d) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(d);
    }
}
