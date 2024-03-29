package com.pigeon.cloud.module.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kennyc.view.MultiStateView;
import com.pigeon.basic.core.base.BaseActivity;
import com.pigeon.basic.core.utils.SmartRefreshUtils;
import com.pigeon.basic.ui.toast.ToastMaker;
import com.pigeon.basic.utils.listener.SimpleListener;
import com.pigeon.cloud.R;
import com.pigeon.cloud.common.Config;
import com.pigeon.cloud.event.ArticleDeleteEvent;
import com.pigeon.cloud.event.ArticleShareEvent;
import com.pigeon.cloud.event.CollectionEvent;
import com.pigeon.cloud.module.main.activity.ShareArticleActivity;
import com.pigeon.cloud.module.main.adapter.ArticleAdapter;
import com.pigeon.cloud.module.main.model.ArticleBean;
import com.pigeon.cloud.module.main.model.ArticleListBean;
import com.pigeon.cloud.module.mine.adapter.MineShareArticleAdapter;
import com.pigeon.cloud.module.mine.presenter.MineSharePresenter;
import com.pigeon.cloud.module.mine.view.MineShareView;
import com.pigeon.cloud.utils.MultiStateUtils;
import com.pigeon.cloud.utils.RvScrollTopUtils;
import com.pigeon.cloud.widget.CollectView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import per.goweii.actionbarex.common.ActionBarCommon;
import per.goweii.actionbarex.common.OnActionBarChildClickListener;

/**
 * @author yangzhikuan
 * @date 2019/5/17
 * 
 */
public class MineShareActivity extends BaseActivity<MineSharePresenter> implements MineShareView {

    public static final int PAGE_START = 1;

    @BindView(R.id.abc)
    ActionBarCommon abc;
    @BindView(R.id.msv)
    MultiStateView msv;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    @BindView(R.id.rv)
    RecyclerView rv;

    private SmartRefreshUtils mSmartRefreshUtils;
    private MineShareArticleAdapter mAdapter;

    private int currPage = PAGE_START;

    private long lastClickTime = 0L;

    public static void start(Context context) {
        Intent intent = new Intent(context, MineShareActivity.class);
        context.startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCollectionEvent(CollectionEvent event) {
        if (isDestroyed()) {
            return;
        }
        mAdapter.notifyCollectionEvent(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onArticleShareEvent(ArticleShareEvent event) {
        currPage = PAGE_START;
        presenter.getMineShareArticleList(currPage, true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onArticleDeleteEvent(ArticleDeleteEvent event) {
        if (event.getArticleId() <= 0) {
            currPage = PAGE_START;
            presenter.getMineShareArticleList(currPage, true);
        } else {
            mAdapter.forEach(new ArticleAdapter.ArticleForEach() {
                @Override
                public boolean forEach(int dataPos, int adapterPos, ArticleBean bean) {
                    if (event.getArticleId() == bean.getId()) {
                        mAdapter.remove(adapterPos);
                        return true;
                    }
                    return false;
                }
            });
        }
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_share;
    }

    @Nullable
    @Override
    protected MineSharePresenter initPresenter() {
        return new MineSharePresenter();
    }

    @Override
    protected void initView() {
        abc.setOnRightIconClickListener(new OnActionBarChildClickListener() {
            @Override
            public void onClick(View v) {
                ShareArticleActivity.start(getContext());
            }
        });
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
        mSmartRefreshUtils = SmartRefreshUtils.with(srl);
        mSmartRefreshUtils.pureScrollMode();
        mSmartRefreshUtils.setRefreshListener(new SmartRefreshUtils.RefreshListener() {
            @Override
            public void onRefresh() {
                currPage = PAGE_START;
                presenter.getMineShareArticleList(currPage, true);
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MineShareArticleAdapter();
        mAdapter.setEnableLoadMore(false);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                presenter.getMineShareArticleList(currPage, true);
            }
        }, rv);
        mAdapter.setOnCollectListener(new ArticleAdapter.OnCollectListener() {
            @Override
            public void collect(ArticleBean item, CollectView v) {
                presenter.collect(item, v);
            }

            @Override
            public void uncollect(ArticleBean item, CollectView v) {
                presenter.uncollect(item, v);
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                mAdapter.closeAll(null);
                ArticleBean item = mAdapter.getItem(position);
                if (item == null) {
                    return;
                }
                switch (view.getId()) {
                    default:
                        break;
                    case R.id.tv_delete:
                        presenter.deleteMineShareArticle(item);
                        break;
                }
            }
        });
        rv.setAdapter(mAdapter);
        MultiStateUtils.setEmptyAndErrorClick(msv, new SimpleListener() {
            @Override
            public void onResult() {
                MultiStateUtils.toLoading(msv);
                currPage = PAGE_START;
                presenter.getMineShareArticleList(currPage, true);
            }
        });
    }

    @Override
    protected void loadData() {
        MultiStateUtils.toLoading(msv);
        currPage = PAGE_START;
        presenter.getMineShareArticleList(currPage, false);
    }

    @Override
    public void getMineShareArticleListSuccess(int code, ArticleListBean data) {
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
    public void getMineShareArticleListFailed(int code, String msg) {
        ToastMaker.showShort(msg);
        mSmartRefreshUtils.fail();
        mAdapter.loadMoreFail();
        if (currPage == PAGE_START) {
            MultiStateUtils.toError(msv);
        }
    }
}
