package com.pigeon.cloud.module.mine.fragment;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewParent;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kennyc.view.MultiStateView;
import com.pigeon.basic.core.base.BaseFragment;
import com.pigeon.basic.core.toast.ToastMaker;
import com.pigeon.basic.core.utils.CopyUtils;
import com.pigeon.basic.core.utils.IntentUtils;
import com.pigeon.basic.core.utils.SmartRefreshUtils;
import com.pigeon.basic.core.utils.listener.SimpleCallback;
import com.pigeon.basic.core.utils.listener.SimpleListener;
import com.pigeon.cloud.R;
import com.pigeon.cloud.event.CollectionEvent;
import com.pigeon.cloud.module.main.model.CollectionLinkBean;
import com.pigeon.cloud.module.mine.adapter.CollectionLinkAdapter;
import com.pigeon.cloud.module.mine.dialog.EditCollectLinkDialog;
import com.pigeon.cloud.module.mine.presenter.CollectionLinkPresenter;
import com.pigeon.cloud.module.mine.view.CollectionLinkView;
import com.pigeon.cloud.utils.MultiStateUtils;
import com.pigeon.cloud.utils.RvConfigUtils;
import com.pigeon.cloud.utils.RvScrollTopUtils;
import com.pigeon.cloud.utils.UrlOpenUtils;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * @author yangzhikuan
 * @date 2019/5/17
 *
 */
public class CollectionLinkFragment extends BaseFragment<CollectionLinkPresenter> implements RvScrollTopUtils.ScrollTop, CollectionLinkView {

    @BindView(R.id.msv)
    MultiStateView msv;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    @BindView(R.id.rv)
    RecyclerView rv;

    private SmartRefreshUtils mSmartRefreshUtils;
    private CollectionLinkAdapter mAdapter;

    public static CollectionLinkFragment create() {
        return new CollectionLinkFragment();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCollectionEvent(CollectionEvent event) {
        if (isDetached()) {
            return;
        }
        if (event.isCollect()) {
            presenter.getCollectLinkList(true);
        } else {
            presenter.updateCollectLinkList();
            if (event.getCollectId() != -1) {
                List<CollectionLinkBean> list = mAdapter.getData();
                for (int i = 0; i < list.size(); i++) {
                    CollectionLinkBean item = list.get(i);
                    if (item.getId() == event.getCollectId()) {
                        mAdapter.remove(i);
                        break;
                    }
                }
            }
        }
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_collection_link;
    }

    @Nullable
    @Override
    protected CollectionLinkPresenter initPresenter() {
        return new CollectionLinkPresenter();
    }

    @Override
    protected void initView() {
        mSmartRefreshUtils = SmartRefreshUtils.with(srl);
        mSmartRefreshUtils.pureScrollMode();
        mSmartRefreshUtils.setRefreshListener(new SmartRefreshUtils.RefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getCollectLinkList(true);
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new CollectionLinkAdapter();
        RvConfigUtils.init(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                mAdapter.closeAll(null);
                CollectionLinkBean item = mAdapter.getItem(position);
                if (item == null) {
                    return;
                }
                switch (view.getId()) {
                    default:
                        break;
                    case R.id.rl_top:
                        UrlOpenUtils.Companion
                                .with(item.getLink())
                                .collectId(item.getId())
                                .title(item.getName())
                                .collected(true)
                                .open(getContext());
                        break;
                    case R.id.tv_copy:
                        CopyUtils.copyText(item.getLink());
                        ToastMaker.showShort("复制成功");
                        break;
                    case R.id.tv_open:
                        if (TextUtils.isEmpty(item.getLink())) {
                            ToastMaker.showShort("链接为空");
                            break;
                        }
                        if (getContext() != null) {
                            IntentUtils.openBrowser(getContext(), item.getLink());
                        }
                        break;
                    case R.id.tv_edit:
                        EditCollectLinkDialog.show(getContext(), item, new SimpleCallback<CollectionLinkBean>() {
                            @Override
                            public void onResult(CollectionLinkBean data) {
                                presenter.updateCollectLink(data);
                            }
                        });
                        break;
                    case R.id.tv_delete:
                        presenter.uncollectLink(item);
                        break;
                }
            }
        });
        rv.setAdapter(mAdapter);
        MultiStateUtils.setEmptyAndErrorClick(msv, new SimpleListener() {
            @Override
            public void onResult() {
                MultiStateUtils.toLoading(msv);
                presenter.getCollectLinkList(true);
            }
        });
        if (getRootView() != null) {
            ViewParent parent = getRootView().getParent();
            if (parent instanceof ViewPager) {
                ViewPager viewPager = (ViewPager) parent;
                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int i, float v, int i1) {
                    }

                    @Override
                    public void onPageSelected(int i) {
                    }

                    @Override
                    public void onPageScrollStateChanged(int i) {
                        if (i != ViewPager.SCROLL_STATE_IDLE) {
                            if (mAdapter != null) {
                                mAdapter.closeAll(null);
                            }
                        }
                    }
                });
            }
        }
    }

    @Override
    protected void loadData() {
        MultiStateUtils.toLoading(msv);
        presenter.getCollectLinkListCache();
    }

    @Override
    public void onVisible(boolean isFirstVisible) {
        super.onVisible(isFirstVisible);
        if (isFirstVisible) {
            presenter.getCollectLinkList(true);
        }
    }

    @Override
    public void getCollectLinkListSuccess(int code, List<CollectionLinkBean> data) {
        List<CollectionLinkBean> copyList;
        if (data != null) {
            copyList = new ArrayList<>(data);
        } else {
            copyList = new ArrayList<>(0);
        }
        Collections.reverse(copyList);
        mAdapter.setNewData(copyList);
        mSmartRefreshUtils.success();
        if (copyList.isEmpty()) {
            MultiStateUtils.toEmpty(msv);
        } else {
            MultiStateUtils.toContent(msv);
        }
    }

    @Override
    public void getCollectLinkListFailed(int code, String msg) {
        ToastMaker.showShort(msg);
        mSmartRefreshUtils.fail();
        MultiStateUtils.toError(msv);
    }

    @Override
    public void updateCollectLinkSuccess(int code, CollectionLinkBean data) {
        List<CollectionLinkBean> list = mAdapter.getData();
        for (int i = 0; i < list.size(); i++) {
            CollectionLinkBean bean = list.get(i);
            if (bean.getId() == data.getId()) {
                bean.setName(data.getName());
                bean.setLink(data.getLink());
                mAdapter.notifyItemChanged(i);
                break;
            }
        }
    }

    @Override
    public void scrollTop() {
        if (isAdded() && !isDetached()) {
            RvScrollTopUtils.smoothScrollTop(rv);
        }
    }
}
