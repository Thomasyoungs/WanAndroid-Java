package com.pigeon.cloud.module.home.presenter;

import com.pigeon.basic.core.base.BasePresenter;
import com.pigeon.cloud.http.RequestListener;
import com.pigeon.cloud.module.home.model.HomeRequest;
import com.pigeon.cloud.module.home.model.HotKeyBean;
import com.pigeon.cloud.module.home.view.SearchHistoryView;
import com.pigeon.cloud.utils.SearchHistoryUtils;
import com.pigeon.cloud.utils.SettingUtils;

import java.util.List;

import com.pigeon.basic.core.base.BasePresenter;
import per.goweii.rxhttp.request.exception.ExceptionHandle;
import com.pigeon.cloud.http.RequestListener;
import com.pigeon.cloud.module.home.model.HomeRequest;
import com.pigeon.cloud.module.home.model.HotKeyBean;
import com.pigeon.cloud.module.home.view.SearchHistoryView;
import com.pigeon.cloud.utils.SearchHistoryUtils;
import com.pigeon.cloud.utils.SettingUtils;
import com.pigeon.basic.core.base.BasePresenter;
import com.pigeon.cloud.http.RequestListener;
import com.pigeon.cloud.module.home.model.HomeRequest;
import com.pigeon.cloud.module.home.model.HotKeyBean;
import com.pigeon.cloud.module.home.view.SearchHistoryView;
import com.pigeon.cloud.utils.SearchHistoryUtils;
import com.pigeon.cloud.utils.SettingUtils;

/**
 * @author yangzhikuan
 * @date 2019/5/12
 *
 */
public class SearchHistoryPresenter extends BasePresenter<SearchHistoryView> {

    private final SearchHistoryUtils mSearchHistoryUtils = SearchHistoryUtils.newInstance();

    public void getHotKeyList() {
        HomeRequest.getHotKeyList(getRxLife(), new RequestListener<List<HotKeyBean>>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int code, List<HotKeyBean> data) {
                if (isAttach()) {
                    getBaseView().getHotKeyListSuccess(code, data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getBaseView().getHotKeyListFail(code, msg);
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

    public List<String> getHistory() {
        return mSearchHistoryUtils.get();
    }

    public void saveHistory(List<String> list) {
        List<String> saves = list;
        int max = SettingUtils.getInstance().getSearchHistoryMaxCount();
        if (list != null && list.size() > max) {
            saves = list.subList(0, max - 1);
        }
        mSearchHistoryUtils.save(saves);
    }

}
