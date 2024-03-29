package com.pigeon.cloud.module.book.adapter;

import android.annotation.SuppressLint;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pigeon.basic.utils.ResUtils;
import com.pigeon.cloud.R;
import com.pigeon.cloud.module.book.model.BookChapterBean;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class BookChapterAdapter extends BaseQuickAdapter<BookChapterBean, BaseViewHolder> {
    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public BookChapterAdapter() {
        super(R.layout.rv_item_book_chapter);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(@NonNull BaseViewHolder helper, @NonNull BookChapterBean item) {
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_state = helper.getView(R.id.tv_state);
        TextView tv_time = helper.getView(R.id.tv_time);
        LinearLayout ll_state = helper.getView(R.id.ll_state);
        MaterialProgressBar pb_percent = helper.getView(R.id.pb_percent);

        tv_title.setText((helper.getAdapterPosition() + 1) + ". " + Html.fromHtml(item.getArticleBean().getTitle()));
        if (item.getTime() == 0) {
            ll_state.setVisibility(View.VISIBLE);
            tv_state.setText("未学习");
            tv_state.setTextColor(ResUtils.getThemeColor(tv_state, R.attr.colorTextThird));
            pb_percent.setVisibility(View.INVISIBLE);
            tv_time.setVisibility(View.INVISIBLE);
        } else {
            ll_state.setVisibility(View.VISIBLE);
            tv_time.setVisibility(View.VISIBLE);
            tv_time.setText(sdf.format(new Date(item.getTime())));
            if (item.getPercent() >= 0.98) {
                tv_state.setText("已学完");
                tv_state.setTextColor(ResUtils.getThemeColor(tv_state, R.attr.colorTextAccent));
                pb_percent.setVisibility(View.INVISIBLE);
            } else {
                ll_state.setVisibility(View.VISIBLE);
                tv_state.setText("已学" + ((int) (item.getPercent() * 100)) + "%");
                tv_state.setTextColor(ResUtils.getThemeColor(tv_state, R.attr.colorTextMain));
                pb_percent.setVisibility(View.VISIBLE);
            }
        }
        pb_percent.setMax(10000);
        pb_percent.setProgress((int) (item.getPercent() * pb_percent.getMax()));
    }
}
