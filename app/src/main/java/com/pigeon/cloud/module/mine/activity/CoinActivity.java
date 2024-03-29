package com.pigeon.cloud.module.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kennyc.view.MultiStateView;
import com.pigeon.basic.core.base.BaseActivity;
import com.pigeon.basic.ui.toast.ToastMaker;
import com.pigeon.basic.utils.AnimatorUtils;
import com.pigeon.basic.utils.listener.OnClickListener2;
import com.pigeon.basic.utils.listener.SimpleListener;
import com.pigeon.cloud.R;
import com.pigeon.cloud.module.main.dialog.WebDialog;
import com.pigeon.cloud.module.mine.adapter.CoinRecordAdapter;
import com.pigeon.cloud.module.mine.model.CoinRecordBean;
import com.pigeon.cloud.module.mine.presenter.CoinPresenter;
import com.pigeon.cloud.module.mine.view.CoinView;
import com.pigeon.cloud.utils.MultiStateUtils;
import com.pigeon.cloud.utils.RvConfigUtils;

import butterknife.BindView;
import per.goweii.actionbarex.common.ActionBarSuper;

/**
 * @author yangzhikuan
 * @date 2019/8/31
 *
 */
public class CoinActivity extends BaseActivity<CoinPresenter> implements CoinView {

    private static final int PAGE_START = 1;

    @BindView(R.id.abc)
    ActionBarSuper abc;
    @BindView(R.id.tv_coin)
    TextView tv_coin;
    @BindView(R.id.msv)
    MultiStateView msv;
    @BindView(R.id.rv)
    RecyclerView rv;

    private int currPage = PAGE_START;
    private CoinRecordAdapter mCoinRecordAdapter = null;

    public static void start(Context context) {
        Intent intent = new Intent(context, CoinActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coin;
    }

    @Nullable
    @Override
    protected CoinPresenter initPresenter() {
        return new CoinPresenter();
    }

    @Override
    protected void initView() {
        abc.getRightActionView(0).setOnClickListener(new OnClickListener2() {
            @Override
            public void onClick2(View v) {
                WebDialog.create(getContext(), "https://www.wanandroid.com/blog/show/2653").show();
            }
        });
        abc.getRightActionView(1).setOnClickListener(new OnClickListener2() {
            @Override
            public void onClick2(View v) {
                CoinRankActivity.start(getContext());
            }
        });
        tv_coin.setText("0");
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        mCoinRecordAdapter = new CoinRecordAdapter();
        RvConfigUtils.init(mCoinRecordAdapter);
        mCoinRecordAdapter.setEnableLoadMore(false);
        mCoinRecordAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                presenter.getCoinRecordList(currPage);
            }
        }, rv);
        rv.setAdapter(mCoinRecordAdapter);
        MultiStateUtils.setEmptyAndErrorClick(msv, new SimpleListener() {
            @Override
            public void onResult() {
                MultiStateUtils.toLoading(msv);
                currPage = PAGE_START;
                presenter.getCoinRecordList(currPage);
            }
        });
    }

    @Override
    protected void loadData() {
        presenter.getCoin();
        MultiStateUtils.toLoading(msv);
        currPage = PAGE_START;
        presenter.getCoinRecordList(currPage);
    }

    @Override
    public void getCoinSuccess(int code, int coin) {
        AnimatorUtils.doIntAnim(tv_coin, coin, 1000);
    }

    @Override
    public void getCoinFail(int code, String msg) {
        ToastMaker.showShort(msg);
    }

    @Override
    public void getCoinRecordListSuccess(int code, CoinRecordBean data) {
        currPage = data.getCurPage() + PAGE_START;
        if (data.getCurPage() == 1) {
            mCoinRecordAdapter.setNewData(data.getDatas());
            mCoinRecordAdapter.setEnableLoadMore(true);
            if (data.getDatas() == null || data.getDatas().isEmpty()) {
                MultiStateUtils.toEmpty(msv);
            } else {
                MultiStateUtils.toContent(msv);
            }
        } else {
            mCoinRecordAdapter.addData(data.getDatas());
            mCoinRecordAdapter.loadMoreComplete();
        }
        if (data.isOver()) {
            mCoinRecordAdapter.loadMoreEnd();
        }
    }

    @Override
    public void getCoinRecordListFail(int code, String msg) {
        mCoinRecordAdapter.loadMoreFail();
        if (currPage == PAGE_START) {
            MultiStateUtils.toError(msv);
        }
    }
}
