package com.pigeon.cloud.module.home.view;

import com.pigeon.basic.core.base.BaseView;

import java.util.List;

import com.pigeon.basic.core.base.BaseView;
import com.pigeon.cloud.module.home.model.BannerBean;
import com.pigeon.cloud.module.main.model.ArticleBean;
import com.pigeon.cloud.module.main.model.ArticleListBean;
import com.pigeon.basic.core.base.BaseView;

/**
 * @author yangzhikuan
 * @date 2019/5/12
 *
 */
public interface HomeView extends BaseView {
    void allFail();

    void getBannerSuccess(int code, List<BannerBean> data);

    void getBannerFail(int code, String msg);

    void getArticleListSuccess(int code, ArticleListBean data);

    void getArticleListFailed(int code, String msg);

    void getTopArticleListSuccess(int code, List<ArticleBean> data);

    void getTopArticleListFailed(int code, String msg);
}
