package com.pigeon.cloud.module.mine.view;

import com.pigeon.basic.core.base.BaseView;
import com.pigeon.cloud.module.main.model.CollectionLinkBean;

import java.util.List;

import com.pigeon.basic.core.base.BaseView;
import com.pigeon.cloud.module.main.model.CollectionLinkBean;
import com.pigeon.basic.core.base.BaseView;
import com.pigeon.cloud.module.main.model.CollectionLinkBean;

/**
 * @author yangzhikuan
 * @date 2019/5/17
 * 
 */
public interface CollectionLinkView extends BaseView {
    void getCollectLinkListSuccess(int code, List<CollectionLinkBean> data);

    void getCollectLinkListFailed(int code, String msg);

    void updateCollectLinkSuccess(int code, CollectionLinkBean data);
}
