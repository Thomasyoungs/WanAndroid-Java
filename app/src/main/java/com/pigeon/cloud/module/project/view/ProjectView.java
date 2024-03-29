package com.pigeon.cloud.module.project.view;

import java.util.List;

import com.pigeon.basic.core.base.BaseView;
import com.pigeon.cloud.module.main.model.ChapterBean;

/**
 * @author yangzhikuan
 * @date 2019/5/12
 *
 */
public interface ProjectView extends BaseView {
    void getProjectChaptersSuccess(int code, List<ChapterBean> data);

    void getProjectChaptersFailed(int code, String msg);
}
