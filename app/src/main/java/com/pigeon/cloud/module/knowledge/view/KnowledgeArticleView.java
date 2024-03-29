package com.pigeon.cloud.module.knowledge.view;

import com.pigeon.basic.core.base.BaseView;
import com.pigeon.cloud.module.main.model.ArticleListBean;

/**
 * @author yangzhikuan
 * @date 2019/5/12
 *
 */
public interface KnowledgeArticleView extends BaseView {
    void getKnowledgeArticleListSuccess(int code, ArticleListBean data);

    void getKnowledgeArticleListFail(int code, String msg);
}
