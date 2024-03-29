package com.pigeon.cloud.module.home.presenter;

import androidx.annotation.IntRange;

import com.pigeon.basic.core.base.BasePresenter;
import com.pigeon.cloud.event.CollectionEvent;
import com.pigeon.cloud.http.RequestListener;
import com.pigeon.cloud.module.home.model.BannerBean;
import com.pigeon.cloud.module.home.model.HomeRequest;
import com.pigeon.cloud.module.home.view.HomeView;
import com.pigeon.cloud.widget.CollectView;

import java.util.List;

import com.pigeon.basic.core.base.BasePresenter;
import per.goweii.rxhttp.request.base.BaseBean;
import per.goweii.rxhttp.request.exception.ExceptionHandle;
import com.pigeon.cloud.event.CollectionEvent;
import com.pigeon.cloud.http.RequestListener;
import com.pigeon.cloud.module.home.model.BannerBean;
import com.pigeon.cloud.module.home.model.HomeRequest;
import com.pigeon.cloud.module.home.view.HomeView;
import com.pigeon.cloud.module.main.model.ArticleBean;
import com.pigeon.cloud.module.main.model.ArticleListBean;
import com.pigeon.cloud.module.main.model.MainRequest;
import com.pigeon.cloud.widget.CollectView;
import com.pigeon.basic.core.base.BasePresenter;
import com.pigeon.cloud.event.CollectionEvent;
import com.pigeon.cloud.http.RequestListener;
import com.pigeon.cloud.module.home.model.BannerBean;
import com.pigeon.cloud.module.home.model.HomeRequest;
import com.pigeon.cloud.module.home.view.HomeView;
import com.pigeon.cloud.widget.CollectView;

/**
 * @author yangzhikuan
 * @date 2019/5/12
 * 
 */
public class HomePresenter extends BasePresenter<HomeView> {

    private boolean isGetBannerSuccess = false;
    private boolean isGetArticleListSuccess = false;
    private boolean isGetTopArticleListSuccess = false;

    private void isAllFailed() {
        if (!isGetBannerSuccess && !isGetArticleListSuccess && !isGetTopArticleListSuccess) {
            if (isAttach()) {
                getBaseView().allFail();
            }
        }
    }

    public void getBanner() {
        HomeRequest.getBanner(getRxLife(), new RequestListener<List<BannerBean>>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int code, List<BannerBean> data) {
                isGetBannerSuccess = true;
                if (isAttach()) {
                    getBaseView().getBannerSuccess(code, data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getBaseView().getBannerFail(code, msg);
                }
            }

            @Override
            public void onError(ExceptionHandle handle) {
            }

            @Override
            public void onFinish() {
                isAllFailed();
            }
        });
    }

    public void getArticleList(@IntRange(from = 0) int page, boolean refresh) {
        HomeRequest.getArticleList(getRxLife(), refresh, page, new RequestListener<ArticleListBean>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int code, ArticleListBean data) {
                isGetArticleListSuccess = true;
                if (isAttach()) {
                    getBaseView().getArticleListSuccess(code, data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getBaseView().getArticleListFailed(code, msg);
                }
            }

            @Override
            public void onError(ExceptionHandle handle) {
            }

            @Override
            public void onFinish() {
                isAllFailed();
            }
        });
    }

    public void getTopArticleList(boolean refresh) {
        HomeRequest.getTopArticleList(getRxLife(), refresh, new RequestListener<List<ArticleBean>>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int code, List<ArticleBean> data) {
                isGetTopArticleListSuccess = true;
                if (isAttach()) {
                    getBaseView().getTopArticleListSuccess(code, data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getBaseView().getTopArticleListFailed(code, msg);
                }
            }

            @Override
            public void onError(ExceptionHandle handle) {
            }

            @Override
            public void onFinish() {
                isAllFailed();
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
