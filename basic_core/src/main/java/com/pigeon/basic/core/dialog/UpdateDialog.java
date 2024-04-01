package com.pigeon.basic.core.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import per.goweii.anylayer.AnyLayer;
import per.goweii.anylayer.Layer;
import com.pigeon.basic.core.R;
import com.pigeon.basic.core.utils.ResUtils;

/**
 * 版本更新弹窗
 *
 * @author yangzhikuan
 * @date 2018/8/6-上午9:17
 */
public class UpdateDialog {

    private final Context mContext;

    private boolean mTest = false;
    private String mUrl = null;
    private String mUrlBackup = null;
    private int mVersionCode = 0;
    private String mVersionName = null;
    private String mTime = null;
    private String mDescription = null;
    private boolean mForce = false;

    private OnDismissListener mOnDismissListener = null;
    private OnUpdateListener mOnUpdateListener = null;

    public static UpdateDialog with(Context context) {
        return new UpdateDialog(context);
    }

    private UpdateDialog(Context context) {
        this.mContext = context;
    }

    public UpdateDialog setTest(boolean test) {
        mTest = test;
        return this;
    }

    public UpdateDialog setUrl(String url) {
        mUrl = url;
        return this;
    }

    public UpdateDialog setUrlBackup(String url) {
        mUrlBackup = url;
        return this;
    }

    public UpdateDialog setForce(boolean force) {
        mForce = force;
        return this;
    }

    public UpdateDialog setVersionCode(int versionCode) {
        mVersionCode = versionCode;
        return this;
    }

    public UpdateDialog setVersionName(String versionName) {
        this.mVersionName = versionName;
        return this;
    }

    public UpdateDialog setTime(String time) {
        mTime = time;
        return this;
    }

    public UpdateDialog setDescription(String description) {
        this.mDescription = description;
        return this;
    }

    public UpdateDialog setOnUpdateListener(OnUpdateListener onUpdateListener) {
        mOnUpdateListener = onUpdateListener;
        return this;
    }

    public UpdateDialog setOnDismissListener(OnDismissListener onDismissListener) {
        mOnDismissListener = onDismissListener;
        return this;
    }

    public void show() {
        AnyLayer.dialog(mContext)
                .setContentView(R.layout.basic_ui_dialog_update)
                .setBackgroundDimDefault()
                .setGravity(Gravity.CENTER)
                .setCancelableOnTouchOutside(!mForce)
                .setCancelableOnClickKeyBack(!mForce)
                .addOnBindDataListener(new Layer.OnBindDataListener() {
                    @Override
                    public void onBindData(@NonNull Layer layer) {
                        final TextView tvTitle = layer.requireView(R.id.basic_ui_tv_dialog_update_title);
                        final View vLine = layer.requireView(R.id.basic_ui_v_dialog_update_line);
                        final TextView tvNo = layer.requireView(R.id.basic_ui_tv_dialog_update_no);
                        final TextView tvVersionName = layer.requireView(R.id.basic_ui_tv_dialog_update_version_name);
                        final TextView tvTime = layer.requireView(R.id.basic_ui_tv_dialog_update_time);
                        final TextView tvDescription = layer.requireView(R.id.basic_ui_tv_dialog_update_description);
                        if (mTest) {
                            tvTitle.setTextColor(ResUtils.getThemeColor(tvTitle.getContext(), R.attr.colorTextAccent));
                            tvTitle.setText(R.string.basic_ui_dialog_update_test_title);
                        } else {
                            tvTitle.setTextColor(ResUtils.getThemeColor(tvTitle.getContext(), R.attr.colorTextMain));
                            tvTitle.setText(R.string.basic_ui_dialog_update_title);
                        }
                        if (TextUtils.isEmpty(mVersionName)) {
                            tvVersionName.setVisibility(View.GONE);
                        } else {
                            tvVersionName.setVisibility(View.VISIBLE);
                            tvVersionName.setText(mVersionName);
                        }
                        if (TextUtils.isEmpty(mTime)) {
                            tvTime.setVisibility(View.GONE);
                        } else {
                            tvTime.setVisibility(View.VISIBLE);
                            tvTime.setText(mTime);
                        }
                        tvDescription.setText(mDescription);
                        //tvDescription.setMovementMethod(ScrollingMovementMethod.getInstance());
                        if (mForce) {
                            tvNo.setVisibility(View.GONE);
                            vLine.setVisibility(View.GONE);
                        } else {
                            tvNo.setVisibility(View.VISIBLE);
                            vLine.setVisibility(View.VISIBLE);
                        }
                    }
                })
                .addOnClickToDismissListener(new Layer.OnClickListener() {
                    @Override
                    public void onClick(@NonNull Layer layer, @NonNull View v) {
                        if (mOnUpdateListener != null) {
                            mOnUpdateListener.onDownload(mUrl, mUrlBackup, mForce);
                        }
                    }
                }, R.id.basic_ui_tv_dialog_update_yes)
                .addOnClickToDismissListener(new Layer.OnClickListener() {
                    @Override
                    public void onClick(@NonNull Layer layer, @NonNull View v) {
                        if (mOnUpdateListener != null) {
                            mOnUpdateListener.onIgnore(mVersionName, mVersionCode);
                        }
                    }
                }, R.id.basic_ui_tv_dialog_update_no)
                .addOnDismissListener(new Layer.OnDismissListener() {
                    @Override
                    public void onPreDismiss(@NonNull Layer layer) {

                    }

                    @Override
                    public void onPostDismiss(@NonNull Layer layer) {
                        if (mOnDismissListener != null) {
                            mOnDismissListener.onDismiss();
                        }
                    }
                })
                .show();
    }

    public interface OnUpdateListener {
        void onDownload(String url, String urlBackup, boolean isForce);

        void onIgnore(String versionName, int versionCode);
    }

    public interface OnDismissListener {
        void onDismiss();
    }
}
