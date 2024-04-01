package com.pigeon.cloud.module.home.presenter;

import com.pigeon.basic.core.base.BasePresenter;
import com.pigeon.basic.core.utils.listener.SimpleCallback;
import com.pigeon.basic.core.utils.listener.SimpleListener;
import com.pigeon.cloud.db.executor.ReadLaterExecutor;
import com.pigeon.cloud.db.model.ReadLaterModel;
import com.pigeon.cloud.event.CollectionEvent;
import com.pigeon.cloud.http.RequestListener;
import com.pigeon.cloud.module.home.view.WebDialogView;
import com.pigeon.cloud.module.main.model.ArticleBean;
import com.pigeon.cloud.module.main.model.MainRequest;
import com.pigeon.cloud.widget.CollectView;

import java.util.List;

import per.goweii.rxhttp.request.base.BaseBean;
import per.goweii.rxhttp.request.exception.ExceptionHandle;

/**
 * @author yangzhikuan
 * @date 2019/8/31
 *
 */
public class WebDialogPresenter extends BasePresenter<WebDialogView> {

    private ReadLaterExecutor mReadLaterExecutor = null;

    @Override
    public void attach(WebDialogView baseView) {
        super.attach(baseView);
        mReadLaterExecutor = new ReadLaterExecutor();
    }

    public void collect(ArticleBean item, final CollectView v) {
        addToRxLife(MainRequest.collectArticle(item.getId(), new RequestListener<BaseBean>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int code, BaseBean data) {
                CollectionEvent.postCollectWithArticleId(item.getId());
                item.setCollect(true);
                if (!v.isChecked()) {
                    v.toggle();
                }
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
                CollectionEvent.postUnCollectWithArticleId(item.getId());
                item.setCollect(false);
                if (v.isChecked()) {
                    v.toggle();
                }
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

    public void addReadLater(String articleUrl, String articleTitle, SimpleCallback<Boolean> callback) {
        mReadLaterExecutor.add(articleUrl, articleTitle, new SimpleCallback<ReadLaterModel>() {
            @Override
            public void onResult(ReadLaterModel data) {
                if (isAttach()) {
                    callback.onResult(true);
                }
            }
        }, new SimpleListener() {
            @Override
            public void onResult() {
                if (isAttach()) {
                    callback.onResult(false);
                }
            }
        });
    }

    public void removeReadLater(String articleUrl, SimpleCallback<Boolean> callback) {
        mReadLaterExecutor.remove(articleUrl, new SimpleListener() {
            @Override
            public void onResult() {
                if (isAttach()) {
                    callback.onResult(true);
                }
            }
        }, new SimpleListener() {
            @Override
            public void onResult() {
                if (isAttach()) {
                    callback.onResult(false);
                }
            }
        });
    }

    public void isReadLater(String articleUrl, SimpleCallback<Boolean> callback) {
        mReadLaterExecutor.findByLink(articleUrl, new SimpleCallback<List<ReadLaterModel>>() {
            @Override
            public void onResult(List<ReadLaterModel> data) {
                if (isAttach()) {
                    callback.onResult(data != null && !data.isEmpty());
                }
            }
        }, new SimpleListener() {
            @Override
            public void onResult() {
                if (isAttach()) {
                    callback.onResult(false);
                }
            }
        });
    }
}
