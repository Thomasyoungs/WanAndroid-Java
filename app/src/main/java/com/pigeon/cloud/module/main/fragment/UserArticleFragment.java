package com.pigeon.cloud.module.main.fragment;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kennyc.view.MultiStateView;
import com.pigeon.basic.core.base.BaseFragment;
import com.pigeon.basic.core.utils.SmartRefreshUtils;
import com.pigeon.basic.ui.toast.ToastMaker;
import com.pigeon.basic.utils.listener.SimpleListener;
import com.pigeon.cloud.R;
import com.pigeon.cloud.common.Config;
import com.pigeon.cloud.event.ArticleShareEvent;
import com.pigeon.cloud.event.CollectionEvent;
import com.pigeon.cloud.event.LoginEvent;
import com.pigeon.cloud.module.main.activity.ShareArticleActivity;
import com.pigeon.cloud.module.main.adapter.ArticleAdapter;
import com.pigeon.cloud.module.main.model.ArticleBean;
import com.pigeon.cloud.module.main.model.ArticleListBean;
import com.pigeon.cloud.module.main.presenter.UserArticlePresenter;
import com.pigeon.cloud.module.main.view.UserArticleView;
import com.pigeon.cloud.utils.MultiStateUtils;
import com.pigeon.cloud.utils.RvScrollTopUtils;
import com.pigeon.cloud.utils.UrlOpenUtils;
import com.pigeon.cloud.widget.CollectView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import per.goweii.actionbarex.common.ActionBarCommon;
import per.goweii.actionbarex.common.OnActionBarChildClickListener;

/**
 * @author yangzhikuan
 * @date 2019/5/18
 * 
 */
public class UserArticleFragment extends BaseFragment<UserArticlePresenter> implements UserArticleView {

    private static final int PAGE_START = 0;

    @BindView(R.id.abc)
    ActionBarCommon abc;
    @BindView(R.id.msv)
    MultiStateView msv;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    @BindView(R.id.rv)
    RecyclerView rv;

    private SmartRefreshUtils mSmartRefreshUtils;
    private ArticleAdapter mAdapter;

    private int currPage = PAGE_START;

    private long lastClickTime = 0L;

    public static UserArticleFragment create() {
        return new UserArticleFragment();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCollectionEvent(CollectionEvent event) {
        if (event.getArticleId() == -1) {
            return;
        }
        mAdapter.notifyCollectionEvent(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent event) {
        if (event.isLogin()) {
            currPage = PAGE_START;
            getProjectArticleList(true);
        } else {
            mAdapter.notifyAllUnCollect();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onArticleShareEvent(ArticleShareEvent event) {
        currPage = PAGE_START;
        getProjectArticleList(true);
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_user_article;
    }

    @Nullable
    @Override
    protected UserArticlePresenter initPresenter() {
        return new UserArticlePresenter();
    }

    @Override
    protected void initView() {
        abc.getTitleTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long currClickTime = System.currentTimeMillis();
                if (currClickTime - lastClickTime <= Config.SCROLL_TOP_DOUBLE_CLICK_DELAY) {
                    RvScrollTopUtils.smoothScrollTop(rv);
                }
                lastClickTime = currClickTime;
            }
        });
        abc.setOnRightIconClickListener(new OnActionBarChildClickListener() {
            @Override
            public void onClick(View v) {
                ShareArticleActivity.start(getContext());
            }
        });
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
                getProjectArticleList(true);
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
                getProjectArticleList(true);
            }
        });
    }

    @Override
    protected void loadData() {
        MultiStateUtils.toLoading(msv);
        currPage = PAGE_START;
        presenter.getUserArticleListCache(currPage);
    }

    @Override
    protected void onVisible(boolean isFirstVisible) {
        super.onVisible(isFirstVisible);
        if (isFirstVisible) {
            currPage = PAGE_START;
            getProjectArticleList(false);
        }
    }

    public void getProjectArticleList(boolean refresh) {
        presenter.getUserArticleList(currPage, refresh);
    }

    @Override
    public void getUserArticleListSuccess(int code, ArticleListBean data) {
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
    public void getUserArticleListFailed(int code, String msg) {
        ToastMaker.showShort(msg);
        mSmartRefreshUtils.fail();
        mAdapter.loadMoreFail();
        if (currPage == PAGE_START) {
            MultiStateUtils.toError(msv);
        }
    }
}
