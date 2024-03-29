package com.pigeon.cloud.module.main.presenter;

import com.pigeon.basic.core.base.BasePresenter;
import com.pigeon.cloud.event.ArticleShareEvent;
import com.pigeon.cloud.http.RequestListener;
import com.pigeon.cloud.module.main.model.MainRequest;
import com.pigeon.cloud.module.main.view.ShareArticleView;
import com.pigeon.basic.core.base.BasePresenter;
import per.goweii.rxhttp.request.base.BaseBean;
import per.goweii.rxhttp.request.exception.ExceptionHandle;
import com.pigeon.cloud.event.ArticleShareEvent;
import com.pigeon.cloud.http.RequestListener;
import com.pigeon.cloud.module.main.model.MainRequest;
import com.pigeon.cloud.module.main.view.ShareArticleView;
import com.pigeon.basic.core.base.BasePresenter;
import com.pigeon.cloud.event.ArticleShareEvent;
import com.pigeon.cloud.http.RequestListener;
import com.pigeon.cloud.module.main.model.MainRequest;
import com.pigeon.cloud.module.main.view.ShareArticleView;

/**
 * @author yangzhikuan
 * @date 2019/10/12
 * 
 */
public class ShareArticlePresenter extends BasePresenter<ShareArticleView> {

    public void shareArticle(String title, String link) {
        addToRxLife(MainRequest.shareArticle(title, link, new RequestListener<BaseBean>() {
            @Override
            public void onStart() {
                showLoadingDialog();
            }

            @Override
            public void onSuccess(int code, BaseBean data) {
                new ArticleShareEvent().post();
                if (isAttach()) {
                    getBaseView().shareArticleSuccess(code, data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getBaseView().shareArticleFailed(code, msg);
                }
            }

            @Override
            public void onError(ExceptionHandle handle) {
            }

            @Override
            public void onFinish() {
                dismissLoadingDialog();
            }
        }));
    }
}
