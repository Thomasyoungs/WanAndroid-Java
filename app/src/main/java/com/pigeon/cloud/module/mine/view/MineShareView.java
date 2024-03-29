package com.pigeon.cloud.module.mine.view;

import com.pigeon.basic.core.base.BaseView;
import com.pigeon.cloud.module.main.model.ArticleListBean;
import com.pigeon.basic.core.base.BaseView;
import com.pigeon.cloud.module.main.model.ArticleListBean;
import com.pigeon.basic.core.base.BaseView;
import com.pigeon.cloud.module.main.model.ArticleListBean;

/**
 * @author yangzhikuan
 * @date 2019/5/17
 *
 */
public interface MineShareView extends BaseView {
    void getMineShareArticleListSuccess(int code, ArticleListBean data);

    void getMineShareArticleListFailed(int code, String msg);
}
