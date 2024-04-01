package com.pigeon.basic.core.dialog;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.pigeon.basic.core.R;
import com.pigeon.basic.core.utils.DownloadUtils;
import com.pigeon.basic.core.utils.ResUtils;

import java.io.File;
import com.pigeon.basic.core.utils.file.FileUtils;

import per.goweii.anylayer.AnyLayer;
import per.goweii.anylayer.Layer;


/**
 * 版本更新弹窗
 *
 * @author yangzhikuan
 * @date 2018/8/6-上午9:17
 */
public class DownloadDialog {

    private final Activity mActivity;
    private Layer mAnyLayer = null;
    private final boolean isForce;
    private boolean isAutoInstall = true;

    private ProgressBar progressBar;
    private TextView tvProgress;
    private TextView tvApkSize;
    private TextView tvState;
    private File mApk;

    public static DownloadDialog with(Activity activity, boolean isForce, String url) {
        return new DownloadDialog(activity, isForce, url);
    }

    private DownloadDialog(Activity activity, boolean isForce, String url) {
        this.mActivity = activity;
        this.isForce = isForce;
        showDialog();
        startDownload(url);
    }

    public DownloadDialog setAutoInstall(boolean autoInstall) {
        isAutoInstall = autoInstall;
        return this;
    }

    private void startDownload(String url) {
        DownloadUtils.download(url, new DownloadUtils.DownloadListener() {
            @Override
            public void onPreExecute() {
                preDownload();
            }

            @Override
            public void onDownloadLength(int length) {
                if (tvApkSize != null) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvApkSize.setText(FileUtils.formatSize(length));
                        }
                    });
                }
            }

            @Override
            public void onProgressUpdate(int progress) {
                setProgress(progress);
            }

            @Override
            public void onPostExecute(File apk) {
                mApk = apk;
                if (isAutoInstall && tvState != null) {
                    tvState.performClick();
                }
            }
        });
    }

    private void showDialog() {
        mAnyLayer = AnyLayer.dialog(mActivity)
                .setContentView(R.layout.basic_ui_dialog_download)
                .setGravity(Gravity.CENTER)
                .setBackgroundDimDefault()
                .setCancelableOnTouchOutside(false)
                .setCancelableOnClickKeyBack(false)
                .addOnBindDataListener(new Layer.OnBindDataListener() {
                    @Override
                    public void onBindData(@NonNull Layer layer) {
                        progressBar = layer.requireView(R.id.basic_ui_pb_dialog_download);
                        tvProgress = layer.requireView(R.id.basic_ui_tv_dialog_download_progress);
                        tvApkSize = layer.requireView(R.id.basic_ui_tv_dialog_download_apk_size);
                        tvState = layer.requireView(R.id.basic_ui_tv_dialog_download_state);
                    }
                })
                .addOnClickListener(new Layer.OnClickListener() {
                    @Override
                    public void onClick(@NonNull Layer layer, @NonNull View v) {
                        if (mApk == null) {
                            return;
                        }
                        if (!isForce) {
                            dismiss();
                        }
                        DownloadUtils.installApk(mActivity, mApk);
                    }
                }, R.id.basic_ui_tv_dialog_download_state);
        mAnyLayer.show();
    }

    private void preDownload() {
        if (progressBar != null) {
            progressBar.setMax(100);
            progressBar.setProgress(0);
        }
        if (tvApkSize != null) {
            tvApkSize.setText("0B");
        }
        if (tvProgress != null) {
            tvProgress.setText("0");
        }
        if (tvState != null) {
            tvState.setText(R.string.basic_ui_dialog_download_state_downloading);
        }
    }

    private void setProgress(int progress) {
        if (progressBar != null) {
            progressBar.setProgress(progress);
        }
        if (tvProgress != null) {
            tvProgress.setText("" + progress);
        }
        if (progress >= 100) {
            if (tvState != null) {
                tvState.setText(R.string.basic_ui_dialog_download_state_install);
                tvState.setTextColor(ResUtils.getThemeColor(mActivity, R.attr.colorTextMain));
            }
        }
    }

    private void dismiss() {
        if (mAnyLayer != null) {
            mAnyLayer.dismiss();
        }
    }
}
