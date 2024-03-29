package com.pigeon.cloud.module.mine.view;

import com.pigeon.basic.core.base.BaseView;
import com.pigeon.cloud.module.main.model.UpdateBean;
import com.pigeon.basic.core.base.BaseView;
import com.pigeon.cloud.module.main.model.UpdateBean;
import com.pigeon.basic.core.base.BaseView;
import com.pigeon.cloud.module.main.model.UpdateBean;

/**
 * @author yangzhikuan
 * @date 2019/5/19
 * 
 */
public interface AboutView extends BaseView {
    void updateSuccess(int code, UpdateBean data);

    void updateFailed(int code, String msg);
}
