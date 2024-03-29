package com.pigeon.cloud.module.main.fragment;

import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.pigeon.basic.core.adapter.TabFragmentPagerAdapter;
import com.pigeon.basic.core.base.BaseFragment;
import com.pigeon.basic.core.base.BasePresenter;
import com.pigeon.cloud.R;
import com.pigeon.cloud.event.CloseSecondFloorEvent;
import com.pigeon.cloud.event.MessageCountEvent;
import com.pigeon.cloud.module.home.fragment.HomeFragment;
import com.pigeon.cloud.module.main.adapter.MainTabAdapter;
import com.pigeon.cloud.module.main.model.TabEntity;
import com.pigeon.cloud.module.mine.fragment.MineFragment;
import com.pigeon.cloud.module.question.fragment.QuestionFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

public class MainFragment extends BaseFragment {

    @BindView(R.id.vp_tab)
    ViewPager vp_tab;
    @BindView(R.id.ll_bottom_bar)
    LinearLayout ll_bottom_bar;
    //@BindView(R.id.bvef)
    //BackdropVisualEffectView bvef;

    private TabFragmentPagerAdapter.Page<TabEntity> mMinePage;
    private TabFragmentPagerAdapter<TabEntity> mTabFragmentPagerAdapter;

    public static MainFragment create() {
        return new MainFragment();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageCountEvent(MessageCountEvent event) {
        if (isDetached()) return;
        mMinePage.getData().setMsgCount(event.getCount());
        mTabFragmentPagerAdapter.notifyPageDataChanged();
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_main;
    }

    @Nullable
    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        mTabFragmentPagerAdapter = new TabFragmentPagerAdapter<>(getChildFragmentManager(), vp_tab, ll_bottom_bar, R.layout.tab_item_main);
        mMinePage = new TabFragmentPagerAdapter.Page<>(MineFragment.create(), new TabEntity("我的", R.drawable.ic_bottom_bar_mine, -1), new MainTabAdapter());
        mTabFragmentPagerAdapter.setPages(
                new TabFragmentPagerAdapter.Page<>(HomeFragment.create(), new TabEntity("首页", R.drawable.ic_bottom_bar_home, -1), new MainTabAdapter()),
                new TabFragmentPagerAdapter.Page<>(QuestionFragment.create(), new TabEntity("问答", R.drawable.ic_bottom_bar_ques, -1), new MainTabAdapter()),
                new TabFragmentPagerAdapter.Page<>(KnowledgeNavigationFragment.create(), new TabEntity("体系", R.drawable.ic_bottom_bar_navi, -1), new MainTabAdapter()),
                mMinePage
        );
        vp_tab.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                new CloseSecondFloorEvent().post();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        //bvef.setShowDebugInfo(false);
        //bvef.setOverlayColor(ResUtils.getThemeColor(bvef, R.attr.colorBottomBarOverlay));
        //bvef.setSimpleSize(8);
        //bvef.setVisualEffect(new RSBlurEffect(getContext(), 8));
    }

    @Override
    protected void loadData() {
    }

    @Override
    protected void onVisible(boolean isFirstVisible) {
        super.onVisible(isFirstVisible);
    }
}
