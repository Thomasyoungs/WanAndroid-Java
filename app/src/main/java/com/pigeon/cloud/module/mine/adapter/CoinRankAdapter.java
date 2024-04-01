package com.pigeon.cloud.module.mine.adapter;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pigeon.basic.core.utils.ResUtils;
import com.pigeon.cloud.module.main.model.CoinInfoBean;

import java.util.List;

import com.pigeon.cloud.R;

/**
 * @author yangzhikuan
 * @date 2019/5/15
 *
 */
public class CoinRankAdapter extends BaseQuickAdapter<CoinInfoBean, BaseViewHolder> {

    private final int mScale = 1000;
    private int mMax = 0;

    public CoinRankAdapter() {
        super(R.layout.rv_item_coin_rank);
    }

    @Override
    public void setNewData(@Nullable List<CoinInfoBean> data) {
        super.setNewData(data);
        if (data != null && !data.isEmpty()) {
            mMax = data.get(0).getCoinCount();
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, CoinInfoBean item) {
        ProgressBar pb = helper.getView(R.id.pb);
        pb.setMax(mMax * mScale);
        cancelProgressAnim(pb);
        if (!item.anim) {
            item.anim = true;
            doProgressAnim(pb, item.getCoinCount());
        } else {
            pb.setProgress(item.getCoinCount() * mScale);
        }
        int index = helper.getAdapterPosition() + 1;
        helper.setText(R.id.tv_index, "" + index);
        helper.setText(R.id.tv_index, "" + index);
        helper.setText(R.id.tv_user_name, item.getUsername());
        helper.setText(R.id.tv_coin_count, "" + item.getCoinCount());
        ImageView iv_index = helper.getView(R.id.iv_index);
        TextView tv_index = helper.getView(R.id.tv_index);
        if (index == 1) {
            iv_index.setImageResource(R.drawable.ic_rank_1);
            tv_index.setTextColor(Color.parseColor("#ffca28"));
            tv_index.setTextSize(TypedValue.COMPLEX_UNIT_PX, tv_index.getContext().getResources().getDimension(R.dimen.text_auxiliary));
        } else if (index == 2) {
            iv_index.setImageResource(R.drawable.ic_rank_2);
            tv_index.setTextColor(Color.parseColor("#cdcdcd"));
            tv_index.setTextSize(TypedValue.COMPLEX_UNIT_PX, tv_index.getContext().getResources().getDimension(R.dimen.text_auxiliary));
        } else if (index == 3) {
            iv_index.setImageResource(R.drawable.ic_rank_3);
            tv_index.setTextColor(Color.parseColor("#d49682"));
            tv_index.setTextSize(TypedValue.COMPLEX_UNIT_PX, tv_index.getContext().getResources().getDimension(R.dimen.text_auxiliary));
        } else {
            iv_index.setImageDrawable(new ColorDrawable(Color.TRANSPARENT));
            tv_index.setTextColor(ResUtils.getThemeColor(tv_index.getContext(), R.attr.colorTextSecond));
            tv_index.setTextSize(TypedValue.COMPLEX_UNIT_PX, tv_index.getContext().getResources().getDimension(R.dimen.text_content));
        }
    }

    private void doProgressAnim(final ProgressBar pb, int to) {
        ValueAnimator animator = ValueAnimator.ofInt(0, to);
        animator.setDuration(1000);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                pb.setProgress((int) animation.getAnimatedValue() * mScale);
            }
        });
        pb.setTag(animator);
        animator.start();
    }

    private void cancelProgressAnim(final ProgressBar pb) {
        Object obj = pb.getTag();
        if (obj instanceof Animator) {
            Animator animator = (Animator) obj;
            animator.cancel();
        }
        pb.setTag(null);
    }
}
