package com.pigeon.cloud.module.main.view;

import com.pigeon.basic.core.base.BaseView;
import com.pigeon.basic.core.base.BaseView;
import per.goweii.rxhttp.request.base.BaseBean;
import com.pigeon.basic.core.base.BaseView;

/**
 * @author yangzhikuan
 * @date 2019/10/12
 *
 */
public interface ShareArticleView extends BaseView {
    void shareArticleSuccess(int code, BaseBean data);

    void shareArticleFailed(int code, String msg);
}
