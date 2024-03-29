package com.pigeon.cloud.module.book.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pigeon.cloud.R;
import com.pigeon.cloud.module.book.model.BookBean;
import com.pigeon.cloud.utils.ImageLoader;

public class BookAdapter extends BaseQuickAdapter<BookBean, BaseViewHolder> {
    public BookAdapter() {
        super(R.layout.rv_item_book);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, @NonNull BookBean item) {
        ImageLoader.image(helper.getView(R.id.piv_img), item.getCover());
    }
}
