package com.pigeon.cloud.module.navigation.presenter;

import com.pigeon.cloud.module.navigation.model.NaviBean;
import com.pigeon.cloud.module.navigation.model.NaviRequest;
import com.pigeon.cloud.module.navigation.view.NaviView;

import java.util.List;

import com.pigeon.basic.core.base.BasePresenter;
import per.goweii.rxhttp.request.exception.ExceptionHandle;
import com.pigeon.cloud.http.RequestListener;
import com.pigeon.cloud.module.navigation.model.NaviBean;
import com.pigeon.cloud.module.navigation.model.NaviRequest;
import com.pigeon.cloud.module.navigation.view.NaviView;
import com.pigeon.cloud.module.navigation.model.NaviBean;
import com.pigeon.cloud.module.navigation.model.NaviRequest;
import com.pigeon.cloud.module.navigation.view.NaviView;

/**
 * @author yangzhikuan
 * @date 2019/5/12
 *
 */
public class NaviPresenter extends BasePresenter<NaviView> {

    public void getKnowledgeListCache() {
        NaviRequest.getNaviListCache(new RequestListener<List<NaviBean>>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int code, List<NaviBean> data) {
                if (isAttach()) {
                    getBaseView().getNaviListSuccess(code, data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
            }

            @Override
            public void onError(ExceptionHandle handle) {
            }

            @Override
            public void onFinish() {
            }
        });
    }

    public void getKnowledgeList() {
        NaviRequest.getNaviList(getRxLife(), new RequestListener<List<NaviBean>>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int code, List<NaviBean> data) {
                if (isAttach()) {
                    getBaseView().getNaviListSuccess(code, data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getBaseView().getNaviListFail(code, msg);
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
}
