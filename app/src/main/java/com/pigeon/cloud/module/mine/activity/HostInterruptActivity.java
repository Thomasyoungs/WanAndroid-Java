package com.pigeon.cloud.module.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.pigeon.basic.core.adapter.FixedFragmentPagerAdapter;
import com.pigeon.basic.core.base.BaseActivity;
import com.pigeon.basic.core.mvp.MvpPresenter;
import com.pigeon.basic.utils.listener.SimpleCallback;
import com.pigeon.cloud.common.Config;
import com.pigeon.cloud.utils.MagicIndicatorUtils;
import com.pigeon.cloud.utils.RvScrollTopUtils;

import butterknife.BindView;
import per.goweii.actionbarex.ActionBarEx;
import com.pigeon.basic.core.adapter.FixedFragmentPagerAdapter;
import com.pigeon.basic.core.base.BaseActivity;
import com.pigeon.basic.core.mvp.MvpPresenter;
import com.pigeon.basic.utils.listener.SimpleCallback;
import com.pigeon.cloud.R;
import com.pigeon.cloud.common.Config;
import com.pigeon.cloud.module.mine.fragment.HostBlackFragment;
import com.pigeon.cloud.module.mine.fragment.HostWhiteFragment;
import com.pigeon.cloud.utils.MagicIndicatorUtils;
import com.pigeon.cloud.utils.RvScrollTopUtils;
import com.pigeon.basic.core.adapter.FixedFragmentPagerAdapter;
import com.pigeon.basic.core.base.BaseActivity;
import com.pigeon.basic.core.mvp.MvpPresenter;
import com.pigeon.basic.utils.listener.SimpleCallback;
import com.pigeon.cloud.common.Config;
import com.pigeon.cloud.utils.MagicIndicatorUtils;
import com.pigeon.cloud.utils.RvScrollTopUtils;

/**
 * @author yangzhikuan
 * @date 2019/5/17
 *
 */
public class HostInterruptActivity extends BaseActivity {

    @BindView(R.id.ab)
    ActionBarEx ab;
    @BindView(R.id.vp)
    ViewPager vp;

    private FixedFragmentPagerAdapter mAdapter;
    private long lastClickTime = 0L;
    private int lastClickPos = 0;

    public static void start(Context context) {
        Intent intent = new Intent(context, HostInterruptActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_host_interrupt;
    }

    @Nullable
    @Override
    protected MvpPresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        ImageView ivBack = ab.getView(R.id.action_bar_fixed_magic_indicator_iv_back);
        ivBack.setVisibility(View.VISIBLE);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mAdapter = new FixedFragmentPagerAdapter(getSupportFragmentManager());
        mAdapter.setTitles("白名单", "黑名单");
        mAdapter.setFragmentList(
                HostWhiteFragment.create(),
                HostBlackFragment.create()
        );
        vp.setAdapter(mAdapter);
        MagicIndicatorUtils.commonNavigator(ab.getView(R.id.mi), vp, mAdapter, new SimpleCallback<Integer>() {
            @Override
            public void onResult(Integer data) {
                notifyScrollTop(data);
            }
        });
    }

    @Override
    protected void loadData() {
    }

    private void notifyScrollTop(int pos) {
        long currClickTime = System.currentTimeMillis();
        if (lastClickPos == pos && currClickTime - lastClickTime <= Config.SCROLL_TOP_DOUBLE_CLICK_DELAY) {
            Fragment fragment = mAdapter.getItem(pos);
            if (fragment instanceof RvScrollTopUtils.ScrollTop) {
                RvScrollTopUtils.ScrollTop scrollTop = (RvScrollTopUtils.ScrollTop) fragment;
                scrollTop.scrollTop();
            }
        }
        lastClickPos = pos;
        lastClickTime = currClickTime;
    }
}
