package com.pigeon.cloud.module.main.view;

import com.pigeon.basic.core.base.BaseView;
import com.pigeon.cloud.db.model.ReadLaterModel;
import com.pigeon.cloud.module.main.model.AdvertBean;
import com.pigeon.cloud.module.main.model.ConfigBean;
import com.pigeon.cloud.module.main.model.UpdateBean;

/**
 * @author yangzhikuan
 * @date 2019/5/19
 * 
 */
public interface MainView extends BaseView {
    void updateSuccess(int code, UpdateBean data);

    void updateFailed(int code, String msg);

    void betaUpdateSuccess(int code, UpdateBean data);

    void betaUpdateFailed(int code, String msg);

    void getConfigSuccess(ConfigBean configBean);

    void getConfigFailed(int code, String msg);

    void newThemeFounded();

    void getAdvertSuccess(int code, AdvertBean advertBean);

    void getAdvertFailed(int code, String msg);

    void getReadLaterArticleSuccess(ReadLaterModel readLaterModel);

    void getReadLaterArticleFailed();

}
