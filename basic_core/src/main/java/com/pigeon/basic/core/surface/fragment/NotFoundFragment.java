package com.pigeon.basic.core.surface.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.pigeon.basic.core.base.BaseFragment;
import com.pigeon.basic.core.mvp.MvpPresenter;

import com.pigeon.basic.core.R;
import com.pigeon.basic.core.base.BaseFragment;
import com.pigeon.basic.core.mvp.MvpPresenter;
import com.pigeon.basic.core.base.BaseFragment;
import com.pigeon.basic.core.mvp.MvpPresenter;

/**
 * 描述：
 *
 * @author yangzhikuan
 * @date 2019/3/29
 */
public class NotFoundFragment extends BaseFragment {

    public static NotFoundFragment create(String msg) {
        NotFoundFragment fragment = new NotFoundFragment();
        Bundle args = new Bundle(1);
        args.putString("msg", msg);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.basic_core_fragment_not_found;
    }

    @Override
    protected MvpPresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        TextView tvMsg = findViewById(R.id.basic_core_tv_msg);
        Bundle args = getArguments();
        if (args != null) {
            String msg = args.getString("msg");
            if (!TextUtils.isEmpty(msg)) {
                tvMsg.setText(msg);
            }
        }
    }

    @Override
    protected void loadData() {
    }
}
