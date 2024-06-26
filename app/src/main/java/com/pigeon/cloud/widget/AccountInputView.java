package com.pigeon.cloud.widget;

import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.pigeon.basic.core.utils.ResUtils;
import com.pigeon.cloud.R;

/**
 * @author yangzhikuan
 * @date 2019/5/15
 *
 */
public class AccountInputView extends InputView {

    private ImageView mIvAccountIcon;
    private ImageView mIvDeleteIcon;

    public AccountInputView(Context context) {
        super(context);
    }

    public AccountInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AccountInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initViews(AttributeSet attrs) {
        super.initViews(attrs);
        getEditText().setHint("请输入用户名");
        changeFocusMode(false);
    }

    @Override
    protected ImageView[] getLeftIcons() {
        mIvAccountIcon = new ImageView(getContext());
        mIvAccountIcon.setScaleType(ImageView.ScaleType.FIT_CENTER);
        mIvAccountIcon.setImageResource(R.drawable.ic_account_normal);
        return new ImageView[]{mIvAccountIcon};
    }

    @Override
    protected ImageView[] getRightIcons() {
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getContext().getResources().getDisplayMetrics());
        mIvDeleteIcon = new ImageView(getContext());
        mIvDeleteIcon.setVisibility(INVISIBLE);
        mIvDeleteIcon.setPadding(padding, padding, padding, padding);
        mIvDeleteIcon.setScaleType(ImageView.ScaleType.FIT_CENTER);
        mIvDeleteIcon.setColorFilter(ResUtils.getThemeColor(getContext(), R.attr.colorIconThird));
        mIvDeleteIcon.setImageResource(R.drawable.ic_delete);
        mIvDeleteIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getEditText().setText("");
            }
        });
        return new ImageView[]{mIvDeleteIcon};
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        super.onFocusChange(v, hasFocus);
        changeFocusMode(hasFocus);
    }

    private void changeFocusMode(boolean focus) {
        if (focus) {
            if (isEmpty()) {
                mIvDeleteIcon.setVisibility(INVISIBLE);
            } else {
                mIvDeleteIcon.setVisibility(VISIBLE);
            }
            mIvAccountIcon.setColorFilter(mViewColorFocus);
        } else {
            mIvDeleteIcon.setVisibility(INVISIBLE);
            mIvAccountIcon.setColorFilter(mViewColorNormal);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        super.afterTextChanged(s);
        if (isEmpty()) {
            mIvDeleteIcon.setVisibility(INVISIBLE);
        } else {
            mIvDeleteIcon.setVisibility(VISIBLE);
        }
    }
}
