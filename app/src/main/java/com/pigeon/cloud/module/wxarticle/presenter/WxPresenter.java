package com.pigeon.cloud.module.wxarticle.presenter;

import com.pigeon.cloud.module.wxarticle.model.WxRequest;
import com.pigeon.cloud.module.wxarticle.view.WxView;

import java.util.List;

import com.pigeon.basic.core.base.BasePresenter;
import per.goweii.rxhttp.request.exception.ExceptionHandle;
import com.pigeon.cloud.http.RequestListener;
import com.pigeon.cloud.module.main.model.ChapterBean;
import com.pigeon.cloud.module.wxarticle.model.WxRequest;
import com.pigeon.cloud.module.wxarticle.view.WxView;
import com.pigeon.cloud.module.wxarticle.model.WxRequest;
import com.pigeon.cloud.module.wxarticle.view.WxView;

/**
 * @author yangzhikuan
 * @date 2019/5/12
 * 
 */
public class WxPresenter extends BasePresenter<WxView> {

    public void getWxArticleChapters() {
        WxRequest.getWxArticleChapters(getRxLife(), new RequestListener<List<ChapterBean>>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int code, List<ChapterBean> data) {
                if (isAttach()) {
                    getBaseView().getWxArticleChaptersSuccess(code, data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getBaseView().getWxArticleChaptersFailed(code, msg);
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
