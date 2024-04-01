package com.pigeon.cloud.module.mine.presenter;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.pigeon.basic.core.base.BasePresenter;
import com.pigeon.basic.core.glide.GlideHelper;
import com.pigeon.basic.core.toast.ToastMaker;
import com.pigeon.basic.core.utils.AppInfoUtils;
import com.pigeon.basic.core.utils.AppOpenUtils;
import com.pigeon.basic.core.utils.CopyUtils;
import com.pigeon.basic.core.utils.bitmap.BitmapUtils;
import com.pigeon.basic.core.utils.listener.SimpleCallback;
import com.pigeon.cloud.http.RequestCallback;
import com.pigeon.cloud.module.mine.model.AboutMeBean;
import com.pigeon.cloud.module.mine.model.MineRequest;
import com.pigeon.cloud.module.mine.view.AboutMeView;

/**
 * @author yangzhikuan
 * @date 2019/5/23
 *
 */
public class AboutMePresenter extends BasePresenter<AboutMeView> {

    private AboutMeBean mAboutMeBean = null;

    public void getAboutMe() {
        MineRequest.getAboutMe(getRxLife(), new RequestCallback<AboutMeBean>() {
            @Override
            public void onSuccess(int code, AboutMeBean data) {
                mAboutMeBean = data;
                if (isAttach()) {
                    getBaseView().getAboutMeSuccess(code, data);
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                if (isAttach()) {
                    getBaseView().getAboutMeFailed(code, msg);
                }
            }
        });
    }

    public void openQQChat() {
        if (mAboutMeBean == null) {
            return;
        }
        if (AppInfoUtils.isAppInstalled(getContext(), AppInfoUtils.PackageName.QQ)) {
            if (AppOpenUtils.openQQChat(getContext(), mAboutMeBean.getQq())) {
                return;
            }
        }
        CopyUtils.copyText(mAboutMeBean.getQq());
        ToastMaker.showShort("QQ已复制");
    }

    public void openQQGroup() {
        if (mAboutMeBean == null) {
            return;
        }
        if (AppInfoUtils.isAppInstalled(getContext(), AppInfoUtils.PackageName.QQ)) {
            if (!TextUtils.isEmpty(mAboutMeBean.getQq_group_key())) {
                if (AppOpenUtils.joinQQGroup(getContext(), mAboutMeBean.getQq_group_key())) {
                    return;
                }
            }
        }
        CopyUtils.copyText(mAboutMeBean.getQq_group());
        ToastMaker.showShort("QQ群已复制");
    }

    public void saveQQQrcode() {
        if (mAboutMeBean == null) {
            return;
        }
        GlideHelper.with(getContext())
                .asBitmap()
                .load(mAboutMeBean.getQq_qrcode())
                .getBitmap(new SimpleCallback<Bitmap>() {
                    @Override
                    public void onResult(Bitmap data) {
                        if (BitmapUtils.saveGallery(data, mAboutMeBean.getName() + "_qq_qrcode_" + System.currentTimeMillis())) {
                            ToastMaker.showShort("保存成功");
                            AppOpenUtils.openQQ(getContext());
                        } else {
                            ToastMaker.showShort("保存失败");
                        }
                    }
                });
    }

    public void savZFBQrcode() {
        if (mAboutMeBean == null) {
            return;
        }
        GlideHelper.with(getContext())
                .asBitmap()
                .load(mAboutMeBean.getQq_qrcode())
                .getBitmap(new SimpleCallback<Bitmap>() {
                    @Override
                    public void onResult(Bitmap data) {
                        if (BitmapUtils.saveGallery(data, mAboutMeBean.getName() + "_qq_qrcode_" + System.currentTimeMillis())) {
                            ToastMaker.showShort("保存成功");
                            AppOpenUtils.openZFBScan(getContext());
                        } else {
                            ToastMaker.showShort("保存失败");
                        }
                    }
                });
    }

    public void saveWXQrcode() {
        if (mAboutMeBean == null) {
            return;
        }
        GlideHelper.with(getContext())
                .asBitmap()
                .load(mAboutMeBean.getWx_qrcode())
                .getBitmap(new SimpleCallback<Bitmap>() {
                    @Override
                    public void onResult(Bitmap data) {
                        if (BitmapUtils.saveGallery(data, mAboutMeBean.getName() + "_wx_qrcode_" + System.currentTimeMillis())) {
                            ToastMaker.showShort("保存成功");
                            AppOpenUtils.openWechatQRCode(getContext());
                        } else {
                            ToastMaker.showShort("保存失败");
                        }
                    }
                });
    }

}
