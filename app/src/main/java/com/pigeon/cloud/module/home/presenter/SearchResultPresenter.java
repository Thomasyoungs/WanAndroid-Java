package com.pigeon.cloud.module.home.presenter;

import androidx.annotation.IntRange;

import com.pigeon.basic.core.base.BasePresenter;
import com.pigeon.cloud.event.CollectionEvent;
import com.pigeon.cloud.http.RequestListener;
import com.pigeon.cloud.module.home.model.HomeRequest;
import com.pigeon.cloud.module.home.view.SearchResultView;
import com.pigeon.cloud.widget.CollectView;
import com.pigeon.basic.core.base.BasePresenter;
import per.goweii.rxhttp.request.base.BaseBean;
import per.goweii.rxhttp.request.exception.ExceptionHandle;
import com.pigeon.cloud.event.CollectionEvent;
import com.pigeon.cloud.http.RequestListener;
import com.pigeon.cloud.module.home.model.HomeRequest;
import com.pigeon.cloud.module.home.view.SearchResultView;
import com.pigeon.cloud.module.main.model.ArticleBean;
import com.pigeon.cloud.module.main.model.ArticleListBean;
import com.pigeon.cloud.module.main.model.MainRequest;
import com.pigeon.cloud.widget.CollectView;
import com.pigeon.basic.core.base.BasePresenter;
import com.pigeon.cloud.event.CollectionEvent;
import com.pigeon.cloud.http.RequestListener;
import com.pigeon.cloud.module.home.model.HomeRequest;
import com.pigeon.cloud.module.home.view.SearchResultView;
import com.pigeon.cloud.widget.CollectView;

/**
 * @author yangzhikuan
 * @date 2019/5/12
 *
 */
public class SearchResultPresenter extends BasePresenter<SearchResultView> {

    public void search(@IntRange(from = 0) int page, String key) {
        HomeRequest.search(getRxLife(), page, key, new RequestListener<ArticleListBean>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int code, ArticleListBean data) {
                if (isAttach()) {
                    getBaseView().searchSuccess(code, data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getBaseView().searchFailed(code, msg);
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
