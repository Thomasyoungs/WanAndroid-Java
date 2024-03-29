package com.pigeon.cloud.module.project.presenter;

import com.pigeon.cloud.module.project.model.ProjectRequest;
import com.pigeon.cloud.module.project.view.ProjectView;

import java.util.List;

import com.pigeon.basic.core.base.BasePresenter;
import per.goweii.rxhttp.request.exception.ExceptionHandle;
import com.pigeon.cloud.http.RequestListener;
import com.pigeon.cloud.module.main.model.ChapterBean;
import com.pigeon.cloud.module.project.model.ProjectRequest;
import com.pigeon.cloud.module.project.view.ProjectView;
import com.pigeon.cloud.module.project.model.ProjectRequest;
import com.pigeon.cloud.module.project.view.ProjectView;

/**
 * @author yangzhikuan
 * @date 2019/5/12
 *
 */
public class ProjectPresenter extends BasePresenter<ProjectView> {

    public void getProjectChapters() {
        ProjectRequest.getProjectChapters(getRxLife(), new RequestListener<List<ChapterBean>>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int code, List<ChapterBean> data) {
                if (isAttach()) {
                    getBaseView().getProjectChaptersSuccess(code, data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getBaseView().getProjectChaptersFailed(code, msg);
                }
            }

            @Override
            public void onError(ExceptionHandle handle) {
            }

            @Override
            public void onFinish() {
            }
        });
    }
}
