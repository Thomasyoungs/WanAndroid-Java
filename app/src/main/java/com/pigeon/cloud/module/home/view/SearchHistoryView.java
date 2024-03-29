package com.pigeon.cloud.module.home.view;

import com.pigeon.basic.core.base.BaseView;

import java.util.List;

import com.pigeon.basic.core.base.BaseView;
import com.pigeon.cloud.module.home.model.HotKeyBean;
import com.pigeon.basic.core.base.BaseView;

/**
 * @author yangzhikuan
 * @date 2019/5/12
 * 
 */
public interface SearchHistoryView extends BaseView {
    void getHotKeyListSuccess(int code, List<HotKeyBean> data);

    void getHotKeyListFail(int code, String msg);
}
