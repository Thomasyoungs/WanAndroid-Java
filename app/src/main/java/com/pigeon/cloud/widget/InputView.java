package com.pigeon.cloud.widget;

import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.pigeon.basic.core.utils.ResUtils;
import com.pigeon.cloud.R;

/**
 * @author yangzhikuan
 * @date 2019/5/15
 *
 */
public class InputView extends FrameLayout implements View.OnFocusChangeListener, TextWatcher, SubmitView.EditTextWrapper {

    private EditText mEditText;
    private View mBottomLine;
    private int mViewHeightFocus;
    protected int mViewColorFocus;
    private int mViewHeightNormal;
    protected int mViewColorNormal;
    private boolean isEmpty = true;

    public InputView(Context context) {
        this(context, null);
    }

    public InputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(attrs);
    }

    @Override
    public EditText getEditText() {
        return mEditText;
    }

    public View getBottomLine() {
        return mBottomLine;
    }

    public String getText() {
        return mEditText.getText().toString().trim();
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    protected void initViews(AttributeSet attrs) {
        int icIconSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 18, getContext().getResources().getDisplayMetrics());
        int icIconMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getContext().getResources().getDisplayMetrics());
        ImageView[] ivIconLefts = getLeftIcons();
        int ivIconLeftCount = ivIconLefts != null ? ivIconLefts.length : 0;
        for (int i = 0; i < ivIconLeftCount; i++) {
            ImageView ivIconLeft = ivIconLefts[i];
            LayoutParams ivIconLeftParams = new LayoutParams(icIconSize, icIconSize);
            ivIconLeftParams.leftMargin = (icIconSize + icIconMargin) * i;
            ivIconLeftParams.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
            addView(ivIconLeft, ivIconLeftParams);
        }
        ImageView[] ivIconRights = getRightIcons();
        int ivIconRightCount = ivIconRights != null ? ivIconRights.length : 0;
        for (int i = 0; i < ivIconRightCount; i++) {
            ImageView ivIconRight = ivIconRights[i];
            LayoutParams ivIconRightParams = new LayoutParams(icIconSize, icIconSize);
            ivIconRightParams.rightMargin = (icIconSize + icIconMargin) * i;
            ivIconRightParams.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
            addView(ivIconRight, ivIconRightParams);
        }

        int etMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getContext().getResources().getDisplayMetrics());
        mEditText = new EditText(getContext());
        LayoutParams etParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        etParams.leftMargin = icIconSize * ivIconLeftCount + icIconMargin * (ivIconLeftCount - 1) + etMargin;
        etParams.rightMargin = icIconSize * ivIconRightCount + icIconMargin * (ivIconRightCount - 1) + etMargin;
        mEditText.setLayoutParams(etParams);
        mEditText.setBackgroundColor(Color.TRANSPARENT);
        mEditText.setTextColor(ResUtils.getThemeColor(getContext(), R.attr.colorTextSurface));
        mEditText.setHintTextColor(ResUtils.getThemeColor(getContext(), R.attr.colorTextThird));
        mEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getContext().getResources().getDimension(R.dimen.text_content));
        mEditText.setSingleLine();
        mEditText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        mEditText.setOnFocusChangeListener(this);
        mEditText.addTextChangedListener(this);
        addView(mEditText);

        mViewColorNormal = ResUtils.getThemeColor(getContext(), R.attr.colorIconThird);
        mViewHeightNormal = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getContext().getResources().getDisplayMetrics());
        mViewColorFocus = ResUtils.getThemeColor(getContext(), R.attr.colorIconMain);
        mViewHeightFocus = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getContext().getResources().getDisplayMetrics());

        mBottomLine = new View(getContext());
        LayoutParams vParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mViewHeightNormal);
        vParams.gravity = Gravity.BOTTOM;
        mBottomLine.setLayoutParams(vParams);
        mBottomLine.setBackgroundColor(mViewColorNormal);
        addView(mBottomLine);
    }

    protected ImageView[] getLeftIcons() {
        return null;
    }

    protected ImageView[] getRightIcons() {
        return null;
    }

    private void changeBottomStyle(final boolean hasFocus) {
        final int height;
        final int color;
        if (hasFocus) {
            color = mViewColorFocus;
            height = mViewHeightFocus;
        } else {
            color = mViewColorNormal;
            height = mViewHeightNormal;
        }
        mBottomLine.setBackgroundColor(color);
        mBottomLine.getLayoutParams().height = height;
        mBottomLine.requestLayout();
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        changeBottomStyle(hasFocus);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(android.text.Editable s) {
        isEmpty = s.toString().length() == 0;
    }
}
