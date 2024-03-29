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
import com.pigeon.basic.utils.listener.SimpleListener;
import com.pigeon.cloud.R;
import com.pigeon.cloud.module.home.activity.UserPageActivity;
import com.pigeon.cloud.module.main.model.CoinInfoBean;
import com.pigeon.cloud.module.mine.adapter.CoinRankAdapter;
import com.pigeon.cloud.module.mine.model.CoinRankBean;
import com.pigeon.cloud.module.mine.presenter.CoinRankPresenter;
import com.pigeon.cloud.module.mine.view.CoinRankView;
import com.pigeon.cloud.utils.MultiStateUtils;
import com.pigeon.cloud.utils.RvConfigUtils;

import butterknife.BindView;
import per.goweii.actionbarex.common.ActionBarCommon;
import per.goweii.actionbarex.common.OnActionBarChildClickListener;

/**
 * @author yangzhikuan
 * @date 2019/8/31
 *
 */
public class CoinRankActivity extends BaseActivity<CoinRankPresenter> implements CoinRankView {

    private static final int PAGE_START = 1;

    @BindView(R.id.abc)
    ActionBarCommon abc;
    @BindView(R.id.msv)
    MultiStateView msv;
    @BindView(R.id.rv)
    RecyclerView rv;

    private int currPage = PAGE_START;
    private CoinRankAdapter mAdapter = null;

    public static void start(Context context) {
        Intent intent = new Intent(context, CoinRankActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coin_rank;
    }

    @Nullable
    @Override
    protected CoinRankPresenter initPresenter() {
        return new CoinRankPresenter();
    }

    @Override
    protected void initView() {
        abc.setOnRightIconClickListener(new OnActionBarChildClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new CoinRankAdapter();
        RvConfigUtils.init(mAdapter);
        mAdapter.setEnableLoadMore(false);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                presenter.getCoinRankList(currPage);
            }
        }, rv);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CoinInfoBean item = mAdapter.getItem(position);
                if (item != null) {
                    UserPageActivity.start(getContext(), item.getUserId());
                }
            }
        });
        rv.setAdapter(mAdapter);
        MultiStateUtils.setEmptyAndErrorClick(msv, new SimpleListener() {
            @Override
            public void onResult() {
                MultiStateUtils.toLoading(msv);
                currPage = PAGE_START;
                presenter.getCoinRankList(currPage);
            }
        });
    }

    @Override
    protected void loadData() {
        MultiStateUtils.toLoading(msv);
        currPage = PAGE_START;
        presenter.getCoinRankList(currPage);
    }

    @Override
    public void getCoinRankListSuccess(int code, CoinRankBean data) {
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
    }

    @Override
    public void getCoinRankListFail(int code, String msg) {
        mAdapter.loadMoreFail();
        if (currPage == PAGE_START) {
            MultiStateUtils.toError(msv);
        }
    }
}
