package com.pigeon.cloud.module.mine.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.daimajia.swipe.SwipeLayout;
import com.pigeon.cloud.module.main.adapter.ArticleAdapter;
import com.pigeon.cloud.module.main.model.ArticleBean;

import java.util.ArrayList;
import java.util.List;

import com.pigeon.cloud.R;
import com.pigeon.cloud.module.main.adapter.ArticleAdapter;
import com.pigeon.cloud.module.main.model.ArticleBean;
import com.pigeon.cloud.module.main.adapter.ArticleAdapter;
import com.pigeon.cloud.module.main.model.ArticleBean;

/**
 * @author yangzhikuan
 * @date 2019/5/15
 *
 */
public class MineShareArticleAdapter extends ArticleAdapter {

    private final List<SwipeLayout> mUnCloseList = new ArrayList<>();

    private ArticleAdapter.OnCollectListener mOnCollectListener = null;

    public MineShareArticleAdapter() {
        super();
    }

    @Override
    protected int getArticleLayoutId() {
        return R.layout.rv_item_mine_share_article;
    }

    public void setOnCollectListener(ArticleAdapter.OnCollectListener onCollectListener) {
        mOnCollectListener = onCollectListener;
    }

    public void closeAll(SwipeLayout layout) {
        for (SwipeLayout swipeLayout : mUnCloseList) {
            if (layout == swipeLayout) {
                continue;
            }
            if (swipeLayout.getOpenStatus() != SwipeLayout.Status.Open) {
                continue;
            }
            swipeLayout.close();
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    closeAll(null);
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ArticleBean item) {
        SwipeLayout sl = helper.getView(R.id.sl);
        sl.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {
                closeAll(layout);
            }

            @Override
            public void onOpen(SwipeLayout layout) {
                mUnCloseList.add(layout);
            }

            @Override
            public void onStartClose(SwipeLayout layout) {
            }

            @Override
            public void onClose(SwipeLayout layout) {
                mUnCloseList.remove(layout);
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
            }
        });
        bindArticle(helper.getView(R.id.rl_article), item, mOnCollectListener);
        helper.addOnClickListener(R.id.tv_delete);
    }
}
