package com.pigeon.cloud.module.mine.view;

import com.pigeon.basic.core.base.BaseView;
import com.pigeon.cloud.module.main.model.UpdateBean;
import com.pigeon.basic.core.base.BaseView;
import per.goweii.rxhttp.request.base.BaseBean;
import com.pigeon.cloud.module.main.model.UpdateBean;
import com.pigeon.basic.core.base.BaseView;
import com.pigeon.cloud.module.main.model.UpdateBean;

/**
 * @author yangzhikuan
 * @date 2019/5/19
 *
 */
public interface SettingView extends BaseView {
    void updateSuccess(int code, UpdateBean data, boolean click);

    void updateFailed(int code, String msg, boolean click);

    void betaUpdateSuccess(int code, UpdateBean data, boolean click);

    void betaUpdateFailed(int code, String msg, boolean click);

    void logoutSuccess(int code, BaseBean data);

    void logoutFailed(int code, String msg);

    void getCacheSizeSuccess(String size);
}
