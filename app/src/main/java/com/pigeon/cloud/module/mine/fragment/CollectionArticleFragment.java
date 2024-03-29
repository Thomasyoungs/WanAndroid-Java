package com.pigeon.cloud.module.mine.fragment;

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
import com.pigeon.cloud.event.CollectionEvent;
import com.pigeon.cloud.module.main.adapter.ArticleAdapter;
import com.pigeon.cloud.module.main.model.ArticleBean;
import com.pigeon.cloud.module.main.model.ArticleListBean;
import com.pigeon.cloud.module.mine.presenter.CollectionArticlePresenter;
import com.pigeon.cloud.module.mine.view.CollectionArticleView;
import com.pigeon.cloud.utils.MultiStateUtils;
import com.pigeon.cloud.utils.RvScrollTopUtils;
import com.pigeon.cloud.utils.UrlOpenUtils;
import com.pigeon.cloud.widget.CollectView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * @author yangzhikuan
 * @date 2019/5/17
 *
 */
public class CollectionArticleFragment extends BaseFragment<CollectionArticlePresenter> implements RvScrollTopUtils.ScrollTop, CollectionArticleView {

    public static final int PAGE_START = 0;

    @BindView(R.id.msv)
    MultiStateView msv;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    @BindView(R.id.rv)
    RecyclerView rv;

    private SmartRefreshUtils mSmartRefreshUtils;
    private ArticleAdapter mAdapter;

    private int currPage = PAGE_START;

    public static CollectionArticleFragment create() {
        return new CollectionArticleFragment();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCollectionEvent(CollectionEvent event) {
        if (isDetached()) {
            return;
        }
        if (event.isCollect()) {
            currPage = PAGE_START;
            presenter.getCollectArticleList(currPage, true);
        } else {
            presenter.updateCollectArticleList(PAGE_START);
            if (event.getArticleId() != -1 || event.getCollectId() != -1) {
                mAdapter.forEach(new ArticleAdapter.ArticleForEach() {
                    @Override
                    public boolean forEach(int dataPos, int adapterPos, ArticleBean bean) {
                        if (event.getArticleId() != -1) {
                            if (bean.getOriginId() == event.getArticleId()) {
                                mAdapter.remove(adapterPos);
                                return true;
                            }
                        } else if (event.getCollectId() != -1) {
                            if (bean.getId() == event.getCollectId()) {
                                mAdapter.remove(adapterPos);
                                return true;
                            }
                        }
                        return false;
                    }
                });
            }
        }
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_collection_article;
    }

    @Nullable
    @Override
    protected CollectionArticlePresenter initPresenter() {
        return new CollectionArticlePresenter();
    }

    @Override
    protected void initView() {
        mSmartRefreshUtils = SmartRefreshUtils.with(srl);
        mSmartRefreshUtils.pureScrollMode();
        mSmartRefreshUtils.setRefreshListener(new SmartRefreshUtils.RefreshListener() {
            @Override
            public void onRefresh() {
                currPage = PAGE_START;
                presenter.getCollectArticleList(currPage, true);
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ArticleAdapter();
        mAdapter.setEnableLoadMore(false);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                presenter.getCollectArticleList(currPage, true);
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
                    presenter.uncollectArticle(item, v);
                }
            }
        });
        rv.setAdapter(mAdapter);
        MultiStateUtils.setEmptyAndErrorClick(msv, new SimpleListener() {
            @Override
            public void onResult() {
                MultiStateUtils.toLoading(msv);
                currPage = PAGE_START;
                presenter.getCollectArticleList(currPage, true);
            }
        });
    }

    @Override
    protected void loadData() {
        MultiStateUtils.toLoading(msv);
        presenter.getCollectArticleListCache(currPage);
    }

    @Override
    public void onVisible(boolean isFirstVisible) {
        super.onVisible(isFirstVisible);
        if (isFirstVisible) {
            currPage = PAGE_START;
            presenter.getCollectArticleList(currPage, true);
        }
    }

    @Override
    public void getCollectArticleListSuccess(int code, ArticleListBean data) {
        if (data.getDatas() != null) {
            for (ArticleBean articleBean : data.getDatas()) {
                articleBean.setCollect(true);
            }
        }
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
    public void getCollectArticleListFailed(int code, String msg) {
        ToastMaker.showShort(msg);
        mSmartRefreshUtils.fail();
        mAdapter.loadMoreFail();
        if (currPage == PAGE_START) {
            MultiStateUtils.toError(msv);
        }
    }

    @Override
    public void scrollTop() {
        if (isAdded() && !isDetached()) {
            RvScrollTopUtils.smoothScrollTop(rv);
        }
    }
}
