package com.pigeon.cloud.utils.wanpwd;

import androidx.annotation.Nullable;

import com.pigeon.basic.core.utils.AppInfoUtils;
import com.pigeon.basic.core.utils.AppOpenUtils;
import com.pigeon.basic.core.utils.Utils;

/**
 * @author yangzhikuan
 * @date 2019/12/28
 *
 */
public class QQWanPwd implements IWanPwd {

    private final String content;

    public QQWanPwd(String content) {
        this.content = content;
    }

    @Nullable
    @Override
    public Runnable getRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                if (AppInfoUtils.isAppInstalled(Utils.getAppContext(), AppInfoUtils.PackageName.QQ)) {
                    AppOpenUtils.openQQChat(Utils.getAppContext(), getQQ());
                }
            }
        };
    }

    @Override
    public String getShowText() {
        return "你发现了一个QQ号码！\n" + getQQ() + "\n是否立即启动QQ加个好友？";
    }

    @Override
    public String getBtnText() {
        return "加好友";
    }

    private String getQQ() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            if (c >= '0' && c <= '9') {
                s.append(c);
            }
        }
        return s.toString();
    }
}
