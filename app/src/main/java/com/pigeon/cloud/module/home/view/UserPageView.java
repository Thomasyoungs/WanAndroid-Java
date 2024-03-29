package com.pigeon.cloud.module.home.view;

import com.pigeon.basic.core.base.BaseView;
import com.pigeon.basic.core.base.BaseView;
import com.pigeon.cloud.module.main.model.UserPageBean;
import com.pigeon.basic.core.base.BaseView;

/**
 * @author yangzhikuan
 * @date 2019/10/3
 *
 */
public interface UserPageView extends BaseView {
    void getUserPageSuccess(int code, UserPageBean data);

    void getUserPageFailed(int code, String msg);
}
