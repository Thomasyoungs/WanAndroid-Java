package com.pigeon.cloud.module.mine.presenter;

import com.pigeon.basic.core.base.BasePresenter;
import com.pigeon.basic.utils.listener.SimpleCallback;
import com.pigeon.basic.utils.listener.SimpleListener;
import com.pigeon.cloud.db.executor.ReadRecordExecutor;
import com.pigeon.cloud.db.model.ReadRecordModel;
import com.pigeon.cloud.module.mine.view.ReadRecordView;

import java.util.List;

/**
 * @author yangzhikuan
 * @date 2019/5/17
 *
 */
public class ReadRecordPresenter extends BasePresenter<ReadRecordView> {

    private ReadRecordExecutor mReadRecordExecutor = null;

    @Override
    public void attach(ReadRecordView baseView) {
        super.attach(baseView);
        mReadRecordExecutor = new ReadRecordExecutor();
    }

    @Override
    public void detach() {
        if (mReadRecordExecutor != null) mReadRecordExecutor.destroy();
        super.detach();
    }

    public void getList(int offset, int perPageCount) {
        if (mReadRecordExecutor == null) return;
        mReadRecordExecutor.getList(offset, perPageCount, new SimpleCallback<List<ReadRecordModel>>() {
            @Override
            public void onResult(List<ReadRecordModel> data) {
                if (isAttach()) {
                    getBaseView().getReadRecordListSuccess(data);
                }
            }
        }, new SimpleListener() {
            @Override
            public void onResult() {
                if (isAttach()) {
                    getBaseView().getReadRecordListFailed();
                }
            }
        });
    }

    public void remove(String link) {
        if (mReadRecordExecutor == null) return;
        mReadRecordExecutor.remove(link, new SimpleListener() {
            @Override
            public void onResult() {
                if (isAttach()) {
                    getBaseView().removeReadRecordSuccess(link);
                }
            }
        }, new SimpleListener() {
            @Override
            public void onResult() {
                if (isAttach()) {
                    getBaseView().removeReadRecordFailed();
                }
            }
        });
    }

    public void removeAll() {
        if (mReadRecordExecutor == null) return;
        mReadRecordExecutor.removeAll(new SimpleListener() {
            @Override
            public void onResult() {
                if (isAttach()) {
                    getBaseView().removeAllReadRecordSuccess();
                }
            }
        }, new SimpleListener() {
            @Override
            public void onResult() {
                if (isAttach()) {
                    getBaseView().removeAllReadRecordFailed();
                }
            }
        });
    }
}
