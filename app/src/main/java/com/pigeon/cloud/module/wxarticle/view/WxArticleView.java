package com.pigeon.cloud.module.wxarticle.view;

import com.pigeon.basic.core.base.BaseView;
import com.pigeon.cloud.module.main.model.ArticleListBean;

/**
 * @author yangzhikuan
 * @date 2019/5/12
 *
 */
public interface WxArticleView extends BaseView {
    void getWxArticleListSuccess(int code, ArticleListBean data);

    void getWxArticleListFailed(int code, String msg);

    void getWxArticleListSearchSuccess(int code, ArticleListBean data);

    void getWxArticleListSearchFailed(int code, String msg);
}
