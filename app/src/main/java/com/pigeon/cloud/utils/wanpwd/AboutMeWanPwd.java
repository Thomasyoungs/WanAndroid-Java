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
public class AboutMeWanPwd implements IWanPwd {
    @Nullable
    @Override
    public Runnable getRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                RouterMap.ABOUT_ME.navigation();
            }
        };
    }

    @Override
    public String getShowText() {
        return "请开发小哥哥喝杯咖啡吧！\n一个完全免费的APP！\n一个这么好用还完全免费的APP！\n一个这么好看又好用还完全免费的APP！\n不请我喝杯咖啡提提神？\n我都快没精力继续维护了！";
    }

    @Override
    public String getBtnText() {
        return "去请客";
    }
}
