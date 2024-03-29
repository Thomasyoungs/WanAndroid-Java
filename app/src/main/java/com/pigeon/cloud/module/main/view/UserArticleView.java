package com.pigeon.cloud.module.main.view;

import com.pigeon.basic.core.base.BaseView;
import com.pigeon.basic.core.base.BaseView;
import com.pigeon.cloud.module.main.model.ArticleListBean;
import com.pigeon.basic.core.base.BaseView;

/**
 * @author yangzhikuan
 * @date 2019/10/3
 *
 */
public interface UserArticleView extends BaseView {
    void getUserArticleListSuccess(int code, ArticleListBean data);

    void getUserArticleListFailed(int code, String msg);
}
