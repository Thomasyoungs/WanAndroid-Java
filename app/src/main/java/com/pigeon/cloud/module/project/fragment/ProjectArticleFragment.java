package com.pigeon.cloud.module.project.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kennyc.view.MultiStateView;
import com.pigeon.basic.core.utils.listener.SimpleListener;
import com.pigeon.cloud.module.project.view.ProjectArticleView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import com.pigeon.basic.core.base.BaseFragment;
import com.pigeon.basic.core.utils.SmartRefreshUtils;
import com.pigeon.basic.core.toast.ToastMaker;
import com.pigeon.cloud.R;
import com.pigeon.cloud.event.CollectionEvent;
import com.pigeon.cloud.event.LoginEvent;
import com.pigeon.cloud.event.ScrollTopEvent;
import com.pigeon.cloud.module.main.adapter.ArticleAdapter;
import com.pigeon.cloud.module.main.model.ArticleBean;
import com.pigeon.cloud.module.main.model.ArticleListBean;
import com.pigeon.cloud.module.main.model.ChapterBean;
import com.pigeon.cloud.module.project.presenter.ProjectArticlePresenter;
import com.pigeon.cloud.utils.MultiStateUtils;
import com.pigeon.cloud.utils.RvScrollTopUtils;
import com.pigeon.cloud.utils.UrlOpenUtils;
import com.pigeon.cloud.widget.CollectView;

/**
 * @author yangzhikuan
 * @date 2019/5/12
 *
 */
public class ProjectArticleFragment extends BaseFragment<ProjectArticlePresenter> implements ProjectArticleView {

    private static final int PAGE_START = 1;

    @BindView(R.id.msv)
    MultiStateView msv;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    @BindView(R.id.rv)
    RecyclerView rv;

    private SmartRefreshUtils mSmartRefreshUtils;
    private ArticleAdapter mAdapter;

    private ChapterBean mChapterBean;
    private int mPosition = -1;

    private int currPage = PAGE_START;

    public static ProjectArticleFragment create(ChapterBean chapterBean, int position) {
        ProjectArticleFragment fragment = new ProjectArticleFragment();
        Bundle args = new Bundle(2);
        args.putSerializable("chapterBean", chapterBean);
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCollectionEvent(CollectionEvent event) {
        if (isDetached()) {
            return;
        }
        if (event.getArticleId() == -1) {
            return;
        }
        mAdapter.notifyCollectionEvent(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent event) {
        if (isDetached()) {
            return;
        }
        if (event.isLogin()) {
            currPage = PAGE_START;
            getProjectArticleList(true);
        } else {
            mAdapter.notifyAllUnCollect();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onScrollTopEvent(ScrollTopEvent event) {
        if (!getClass().equals(event.getClazz())) {
            return;
        }
        if (mPosition != event.getPosition()) {
            return;
        }
        if (isAdded() && !isDetached()) {
            RvScrollTopUtils.smoothScrollTop(rv);
        }
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_project_article;
    }

    @Nullable
    @Override
    protected ProjectArticlePresenter initPresenter() {
        return new ProjectArticlePresenter();
    }

    @Override
    protected void initView() {
        Bundle args = getArguments();
        if (args != null) {
            mChapterBean = (ChapterBean) args.getSerializable("chapterBean");
            mPosition = args.getInt("position", -1);
        }

        mSmartRefreshUtils = SmartRefreshUtils.with(srl);
        mSmartRefreshUtils.pureScrollMode();
        mSmartRefreshUtils.setRefreshListener(new SmartRefreshUtils.RefreshListener() {
            @Override
            public void onRefresh() {
                currPage = PAGE_START;
                getProjectArticleList(true);
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ArticleAdapter();
        mAdapter.setEnableLoadMore(false);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                currPage++;
                getProjectArticleList(false);
            }
        }, rv);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ArticleBean item = mAdapter.getItem(position);
                if (item != null) {
                    UrlOpenUtils.Companion.with(item).open(getContext());
                }
            }
        });
        mAdapter.setOnItemChildViewClickListener(new ArticleAdapter.OnItemChildViewClickListener() {
            @Override
            public void onCollectClick(BaseViewHolder helper, CollectView v, int position) {
                ArticleBean item = mAdapter.getItem(position);
                if (item != null) {
                    if (v.isChecked()) {
                        presenter.collect(item, v);
                    } else {
                        presenter.uncollect(item, v);
                    }
                }
            }
        });
        rv.setAdapter(mAdapter);
        MultiStateUtils.setEmptyAndErrorClick(msv, new SimpleListener() {
            @Override
            public void onResult() {
                MultiStateUtils.toLoading(msv);
                currPage = PAGE_START;
                getProjectArticleList(false);
            }
        });
    }

    @Override
    protected void loadData() {
        if (mChapterBean != null) {
            MultiStateUtils.toLoading(msv);
            presenter.getProjectArticleListCache(mChapterBean.getId(), currPage);
        } else {
            MultiStateUtils.toError(msv);
        }
    }

    @Override
    public void onVisible(boolean isFirstVisible) {
        super.onVisible(isFirstVisible);
        if (isFirstVisible) {
            currPage = PAGE_START;
            getProjectArticleList(false);
        }
    }

    public void getProjectArticleList(boolean refresh) {
        if (mChapterBean != null) {
            presenter.getProjectArticleList(mChapterBean.getId(), currPage, true);
        }
    }

    @Override
    public void getProjectArticleListSuccess(int code, ArticleListBean data) {
        currPage = data.getCurPage() + PAGE_START;
        if (data.getCurPage() == 1) {
            mAdapter.setNewData(data.getDatas());
            mAdapter.setEnableLoadMore(true);
            if (data.getDatas() == null || data.getDatas().isEmpty()) {
                MultiStateUtils.toEmpty(msv);
            } else {
                MultiStateUtils.toContent(msv);
            }
        } else {
            mAdapter.addData(data.getDatas());
            mAdapter.loadMoreComplete();
        }
        if (data.isOver()) {
            mAdapter.loadMoreEnd();
        }
        mSmartRefreshUtils.success();
    }

    @Override
    public void getProjectArticleListFailed(int code, String msg) {
        ToastMaker.showShort(msg);
        mSmartRefreshUtils.fail();
        mAdapter.loadMoreFail();
        if (currPage == PAGE_START) {
            MultiStateUtils.toError(msv);
        }
    }
}
