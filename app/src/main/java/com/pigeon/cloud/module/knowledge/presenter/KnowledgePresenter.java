package com.pigeon.cloud.module.knowledge.presenter;

import java.util.List;

import com.pigeon.basic.core.base.BasePresenter;
import per.goweii.rxhttp.request.exception.ExceptionHandle;
import com.pigeon.cloud.http.RequestListener;
import com.pigeon.cloud.module.knowledge.model.KnowledgeRequest;
import com.pigeon.cloud.module.knowledge.view.KnowledgeView;
import com.pigeon.cloud.module.main.model.ChapterBean;

/**
 * @author yangzhikuan
 * @date 2019/5/12
 * 
 */
public class KnowledgePresenter extends BasePresenter<KnowledgeView> {

    public void getKnowledgeListCache() {
        KnowledgeRequest.getKnowledgeListCache(new RequestListener<List<ChapterBean>>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int code, List<ChapterBean> data) {
                if (isAttach()) {
                    getBaseView().getKnowledgeListSuccess(code, data);
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
        KnowledgeRequest.getKnowledgeList(getRxLife(), new RequestListener<List<ChapterBean>>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int code, List<ChapterBean> data) {
                if (isAttach()) {
                    getBaseView().getKnowledgeListSuccess(code, data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getBaseView().getKnowledgeListFail(code, msg);
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

    public void getKnowledgeListCacheAndNet() {
        KnowledgeRequest.getKnowledgeListCacheAndNet(getRxLife(), new RequestListener<List<ChapterBean>>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int code, List<ChapterBean> data) {
                if (isAttach()) {
                    getBaseView().getKnowledgeListSuccess(code, data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getBaseView().getKnowledgeListFail(code, msg);
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
