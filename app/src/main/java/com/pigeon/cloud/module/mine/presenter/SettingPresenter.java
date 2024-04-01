package com.pigeon.cloud.module.mine.presenter;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import com.pigeon.basic.core.base.BasePresenter;
import per.goweii.rxhttp.request.base.BaseBean;
import per.goweii.rxhttp.request.exception.ExceptionHandle;

import com.pigeon.basic.core.utils.file.CacheUtils;
import com.pigeon.cloud.http.RequestListener;
import com.pigeon.cloud.http.WanCache;
import com.pigeon.cloud.module.main.model.MainRequest;
import com.pigeon.cloud.module.main.model.UpdateBean;
import com.pigeon.cloud.module.mine.model.MineRequest;
import com.pigeon.cloud.module.mine.view.SettingView;

/**
 * @author yangzhikuan
 * @date 2019/5/19
 * 
 */
public class SettingPresenter extends BasePresenter<SettingView> {

    public void update(boolean click) {
        MainRequest.update(getRxLife(), new RequestListener<UpdateBean>() {
            @Override
            public void onStart() {
                showLoadingBar();
            }

            @Override
            public void onSuccess(int code, UpdateBean data) {
                if (isAttach()) {
                    getBaseView().updateSuccess(code, data, click);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getBaseView().updateFailed(code, msg, click);
                }
            }

            @Override
            public void onError(ExceptionHandle handle) {
            }

            @Override
            public void onFinish() {
                dismissLoadingBar();
            }
        });
    }

    public void betaUpdate(boolean click) {
        MainRequest.betaUpdate(getRxLife(), new RequestListener<UpdateBean>() {
            @Override
            public void onStart() {
                showLoadingBar();
            }

            @Override
            public void onSuccess(int code, UpdateBean data) {
                if (isAttach()) {
                    getBaseView().betaUpdateSuccess(code, data, click);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getBaseView().betaUpdateFailed(code, msg, click);
                }
            }

            @Override
            public void onError(ExceptionHandle handle) {
            }

            @Override
            public void onFinish() {
                dismissLoadingBar();
            }
        });
    }


    public void logout() {
        addToRxLife(MineRequest.logout(new RequestListener<BaseBean>() {
            @Override
            public void onStart() {
                showLoadingBar();
            }

            @Override
            public void onSuccess(int code, BaseBean data) {
                if (isAttach()) {
                    getBaseView().logoutSuccess(code, data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getBaseView().logoutFailed(code, msg);
                }
            }

            @Override
            public void onError(ExceptionHandle handle) {
            }

            @Override
            public void onFinish() {
                dismissLoadingBar();
            }
        }));
    }

    public void getCacheSize() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String size = CacheUtils.getTotalCacheSize();
                if (!emitter.isDisposed()) {
                    emitter.onNext(size);
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                addToRxLife(d);
            }

            @Override
            public void onNext(String size) {
                if (isAttach()) {
                    getBaseView().getCacheSizeSuccess(size);
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

    public void clearCache() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                CacheUtils.clearAllCache();
                String size = CacheUtils.getTotalCacheSize();
                WanCache.getInstance().openDiskLruCache();
                if (!emitter.isDisposed()) {
                    emitter.onNext(size);
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                addToRxLife(d);
            }

            @Override
            public void onNext(String size) {
                if (isAttach()) {
                    getBaseView().getCacheSizeSuccess(size);
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

}
