package com.pigeon.cloud.module.project.view;

import com.pigeon.basic.core.base.BaseView;
import com.pigeon.cloud.module.main.model.ArticleListBean;

/**
 * @author yangzhikuan
 * @date 2019/5/12
 *
 */
public interface ProjectArticleView extends BaseView {
    void getProjectArticleListSuccess(int code, ArticleListBean data);

    void getProjectArticleListFailed(int code, String msg);
}
