package com.pigeon.cloud.module.mine.presenter;

import com.pigeon.basic.core.base.BasePresenter;
import com.pigeon.basic.core.toast.ToastMaker;
import com.pigeon.cloud.event.ArticleDeleteEvent;
import com.pigeon.cloud.event.CollectionEvent;
import com.pigeon.cloud.http.RequestListener;
import com.pigeon.cloud.module.main.model.ArticleBean;
import com.pigeon.cloud.module.main.model.MainRequest;
import com.pigeon.cloud.module.main.model.UserPageBean;
import com.pigeon.cloud.module.mine.model.MineRequest;
import com.pigeon.cloud.module.mine.view.MineShareView;
import com.pigeon.cloud.widget.CollectView;

import per.goweii.rxhttp.request.base.BaseBean;
import per.goweii.rxhttp.request.exception.ExceptionHandle;

/**
 * @author yangzhikuan
 * @date 2019/5/17
 *
 */
public class MineSharePresenter extends BasePresenter<MineShareView> {

    public void getMineShareArticleList(int page, boolean refresh) {
        MineRequest.getMineShareArticleList(getRxLife(), refresh, page, new RequestListener<UserPageBean>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int code, UserPageBean data) {
                if (isAttach()) {
                    getBaseView().getMineShareArticleListSuccess(code, data.getShareArticles());
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getBaseView().getMineShareArticleListFailed(code, msg);
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

    public void deleteMineShareArticle(ArticleBean item) {
        addToRxLife(MineRequest.deleteMineShareArticle(item.getId(), new RequestListener<BaseBean>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int code, BaseBean data) {
                ArticleDeleteEvent.postWithArticleId(item.getId());
            }

            @Override
            public void onFailed(int code, String msg) {
                ToastMaker.showShort(msg);
            }

            @Override
            public void onError(ExceptionHandle handle) {
            }

            @Override
            public void onFinish() {
            }
        }));
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
                if (v.isChecked()) {
                    v.toggle();
                }
                CollectionEvent.postUncollect(item.getOriginId(), item.getId());
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
