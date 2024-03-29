package com.pigeon.cloud.module.navigation.fragment;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kennyc.view.MultiStateView;
import com.pigeon.cloud.module.navigation.adapter.NaviAdapter;
import com.pigeon.cloud.module.navigation.model.NaviBean;
import com.pigeon.cloud.module.navigation.view.NaviView;

import java.util.List;

import butterknife.BindView;
import com.pigeon.basic.core.base.BaseFragment;
import com.pigeon.basic.ui.toast.ToastMaker;
import com.pigeon.basic.utils.listener.SimpleListener;
import com.pigeon.cloud.R;
import com.pigeon.cloud.module.main.model.ArticleBean;
import com.pigeon.cloud.module.navigation.adapter.NaviAdapter;
import com.pigeon.cloud.module.navigation.model.NaviBean;
import com.pigeon.cloud.module.navigation.presenter.NaviPresenter;
import com.pigeon.cloud.module.navigation.view.NaviView;
import com.pigeon.cloud.utils.MultiStateUtils;
import com.pigeon.cloud.utils.RvScrollTopUtils;
import com.pigeon.cloud.utils.UrlOpenUtils;
import com.pigeon.cloud.module.navigation.adapter.NaviAdapter;
import com.pigeon.cloud.module.navigation.model.NaviBean;
import com.pigeon.cloud.module.navigation.view.NaviView;

/**
 * @author yangzhikuan
 * @date 2019/5/12
 * 
 */
public class NaviFragment extends BaseFragment<NaviPresenter> implements RvScrollTopUtils.ScrollTop, NaviView {

    @BindView(R.id.msv)
    MultiStateView msv;
    @BindView(R.id.rv)
    RecyclerView rv;

    private NaviAdapter mAdapter;

    public static NaviFragment create() {
        return new NaviFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_knowledge_navigation_child;
    }

    @Nullable
    @Override
    protected NaviPresenter initPresenter() {
        return new NaviPresenter();
    }

    @Override
    protected void initView() {
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new NaviAdapter();
        mAdapter.setEnableLoadMore(false);
        mAdapter.setOnItemClickListener(new NaviAdapter.OnItemClickListener() {
            @Override
            public void onClick(ArticleBean bean, int pos) {
                UrlOpenUtils.Companion.with(bean).open(getContext());
            }
        });
        rv.setAdapter(mAdapter);
        MultiStateUtils.setEmptyAndErrorClick(msv, new SimpleListener() {
            @Override
            public void onResult() {
                MultiStateUtils.toLoading(msv);
                presenter.getKnowledgeList();
            }
        });
    }

    @Override
    protected void loadData() {
        MultiStateUtils.toLoading(msv);
        presenter.getKnowledgeListCache();
    }

    @Override
    public void onVisible(boolean isFirstVisible) {
        super.onVisible(isFirstVisible);
        if (isFirstVisible) {
            presenter.getKnowledgeList();
        }
    }

    @Override
    public void scrollTop() {
        if (isAdded() && !isDetached()) {
            RvScrollTopUtils.smoothScrollTop(rv);
        }
    }

    @Override
    public void getNaviListSuccess(int code, List<NaviBean> data) {
        mAdapter.setNewData(data);
        if (data == null || data.isEmpty()) {
            MultiStateUtils.toEmpty(msv);
        } else {
            MultiStateUtils.toContent(msv);
        }
    }

    @Override
    public void getNaviListFail(int code, String msg) {
        ToastMaker.showShort(msg);
        MultiStateUtils.toError(msv);
    }
}
