package com.pigeon.cloud.module.mine.view;

import com.pigeon.basic.core.base.BaseView;
import com.pigeon.basic.core.base.BaseView;
import com.pigeon.cloud.module.mine.model.AboutMeBean;
import com.pigeon.basic.core.base.BaseView;

/**
 * @author yangzhikuan
 * @date 2019/5/23
 *
 */
public interface AboutMeView extends BaseView {
    void getAboutMeSuccess(int code, AboutMeBean data);

    void getAboutMeFailed(int code, String msg);
}
