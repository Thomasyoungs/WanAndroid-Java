package com.pigeon.cloud.module.knowledge.fragment;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kennyc.view.MultiStateView;
import com.pigeon.basic.core.utils.listener.SimpleListener;
import com.pigeon.cloud.module.knowledge.activity.KnowledgeArticleActivity;

import java.util.List;

import butterknife.BindView;
import com.pigeon.basic.core.base.BaseFragment;
import com.pigeon.basic.core.toast.ToastMaker;
import com.pigeon.cloud.R;
import com.pigeon.cloud.module.knowledge.adapter.KnowledgeAdapter;
import com.pigeon.cloud.module.knowledge.presenter.KnowledgePresenter;
import com.pigeon.cloud.module.knowledge.view.KnowledgeView;
import com.pigeon.cloud.module.main.model.ChapterBean;
import com.pigeon.cloud.utils.MultiStateUtils;
import com.pigeon.cloud.utils.RvScrollTopUtils;

/**
 * @author yangzhikuan
 * @date 2019/5/12
 * 
 */
public class KnowledgeFragment extends BaseFragment<KnowledgePresenter> implements RvScrollTopUtils.ScrollTop, KnowledgeView {

    @BindView(R.id.msv)
    MultiStateView msv;
    @BindView(R.id.rv)
    RecyclerView rv;

    private KnowledgeAdapter mAdapter;

    public static KnowledgeFragment create() {
        return new KnowledgeFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_knowledge_navigation_child;
    }

    @Nullable
    @Override
    protected KnowledgePresenter initPresenter() {
        return new KnowledgePresenter();
    }

    @Override
    protected void initView() {
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new KnowledgeAdapter();
        mAdapter.setEnableLoadMore(false);
        mAdapter.setOnItemClickListener(new KnowledgeAdapter.OnItemClickListener() {
            @Override
            public void onClick(ChapterBean bean, int pos) {
                KnowledgeArticleActivity.start(getContext(), bean, pos);
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
    public void getKnowledgeListSuccess(int code, List<ChapterBean> data) {
        mAdapter.setNewData(data);
        if (data == null || data.isEmpty()) {
            MultiStateUtils.toEmpty(msv);
        } else {
            MultiStateUtils.toContent(msv);
        }
    }

    @Override
    public void getKnowledgeListFail(int code, String msg) {
        ToastMaker.showShort(msg);
        MultiStateUtils.toError(msv);
    }
}
