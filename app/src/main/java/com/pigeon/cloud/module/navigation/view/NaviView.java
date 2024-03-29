package com.pigeon.cloud.module.navigation.view;

import java.util.List;

import com.pigeon.basic.core.base.BaseView;
import com.pigeon.cloud.module.navigation.model.NaviBean;

/**
 * @author yangzhikuan
 * @date 2019/5/12
 * 
 */
public interface NaviView extends BaseView {
    void getNaviListSuccess(int code, List<NaviBean> data);

    void getNaviListFail(int code, String msg);
}
