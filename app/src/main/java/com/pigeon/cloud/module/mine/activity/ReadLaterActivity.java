package com.pigeon.cloud.module.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kennyc.view.MultiStateView;
import com.pigeon.basic.core.base.BaseActivity;
import com.pigeon.basic.core.utils.SmartRefreshUtils;
import com.pigeon.basic.ui.dialog.TipDialog;
import com.pigeon.basic.ui.toast.ToastMaker;
import com.pigeon.basic.utils.CopyUtils;
import com.pigeon.basic.utils.IntentUtils;
import com.pigeon.basic.utils.listener.SimpleCallback;
import com.pigeon.basic.utils.listener.SimpleListener;
import com.pigeon.cloud.R;
import com.pigeon.cloud.db.model.ReadLaterModel;
import com.pigeon.cloud.event.ReadLaterEvent;
import com.pigeon.cloud.module.mine.adapter.ReadLaterAdapter;
import com.pigeon.cloud.module.mine.presenter.ReadLaterPresenter;
import com.pigeon.cloud.module.mine.view.ReadLaterView;
import com.pigeon.cloud.utils.MultiStateUtils;
import com.pigeon.cloud.utils.RvConfigUtils;
import com.pigeon.cloud.utils.UrlOpenUtils;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import per.goweii.actionbarex.common.ActionBarCommon;
import per.goweii.actionbarex.common.OnActionBarChildClickListener;

/**
 * @author yangzhikuan
 * @date 2019/5/17
 *
 */
public class ReadLaterActivity extends BaseActivity<ReadLaterPresenter> implements ReadLaterView {

    @BindView(R.id.abc)
    ActionBarCommon abc;
    @BindView(R.id.msv)
    MultiStateView msv;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    @BindView(R.id.rv)
    RecyclerView rv;

    private SmartRefreshUtils mSmartRefreshUtils;
    private ReadLaterAdapter mAdapter;

    private int offset = 0;
    private int perPageCount = 20;

    public static void start(Context context) {
        Intent intent = new Intent(context, ReadLaterActivity.class);
        context.startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReadLaterEvent(ReadLaterEvent event) {
        if (isDestroyed()) {
            return;
        }
        offset = 0;
        presenter.getList(0, perPageCount);
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_read_later;
    }

    @Nullable
    @Override
    protected ReadLaterPresenter initPresenter() {
        return new ReadLaterPresenter();
    }

    @Override
    protected void initView() {
        abc.setOnRightTextClickListener(new OnActionBarChildClickListener() {
            @Override
            public void onClick(View v) {
                TipDialog.with(getContext())
                        .message("确定要全部删除吗？")
                        .onYes(new SimpleCallback<Void>() {
                            @Override
                            public void onResult(Void data) {
                                presenter.removeAll();
                            }
                        })
                        .show();
            }
        });
        mSmartRefreshUtils = SmartRefreshUtils.with(srl);
        mSmartRefreshUtils.pureScrollMode();
        mSmartRefreshUtils.setRefreshListener(new SmartRefreshUtils.RefreshListener() {
            @Override
            public void onRefresh() {
                offset = 0;
                getPageList();
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ReadLaterAdapter();
        RvConfigUtils.init(mAdapter);
        mAdapter.setEnableLoadMore(false);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getPageList();
            }
        }, rv);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                mAdapter.closeAll(null);
                ReadLaterModel item = mAdapter.getItem(position);
                if (item == null) {
                    return;
                }
                switch (view.getId()) {
                    default:
                        break;
                    case R.id.rl_top:
                        UrlOpenUtils.Companion
                                .with(item.getLink())
                                .title(item.getTitle())
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
                    case R.id.tv_delete:
                        ReadLaterModel model = mAdapter.getItem(position);
                        if (model != null) {
                            presenter.remove(model.getLink());
                        }
                        break;
                }
            }
        });
        rv.setAdapter(mAdapter);
        MultiStateUtils.setEmptyAndErrorClick(msv, new SimpleListener() {
            @Override
            public void onResult() {
                MultiStateUtils.toLoading(msv);
                offset = 0;
                getPageList();
            }
        });
    }

    @Override
    protected void loadData() {
        MultiStateUtils.toLoading(msv);
        offset = 0;
        getPageList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void getPageList() {
        presenter.getList(offset, perPageCount);
    }

    @Override
    public void getReadLaterListSuccess(List<ReadLaterModel> list) {
        mSmartRefreshUtils.success();
        if (offset == 0) {
            mAdapter.setNewData(list);
            if (list.isEmpty()) {
                MultiStateUtils.toEmpty(msv, true);
            } else {
                MultiStateUtils.toContent(msv);
            }
        } else {
            mAdapter.addData(list);
            mAdapter.loadMoreComplete();
        }
        offset = mAdapter.getData().size();
        if (list.size() < perPageCount) {
            mAdapter.loadMoreEnd();
        }
    }

    @Override
    public void getReadLaterListFailed() {
        mSmartRefreshUtils.fail();
        if (offset == 0) {
            MultiStateUtils.toError(msv);
        } else {
            mAdapter.loadMoreFail();
        }
    }

    @Override
    public void removeReadLaterSuccess(String link) {
        List<ReadLaterModel> list = mAdapter.getData();
        for (int i = 0; i < list.size(); i++) {
            ReadLaterModel model = list.get(i);
            if (TextUtils.equals(model.getLink(), link)) {
                mAdapter.remove(i);
                break;
            }
        }
        offset = mAdapter.getData().size();
        if (mAdapter.getData().isEmpty()) {
            MultiStateUtils.toEmpty(msv, true);
        }
    }

    @Override
    public void removeReadLaterFailed() {
        ToastMaker.showShort("删除失败");
    }

    @Override
    public void removeAllReadLaterSuccess() {
        mAdapter.setNewData(null);
        offset = mAdapter.getData().size();
        MultiStateUtils.toEmpty(msv, true);
    }

    @Override
    public void removeAllReadLaterFailed() {
        ToastMaker.showShort("删除失败");
    }
}
