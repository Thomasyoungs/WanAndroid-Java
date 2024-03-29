package com.pigeon.cloud.module.main.presenter;

import com.pigeon.basic.core.base.BasePresenter;
import com.pigeon.cloud.event.CollectionEvent;
import com.pigeon.cloud.http.RequestListener;
import com.pigeon.cloud.module.main.model.ArticleBean;
import com.pigeon.cloud.module.main.model.ArticleListBean;
import com.pigeon.cloud.module.main.model.MainRequest;
import com.pigeon.cloud.module.main.view.UserArticleView;
import com.pigeon.cloud.widget.CollectView;
import com.pigeon.basic.core.base.BasePresenter;
import per.goweii.rxhttp.request.base.BaseBean;
import per.goweii.rxhttp.request.exception.ExceptionHandle;
import com.pigeon.cloud.event.CollectionEvent;
import com.pigeon.cloud.http.RequestListener;
import com.pigeon.cloud.module.main.model.ArticleBean;
import com.pigeon.cloud.module.main.model.ArticleListBean;
import com.pigeon.cloud.module.main.model.MainRequest;
import com.pigeon.cloud.module.main.view.UserArticleView;
import com.pigeon.cloud.widget.CollectView;
import com.pigeon.basic.core.base.BasePresenter;
import com.pigeon.cloud.event.CollectionEvent;
import com.pigeon.cloud.http.RequestListener;
import com.pigeon.cloud.module.main.model.ArticleBean;
import com.pigeon.cloud.module.main.model.ArticleListBean;
import com.pigeon.cloud.module.main.model.MainRequest;
import com.pigeon.cloud.module.main.view.UserArticleView;
import com.pigeon.cloud.widget.CollectView;

/**
 * @author yangzhikuan
 * @date 2019/10/3
 *
 */
public class UserArticlePresenter extends BasePresenter<UserArticleView> {

    public void getUserArticleListCache(int page) {
        MainRequest.getUserArticleListCache(page, new RequestListener<ArticleListBean>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int code, ArticleListBean data) {
                if (isAttach()) {
                    getBaseView().getUserArticleListSuccess(code, data);
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

    public void getUserArticleList(int page, boolean refresh) {
        MainRequest.getUserArticleList(getRxLife(), refresh, page, new RequestListener<ArticleListBean>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int code, ArticleListBean data) {
                if (isAttach()) {
                    getBaseView().getUserArticleListSuccess(code, data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getBaseView().getUserArticleListFailed(code, msg);
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
