package com.pigeon.cloud.module.home.view;

import com.pigeon.basic.core.base.BaseView;
import com.pigeon.basic.core.base.BaseView;
import com.pigeon.cloud.module.main.model.ArticleListBean;
import com.pigeon.basic.core.base.BaseView;

/**
 * @author yangzhikuan
 * @date 2019/5/12
 *
 */
public interface SearchResultView extends BaseView {
    void searchSuccess(int code, ArticleListBean data);

    void searchFailed(int code, String msg);
}
