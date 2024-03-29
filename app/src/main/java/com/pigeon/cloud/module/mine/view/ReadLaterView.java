package com.pigeon.cloud.module.mine.view;

import com.pigeon.basic.core.base.BaseView;
import com.pigeon.cloud.db.model.ReadLaterModel;

import java.util.List;

/**
 * @author yangzhikuan
 * @date 2019/5/23
 * 
 */
public interface ReadLaterView extends BaseView {
    void getReadLaterListSuccess(List<ReadLaterModel> list);

    void getReadLaterListFailed();

    void removeReadLaterSuccess(String link);

    void removeReadLaterFailed();

    void removeAllReadLaterSuccess();

    void removeAllReadLaterFailed();
}
