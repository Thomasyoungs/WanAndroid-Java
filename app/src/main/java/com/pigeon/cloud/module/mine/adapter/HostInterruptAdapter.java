package com.pigeon.cloud.module.mine.adapter;

import android.widget.CompoundButton;

import androidx.appcompat.widget.SwitchCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pigeon.cloud.module.mine.model.HostEntity;

import com.pigeon.cloud.R;
import com.pigeon.cloud.module.mine.model.HostEntity;
import com.pigeon.cloud.module.mine.model.HostEntity;

/**
 * @author yangzhikuan
 * @date 2019/5/15
 * 
 */
public class HostInterruptAdapter extends BaseQuickAdapter<HostEntity, BaseViewHolder> {

    private OnCheckedChangeListener mOnCheckedChangeListener = null;

    public HostInterruptAdapter() {
        super(R.layout.rv_item_host_interrupt);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        mOnCheckedChangeListener = onCheckedChangeListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, HostEntity item) {
        SwitchCompat sc_enable = helper.getView(R.id.sc_enable);
        sc_enable.setChecked(item.isEnable());
        helper.setGone(R.id.iv_delete, item.isCustom());
        helper.setText(R.id.tv_host, item.getHost());
        helper.addOnClickListener(R.id.iv_delete);
        sc_enable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mOnCheckedChangeListener != null) {
                    mOnCheckedChangeListener.onCheckedChanged(helper.getAdapterPosition(), isChecked);
                }
            }
        });
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(int position, boolean isChecked);
    }
}
