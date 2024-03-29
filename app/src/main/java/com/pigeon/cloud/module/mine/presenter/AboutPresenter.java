package com.pigeon.cloud.module.mine.presenter;

import com.pigeon.basic.core.base.BasePresenter;
import com.pigeon.cloud.http.RequestListener;
import com.pigeon.cloud.module.main.model.MainRequest;
import com.pigeon.cloud.module.main.model.UpdateBean;
import com.pigeon.cloud.module.mine.view.AboutView;
import com.pigeon.basic.core.base.BasePresenter;
import per.goweii.rxhttp.request.exception.ExceptionHandle;
import com.pigeon.cloud.http.RequestListener;
import com.pigeon.cloud.module.main.model.MainRequest;
import com.pigeon.cloud.module.main.model.UpdateBean;
import com.pigeon.cloud.module.mine.view.AboutView;
import com.pigeon.basic.core.base.BasePresenter;
import com.pigeon.cloud.http.RequestListener;
import com.pigeon.cloud.module.main.model.MainRequest;
import com.pigeon.cloud.module.main.model.UpdateBean;
import com.pigeon.cloud.module.mine.view.AboutView;

/**
 * @author yangzhikuan
 * @date 2019/5/19
 *
 */
public class AboutPresenter extends BasePresenter<AboutView> {

    public void getAppDownloadUrl() {
        MainRequest.update(getRxLife(), new RequestListener<UpdateBean>() {
            @Override
            public void onStart() {
                showLoadingBar();
            }

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

            @Override
            public void onError(ExceptionHandle handle) {
            }

            @Override
            public void onFinish() {
                dismissLoadingBar();
            }
        });
    }

}
