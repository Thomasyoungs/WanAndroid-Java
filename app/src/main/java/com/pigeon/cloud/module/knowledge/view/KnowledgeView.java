package com.pigeon.cloud.module.knowledge.view;

import java.util.List;

import com.pigeon.basic.core.base.BaseView;
import com.pigeon.cloud.module.main.model.ChapterBean;

/**
 * @author yangzhikuan
 * @date 2019/5/12
 *
 */
public interface KnowledgeView extends BaseView {
    void getKnowledgeListSuccess(int code, List<ChapterBean> data);

    void getKnowledgeListFail(int code, String msg);
}
