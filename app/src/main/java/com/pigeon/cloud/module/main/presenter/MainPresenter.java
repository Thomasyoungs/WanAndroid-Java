package com.pigeon.cloud.module.main.presenter;

import com.pigeon.basic.core.base.BasePresenter;
import com.pigeon.basic.utils.listener.SimpleCallback;
import com.pigeon.basic.utils.listener.SimpleListener;
import com.pigeon.cloud.db.executor.ReadLaterExecutor;
import com.pigeon.cloud.db.model.ReadLaterModel;
import com.pigeon.cloud.http.RequestCallback;
import com.pigeon.cloud.http.RequestListener;
import com.pigeon.cloud.module.main.model.AdvertBean;
import com.pigeon.cloud.module.main.model.ConfigBean;
import com.pigeon.cloud.module.main.model.MainRequest;
import com.pigeon.cloud.module.main.model.UpdateBean;
import com.pigeon.cloud.module.main.view.MainView;
import com.pigeon.cloud.utils.ConfigUtils;

import java.util.List;

import per.goweii.rxhttp.request.exception.ExceptionHandle;

/**
 * @author yangzhikuan
 * @date 2019/5/19
 * 
 */
public class MainPresenter extends BasePresenter<MainView> {

    private ReadLaterExecutor mReadLaterExecutor = null;

    @Override
    public void attach(MainView baseView) {
        super.attach(baseView);
        mReadLaterExecutor = new ReadLaterExecutor();
    }

    @Override
    public void detach() {
        if (mReadLaterExecutor != null) mReadLaterExecutor.destroy();
        super.detach();
    }

    public void update() {
        MainRequest.update(getRxLife(), new RequestCallback<UpdateBean>() {
            @Override
            public void onSuccess(int code, UpdateBean data) {
                if (isAttach()) {
                    getBaseView().updateSuccess(code, data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getBaseView().updateFailed(code, msg);
                }
            }
        });
    }

    public void betaUpdate() {
        MainRequest.betaUpdate(getRxLife(), new RequestCallback<UpdateBean>() {
            @Override
            public void onSuccess(int code, UpdateBean data) {
                if (isAttach()) {
                    getBaseView().betaUpdateSuccess(code, data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getBaseView().betaUpdateFailed(code, msg);
                }
            }
        });
    }

    public void getConfig() {
        MainRequest.getConfig(getRxLife(), new RequestListener<ConfigBean>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int code, ConfigBean data) {
                if (isAttach()) {
                    getBaseView().getConfigSuccess(data);
                }
                int oldTheme = ConfigUtils.getInstance().getTheme();
                ConfigUtils.getInstance().setConfig(data);
                int newTheme = ConfigUtils.getInstance().getTheme();
                if (oldTheme != newTheme) {
                    if (isAttach()) {
                        getBaseView().newThemeFounded();
                    }
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getBaseView().getConfigFailed(code, msg);
                }
            }

            @Override
            public void onError(ExceptionHandle handle) {
            }

            @Override
            public void onFinish() {
            }
        });
    }

    public void getAdvert() {
        MainRequest.getAdvert(getRxLife(), new RequestCallback<AdvertBean>() {
            @Override
            public void onSuccess(int code, AdvertBean data) {
                if (isAttach()) {
                    getBaseView().getAdvertSuccess(code, data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getBaseView().getAdvertFailed(code, msg);
                }
            }
        });
    }

    public void getReadLaterArticle() {
        mReadLaterExecutor.findLately(1, new SimpleCallback<List<ReadLaterModel>>() {
            @Override
            public void onResult(List<ReadLaterModel> data) {
                if (!data.isEmpty()) {
                    if (isAttach()) {
                        getBaseView().getReadLaterArticleSuccess(data.get(0));
                    }
                } else {
                    if (isAttach()) {
                        getBaseView().getReadLaterArticleFailed();
                    }
                }
            }
        }, new SimpleListener() {
            @Override
            public void onResult() {
                if (isAttach()) {
                    getBaseView().getReadLaterArticleFailed();
                }
            }
        });
    }

}
