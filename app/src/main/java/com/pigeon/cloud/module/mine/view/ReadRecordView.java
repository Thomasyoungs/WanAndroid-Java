package com.pigeon.cloud.module.mine.view;

import com.pigeon.basic.core.base.BaseView;
import com.pigeon.cloud.db.model.ReadRecordModel;

import java.util.List;

/**
 * @author yangzhikuan
 * @date 2019/5/23
 *
 */
public interface ReadRecordView extends BaseView {
    void getReadRecordListSuccess(List<ReadRecordModel> list);

    void getReadRecordListFailed();

    void removeReadRecordSuccess(String link);

    void removeReadRecordFailed();

    void removeAllReadRecordSuccess();

    void removeAllReadRecordFailed();
}
