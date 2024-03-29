package com.pigeon.cloud.module.main.fragment;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.pigeon.basic.core.adapter.FixedFragmentPagerAdapter;
import com.pigeon.basic.core.base.BaseFragment;
import com.pigeon.basic.core.mvp.MvpPresenter;
import com.pigeon.basic.utils.listener.SimpleCallback;
import com.pigeon.cloud.common.Config;
import com.pigeon.cloud.module.knowledge.fragment.KnowledgeFragment;
import com.pigeon.cloud.module.navigation.fragment.NaviFragment;
import com.pigeon.cloud.utils.MagicIndicatorUtils;
import com.pigeon.cloud.utils.RvScrollTopUtils;

import butterknife.BindView;
import per.goweii.actionbarex.ActionBarEx;
import com.pigeon.basic.core.adapter.FixedFragmentPagerAdapter;
import com.pigeon.basic.core.base.BaseFragment;
import com.pigeon.basic.core.mvp.MvpPresenter;
import com.pigeon.basic.utils.listener.SimpleCallback;
import com.pigeon.cloud.R;
import com.pigeon.cloud.common.Config;
import com.pigeon.cloud.module.knowledge.fragment.KnowledgeFragment;
import com.pigeon.cloud.module.navigation.fragment.NaviFragment;
import com.pigeon.cloud.utils.MagicIndicatorUtils;
import com.pigeon.cloud.utils.RvScrollTopUtils;
import com.pigeon.basic.core.adapter.FixedFragmentPagerAdapter;
import com.pigeon.basic.core.base.BaseFragment;
import com.pigeon.basic.core.mvp.MvpPresenter;
import com.pigeon.basic.utils.listener.SimpleCallback;
import com.pigeon.cloud.common.Config;
import com.pigeon.cloud.module.knowledge.fragment.KnowledgeFragment;
import com.pigeon.cloud.module.navigation.fragment.NaviFragment;
import com.pigeon.cloud.utils.MagicIndicatorUtils;
import com.pigeon.cloud.utils.RvScrollTopUtils;

/**
 * @author yangzhikuan
 * @date 2019/5/19
 *
 */
public class KnowledgeNavigationFragment extends BaseFragment implements RvScrollTopUtils.ScrollTop {

    @BindView(R.id.ab)
    ActionBarEx ab;
    @BindView(R.id.vp)
    ViewPager vp;

    private FixedFragmentPagerAdapter mAdapter;
    private long lastClickTime = 0L;
    private int lastClickPos = 0;

    public static KnowledgeNavigationFragment create() {
        return new KnowledgeNavigationFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_knowledge_navigation;
    }

    @Nullable
    @Override
    protected MvpPresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        mAdapter = new FixedFragmentPagerAdapter(getChildFragmentManager());
        mAdapter.setTitles("体系", "导航");
        mAdapter.setFragmentList(
                KnowledgeFragment.create(),
                NaviFragment.create()
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

    @Override
    public void onVisible(boolean isFirstVisible) {
        super.onVisible(isFirstVisible);
    }

    @Override
    public void scrollTop() {
        if (isAdded() && !isDetached()) {
            Fragment fragment = mAdapter.getItem(vp.getCurrentItem());
            if (fragment instanceof RvScrollTopUtils.ScrollTop) {
                RvScrollTopUtils.ScrollTop scrollTop = (RvScrollTopUtils.ScrollTop) fragment;
                scrollTop.scrollTop();
            }
        }
    }

    private void notifyScrollTop(int pos) {
        long currClickTime = System.currentTimeMillis();
        if (lastClickPos == pos && currClickTime - lastClickTime <= Config.SCROLL_TOP_DOUBLE_CLICK_DELAY) {
            Fragment fragment = mAdapter.getItem(vp.getCurrentItem());
            if (fragment instanceof RvScrollTopUtils.ScrollTop) {
                RvScrollTopUtils.ScrollTop scrollTop = (RvScrollTopUtils.ScrollTop) fragment;
                scrollTop.scrollTop();
            }
        }
        lastClickPos = pos;
        lastClickTime = currClickTime;
    }
}
