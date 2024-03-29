package com.pigeon.cloud.utils.wanpwd;

import androidx.annotation.Nullable;

import com.pigeon.cloud.utils.router.RouterMap;
import com.pigeon.cloud.utils.router.RouterMap;
import com.pigeon.cloud.utils.router.RouterMap;

/**
 * @author yangzhikuan
 * @date 2019/12/28
 *
 */
public class UnknownWanPwd implements IWanPwd {
    @Nullable
    @Override
    public Runnable getRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                RouterMap.SETTING.navigation();
            }
        };
    }

    @Override
    public String getShowText() {
        return "你发现了一个神秘口令！\n可惜当前版本不支持，快去设置中更新版本再试试吧！";
    }

    @Override
    public String getBtnText() {
        return "去更新";
    }
}
