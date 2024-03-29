package com.pigeon.cloud.module.project.fragment;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.pigeon.cloud.module.project.view.ProjectView;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.List;

import butterknife.BindView;
import per.goweii.actionbarex.ActionBarEx;
import com.pigeon.basic.core.adapter.MultiFragmentPagerAdapter;
import com.pigeon.basic.core.base.BaseFragment;
import com.pigeon.basic.ui.toast.ToastMaker;
import com.pigeon.basic.utils.listener.SimpleCallback;
import com.pigeon.cloud.R;
import com.pigeon.cloud.common.Config;
import com.pigeon.cloud.event.ScrollTopEvent;
import com.pigeon.cloud.module.main.model.ChapterBean;
import com.pigeon.cloud.module.project.presenter.ProjectPresenter;
import com.pigeon.cloud.module.project.view.ProjectView;
import com.pigeon.cloud.utils.MagicIndicatorUtils;
import com.pigeon.cloud.utils.RvScrollTopUtils;
import com.pigeon.cloud.module.project.view.ProjectView;

/**
 * @author yangzhikuan
 * @date 2019/5/12
 *
 */
public class ProjectFragment extends BaseFragment<ProjectPresenter> implements RvScrollTopUtils.ScrollTop, ProjectView {

    @BindView(R.id.ab)
    ActionBarEx ab;
    @BindView(R.id.vp)
    ViewPager vp;

    private MultiFragmentPagerAdapter<ChapterBean, ProjectArticleFragment> mAdapter;
    private CommonNavigator mCommonNavigator;
    private long lastClickTime = 0L;
    private int lastClickPos = 0;

    public static ProjectFragment create() {
        return new ProjectFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_project;
    }

    @Nullable
    @Override
    protected ProjectPresenter initPresenter() {
        return new ProjectPresenter();
    }

    @Override
    protected void initView() {
        mAdapter = new MultiFragmentPagerAdapter<>(
                getChildFragmentManager(),
                new MultiFragmentPagerAdapter.FragmentCreator<ChapterBean, ProjectArticleFragment>() {
                    @Override
                    public ProjectArticleFragment create(ChapterBean data, int pos) {
                        return ProjectArticleFragment.create(data, pos);
                    }

                    @Override
                    public String getTitle(ChapterBean data) {
                        return data.getName();
                    }
                });
        vp.setAdapter(mAdapter);
        mCommonNavigator = MagicIndicatorUtils.commonNavigator(ab.getView(R.id.mi), vp, mAdapter, new SimpleCallback<Integer>() {
            @Override
            public void onResult(Integer data) {
                notifyScrollTop(data);
            }
        });
    }

    @Override
    protected void loadData() {
        presenter.getProjectChapters();
    }

    @Override
    public void onVisible(boolean isFirstVisible) {
        super.onVisible(isFirstVisible);
    }

    @Override
    public void scrollTop() {
        if (isAdded() && !isDetached()) {
            new ScrollTopEvent(ProjectArticleFragment.class, vp.getCurrentItem()).post();
        }
    }

    private void notifyScrollTop(int pos) {
        long currClickTime = System.currentTimeMillis();
        if (lastClickPos == pos && currClickTime - lastClickTime <= Config.SCROLL_TOP_DOUBLE_CLICK_DELAY) {
            new ScrollTopEvent(ProjectArticleFragment.class, vp.getCurrentItem()).post();
        }
        lastClickPos = pos;
        lastClickTime = currClickTime;
    }

    @Override
    public void getProjectChaptersSuccess(int code, List<ChapterBean> data) {
        mAdapter.setDataList(data);
        mCommonNavigator.notifyDataSetChanged();
    }

    @Override
    public void getProjectChaptersFailed(int code, String msg) {
        ToastMaker.showShort(msg);
    }
}
