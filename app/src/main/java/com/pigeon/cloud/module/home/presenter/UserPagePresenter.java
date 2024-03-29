package com.pigeon.cloud.module.home.presenter;

import com.pigeon.basic.core.base.BasePresenter;
import com.pigeon.cloud.event.CollectionEvent;
import com.pigeon.cloud.http.RequestListener;
import com.pigeon.cloud.module.home.view.UserPageView;
import com.pigeon.cloud.widget.CollectView;
import com.pigeon.basic.core.base.BasePresenter;
import per.goweii.rxhttp.request.base.BaseBean;
import per.goweii.rxhttp.request.exception.ExceptionHandle;
import com.pigeon.cloud.event.CollectionEvent;
import com.pigeon.cloud.http.RequestListener;
import com.pigeon.cloud.module.home.view.UserPageView;
import com.pigeon.cloud.module.main.model.ArticleBean;
import com.pigeon.cloud.module.main.model.MainRequest;
import com.pigeon.cloud.module.main.model.UserPageBean;
import com.pigeon.cloud.widget.CollectView;
import com.pigeon.basic.core.base.BasePresenter;
import com.pigeon.cloud.event.CollectionEvent;
import com.pigeon.cloud.http.RequestListener;
import com.pigeon.cloud.module.home.view.UserPageView;
import com.pigeon.cloud.widget.CollectView;

/**
 * @author yangzhikuan
 * @date 2019/10/3
 * 
 */
public class UserPagePresenter extends BasePresenter<UserPageView> {

    public void getUserPage(int userId, int page, boolean refresh) {
        MainRequest.getUserPage(getRxLife(), refresh, userId, page, new RequestListener<UserPageBean>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int code, UserPageBean data) {
                if (isAttach()) {
                    getBaseView().getUserPageSuccess(code, data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getBaseView().getUserPageFailed(code, msg);
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

    public void collect(ArticleBean item, final CollectView v) {
        addToRxLife(MainRequest.collectArticle(item.getId(), new RequestListener<BaseBean>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int code, BaseBean data) {
                item.setCollect(true);
                if (!v.isChecked()) {
                    v.toggle();
                }
                CollectionEvent.postCollectWithArticleId(item.getId());
            }

            @Override
            public void onFailed(int code, String msg) {
                if (v.isChecked()) {
                    v.toggle();
                }
            }

            @Override
            public void onError(ExceptionHandle handle) {
            }

            @Override
            public void onFinish() {
            }
        }));
    }

    public void uncollect(ArticleBean item, final CollectView v) {
        addToRxLife(MainRequest.uncollectArticle(item.getId(), new RequestListener<BaseBean>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int code, BaseBean data) {
                item.setCollect(false);
                if (v.isChecked()) {
                    v.toggle();
                }
                CollectionEvent.postUnCollectWithArticleId(item.getId());
            }

            @Override
            public void onFailed(int code, String msg) {
                if (!v.isChecked()) {
                    v.toggle();
                }
            }

            @Override
            public void onError(ExceptionHandle handle) {
            }

            @Override
            public void onFinish() {
            }
        }));
    }
}
