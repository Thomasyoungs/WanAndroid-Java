package com.pigeon.cloud.module.mine.presenter;

import com.pigeon.basic.core.base.BasePresenter;
import com.pigeon.basic.utils.listener.SimpleCallback;
import com.pigeon.basic.utils.listener.SimpleListener;
import com.pigeon.cloud.db.executor.ReadLaterExecutor;
import com.pigeon.cloud.db.model.ReadLaterModel;
import com.pigeon.cloud.module.mine.view.ReadLaterView;

import java.util.List;

/**
 * @author yangzhikuan
 * @date 2019/5/17
 *
 */
public class ReadLaterPresenter extends BasePresenter<ReadLaterView> {

    private ReadLaterExecutor mReadLaterExecutor = null;

    @Override
    public void attach(ReadLaterView baseView) {
        super.attach(baseView);
        mReadLaterExecutor = new ReadLaterExecutor();
    }

    @Override
    public void detach() {
        if (mReadLaterExecutor != null) mReadLaterExecutor.destroy();
        super.detach();
    }

    public void getList(int offset, int perPageCount) {
        if (mReadLaterExecutor == null) return;
        mReadLaterExecutor.getList(offset, perPageCount, new SimpleCallback<List<ReadLaterModel>>() {
            @Override
            public void onResult(List<ReadLaterModel> data) {
                if (isAttach()) {
                    getBaseView().getReadLaterListSuccess(data);
                }
            }
        }, new SimpleListener() {
            @Override
            public void onResult() {
                if (isAttach()) {
                    getBaseView().getReadLaterListFailed();
                }
            }
        });
    }

    public void remove(String link) {
        if (mReadLaterExecutor == null) return;
        mReadLaterExecutor.remove(link, new SimpleListener() {
            @Override
            public void onResult() {
                if (isAttach()) {
                    getBaseView().removeReadLaterSuccess(link);
                }
            }
        }, new SimpleListener() {
            @Override
            public void onResult() {
                if (isAttach()) {
                    getBaseView().removeReadLaterFailed();
                }
            }
        });
    }

    public void removeAll() {
        if (mReadLaterExecutor == null) return;
        mReadLaterExecutor.removeAll(new SimpleListener() {
            @Override
            public void onResult() {
                if (isAttach()) {
                    getBaseView().removeAllReadLaterSuccess();
                }
            }
        }, new SimpleListener() {
            @Override
            public void onResult() {
                if (isAttach()) {
                    getBaseView().removeAllReadLaterFailed();
                }
            }
        });
    }
}
