package com.pigeon.cloud.module.mine.view;

import com.pigeon.basic.core.base.BaseView;
import com.pigeon.basic.core.base.BaseView;
import com.pigeon.cloud.module.mine.model.UserInfoBean;
import com.pigeon.basic.core.base.BaseView;

/**
 * @author yangzhikuan
 * @date 2019/5/12
 * 
 */
public interface MineView extends BaseView {
    void getUserInfoSuccess(int code, UserInfoBean coin);

    void getUserInfoFail(int code, String msg);

    void getMessageUnreadCountSuccess(int count);
}
