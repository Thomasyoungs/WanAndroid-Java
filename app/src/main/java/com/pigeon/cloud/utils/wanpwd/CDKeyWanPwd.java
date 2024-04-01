package com.pigeon.cloud.utils.wanpwd;

import androidx.annotation.Nullable;

import com.pigeon.cloud.utils.UserUtils;
import com.pigeon.cloud.utils.cdkey.CDKeyUtils;
import com.pigeon.basic.core.toast.ToastMaker;

/**
 * @author yangzhikuan
 * @date 2020/1/1
 * 
 */
public class CDKeyWanPwd implements IWanPwd {

    private final Runnable mRunnable;

    public CDKeyWanPwd(String content) {
        mRunnable = new Runnable() {
            @Override
            public void run() {
                if (!UserUtils.getInstance().isLogin()) {
                    ToastMaker.showShort("请先登录");
                    return;
                }
                int id = UserUtils.getInstance().getWanId();
                if (CDKeyUtils.getInstance().check(String.valueOf(id), content)) {
                    CDKeyUtils.getInstance().set(content);
                    ToastMaker.showShort("激活成功，重启APP后生效");
                } else {
                    ToastMaker.showShort("激活码无效");
                }
            }
        };
    }

    @Nullable
    @Override
    public Runnable getRunnable() {
        return mRunnable;
    }

    @Override
    public String getShowText() {
        return "你发现了一个激活码！\n激活码仅与当前登录账户绑定，更换设备或账户后需重新激活，成功激活后将会去除所有广告，是否立即激活？";
    }

    @Override
    public String getBtnText() {
        return "激活";
    }
}
