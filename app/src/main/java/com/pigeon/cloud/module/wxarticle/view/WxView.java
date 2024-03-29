package com.pigeon.cloud.module.wxarticle.view;

import java.util.List;

import com.pigeon.basic.core.base.BaseView;
import com.pigeon.cloud.module.main.model.ChapterBean;

/**
 * @author yangzhikuan
 * @date 2019/5/12
 *
 */
public interface WxView extends BaseView {
    void getWxArticleChaptersSuccess(int code, List<ChapterBean> data);

    void getWxArticleChaptersFailed(int code, String msg);
}
