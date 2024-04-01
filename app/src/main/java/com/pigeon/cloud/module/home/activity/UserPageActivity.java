package com.pigeon.cloud.module.home.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.kennyc.view.MultiStateView;
import com.pigeon.basic.core.base.BaseActivity;
import com.pigeon.basic.core.utils.SmartRefreshUtils;
import com.pigeon.basic.core.toast.ToastMaker;
import com.pigeon.basic.core.utils.CopyUtils;
import com.pigeon.basic.core.utils.LogUtils;
import com.pigeon.basic.core.utils.RandomUtils;
import com.pigeon.basic.core.utils.ResUtils;
import com.pigeon.basic.core.utils.listener.SimpleListener;
import com.pigeon.cloud.BuildConfig;
import com.pigeon.cloud.R;
import com.pigeon.cloud.event.CollectionEvent;
import com.pigeon.cloud.event.LoginEvent;
import com.pigeon.cloud.module.home.presenter.UserPagePresenter;
import com.pigeon.cloud.module.home.view.UserPageView;
import com.pigeon.cloud.module.main.adapter.ArticleAdapter;
import com.pigeon.cloud.module.main.model.ArticleBean;
import com.pigeon.cloud.module.main.model.UserPageBean;
import com.pigeon.cloud.utils.ImageLoader;
import com.pigeon.cloud.utils.MultiStateUtils;
import com.pigeon.cloud.utils.UrlOpenUtils;
import com.pigeon.cloud.utils.router.Router;
import com.pigeon.cloud.widget.CollectView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.listener.OnMultiListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Random;

import butterknife.BindView;
import per.goweii.actionbarex.common.ActionBarCommon;
import per.goweii.actionbarex.common.OnActionBarChildClickListener;

/**
 * @author yangzhikuan
 * @date 2019/5/18
 *
 */
public class UserPageActivity extends BaseActivity<UserPagePresenter> implements UserPageView {

    private static final int PAGE_START = 1;

    @BindView(R.id.msv)
    MultiStateView msv;
    @BindView(R.id.msv_list)
    MultiStateView msv_list;
    @BindView(R.id.cl)
    CoordinatorLayout cl;
    @BindView(R.id.ctbl)
    CollapsingToolbarLayout ctbl;
    @BindView(R.id.abl)
    AppBarLayout abl;
    @BindView(R.id.abc)
    ActionBarCommon abc;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    @BindView(R.id.iv_blur)
    ImageView iv_blur;
    @BindView(R.id.rl_user_info)
    RelativeLayout rl_user_info;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.civ_user_icon)
    ImageView civ_user_icon;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.tv_user_id)
    TextView tv_user_id;
    @BindView(R.id.tv_user_coin)
    TextView tv_user_coin;
    @BindView(R.id.tv_user_ranking)
    TextView tv_user_ranking;

    private SmartRefreshUtils mSmartRefreshUtils;
    private ArticleAdapter mAdapter;

    private int currPage = PAGE_START;
    private int mUserId = -1;

    public static void start(Context context, int userId) {
        Intent intent = new Intent(context, UserPageActivity.class);
        intent.putExtra("id", userId);
        context.startActivity(intent);
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
            getUserPage(true);
        } else {
            mAdapter.notifyAllUnCollect();
        }
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_page;
    }

    @Nullable
    @Override
    protected UserPagePresenter initPresenter() {
        return new UserPagePresenter();
    }

    @Override
    protected void initView() {
        mUserId = getUserIdFromIntent(getIntent());
        abc.setOnRightTextClickListener(new OnActionBarChildClickListener() {
            @Override
            public void onClick(View v) {
                String userId = String.valueOf(mUserId);
                String salt = RandomUtils.randomLetter(10 - userId.length());
                StringBuilder id = new StringBuilder();
                Random random = new Random();
                for (int i = 0; i < userId.length(); i++) {
                    int l = userId.length() - i;
                    int maxi = salt.length() - l - 1;
                    maxi = maxi < 2 ? 2 : maxi;
                    int ii = random.nextInt(maxi);
                    id.append(salt.substring(0, ii));
                    id.append(userId.charAt(i));
                    salt = salt.substring(ii);
                }
                id.append(salt);
                StringBuilder s = new StringBuilder();
                s.append("【玩口令】你的好友给你分享了一个神秘用户，户制泽条消息");
                s.append(String.format(BuildConfig.WANPWD_FORMAT, BuildConfig.WANPWD_TYPE_USERPAGE, id.toString()));
                s.append("打開最美玩安卓客户端揭开他/她的神秘面纱");
                LogUtils.d("UserPageActivity", s);
                CopyUtils.copyText(s.toString());
                ToastMaker.showShort("口令已复制");
            }
        });
        mSmartRefreshUtils = SmartRefreshUtils.with(srl);
        mSmartRefreshUtils.pureScrollMode();
        mSmartRefreshUtils.setRefreshListener(new SmartRefreshUtils.RefreshListener() {
            @Override
            public void onRefresh() {
                currPage = PAGE_START;
                getUserPage(true);
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ArticleAdapter();
        mAdapter.setEnableLoadMore(false);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getUserPage(true);
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
                getUserPage(true);
            }
        });
        srl.setOnMultiListener(new OnMultiListener() {
            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                if (iv_blur != null && rl_user_info != null) {
                    iv_blur.getLayoutParams().height = rl_user_info.getMeasuredHeight() + offset;
                    iv_blur.requestLayout();
                }
            }

            @Override
            public void onHeaderReleased(RefreshHeader header, int headerHeight, int maxDragHeight) {
            }

            @Override
            public void onHeaderStartAnimator(RefreshHeader header, int headerHeight, int maxDragHeight) {
            }

            @Override
            public void onHeaderFinish(RefreshHeader header, boolean success) {
            }

            @Override
            public void onFooterMoving(RefreshFooter footer, boolean isDragging, float percent, int offset, int footerHeight, int maxDragHeight) {
                if (iv_blur != null && rl_user_info != null) {
                    iv_blur.getLayoutParams().height = rl_user_info.getMeasuredHeight() - offset;
                    iv_blur.requestLayout();
                }
            }

            @Override
            public void onFooterReleased(RefreshFooter footer, int footerHeight, int maxDragHeight) {
            }

            @Override
            public void onFooterStartAnimator(RefreshFooter footer, int footerHeight, int maxDragHeight) {
            }

            @Override
            public void onFooterFinish(RefreshFooter footer, boolean success) {
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
            }
        });
        abl.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout abl, int offset) {
                if (Math.abs(offset) == abl.getTotalScrollRange()) {
                    abc.getTitleTextView().setAlpha(1F);
                    int color = ResUtils.getThemeColor(abc, R.attr.colorMainOrSurface);
                    abc.setBackgroundColor(color);
                    rl_user_info.setAlpha(1f);
                } else {
                    abc.getTitleTextView().setAlpha(0F);
                    int color = ResUtils.getThemeColor(abc, R.attr.colorTransparent);
                    abc.setBackgroundColor(color);
                    rl_user_info.setAlpha(1f - ((float) Math.abs(offset) / (float) abl.getTotalScrollRange()));
                }
            }
        });
        ctbl.post(new Runnable() {
            @Override
            public void run() {
                ctbl.setMinimumHeight(abc.getActionBar().getHeight());
                ctbl.setScrimVisibleHeightTrigger(abc.getActionBar().getHeight());
            }
        });
    }

    @Override
    protected void loadData() {
        MultiStateUtils.toLoading(msv);
        msv_list.setVisibility(View.GONE);
        currPage = PAGE_START;
        getUserPage(false);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int newUserId = getUserIdFromIntent(intent);
        if (mUserId != newUserId) {
            loadData();
        }
    }

    private int getUserIdFromIntent(Intent intent) {
        int id = mUserId;
        Uri uri = Router.getUriFrom(intent);
        if (uri != null) {
            String userId = uri.getQueryParameter("id");
            if (userId != null) {
                try {
                    id = Integer.parseInt(userId);
                } catch (Exception ignore) {
                }
            }
        } else {
            id = intent.getIntExtra("id", id);
        }
        if (id < 0) {
            abc.getRightTextView().setVisibility(View.GONE);
        } else {
            abc.getRightTextView().setVisibility(View.VISIBLE);
        }
        return id;
    }

    public void getUserPage(boolean refresh) {
        presenter.getUserPage(mUserId, currPage, refresh);
    }

    @Override
    public void getUserPageSuccess(int code, UserPageBean data) {
        MultiStateUtils.toContent(msv);
        currPage = data.getShareArticles().getCurPage() + PAGE_START;
        if (data.getShareArticles().getCurPage() == 1) {
            abc.getTitleTextView().setText(data.getCoinInfo().getUsername());
            ImageLoader.userIcon(civ_user_icon, "");
            ImageLoader.userBlur(iv_blur, "");
            tv_user_name.setText(data.getCoinInfo().getUsername());
            tv_user_id.setText("" + data.getCoinInfo().getUserId());
            tv_user_coin.setText("" + data.getCoinInfo().getCoinCount());
            tv_user_ranking.setText("" + data.getCoinInfo().getRank());
            mAdapter.setNewData(data.getShareArticles().getDatas());
            mAdapter.setEnableLoadMore(true);
            msv_list.setVisibility(View.VISIBLE);
            if (data.getShareArticles().getDatas() == null || data.getShareArticles().getDatas().isEmpty()) {
                MultiStateUtils.toEmpty(msv_list);
            } else {
                MultiStateUtils.toContent(msv_list);
            }
        } else {
            mAdapter.addData(data.getShareArticles().getDatas());
            mAdapter.loadMoreComplete();
        }
        if (data.getShareArticles().isOver()) {
            mAdapter.loadMoreEnd();
        }
        mSmartRefreshUtils.success();
    }

    @Override
    public void getUserPageFailed(int code, String msg) {
        ToastMaker.showShort(msg);
        mSmartRefreshUtils.fail();
        mAdapter.loadMoreFail();
        if (currPage == PAGE_START) {
            MultiStateUtils.toError(msv);
        }
    }
}
