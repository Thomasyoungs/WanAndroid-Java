package com.pigeon.cloud.module.main.view;

import com.pigeon.basic.core.base.BaseView;
import com.pigeon.cloud.db.model.ReadLaterModel;
import com.pigeon.cloud.module.main.model.CollectArticleEntity;

/**
 * @author yangzhikuan
 * @date 2019/5/20
 *
 */
public interface WebView extends BaseView {
    void collectSuccess(CollectArticleEntity entity);

    void collectFailed(String msg);

    void uncollectSuccess(CollectArticleEntity entity);

    void uncollectFailed(String msg);

    void isAddedReadLaterSuccess(ReadLaterModel data);
}
