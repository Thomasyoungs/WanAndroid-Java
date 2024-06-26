package com.pigeon.cloud.utils.wanpwd;

import androidx.annotation.Nullable;

import com.pigeon.cloud.utils.router.Param;
import com.pigeon.cloud.utils.router.RouterMap;
import com.pigeon.cloud.utils.router.Param;
import com.pigeon.cloud.utils.router.RouterMap;
import com.pigeon.cloud.utils.router.Param;
import com.pigeon.cloud.utils.router.RouterMap;

/**
 * @author yangzhikuan
 * @date 2019/12/28
 * 
 */
public class UserPageWanPwd implements IWanPwd {

    private final String content;
    private String mShowText;
    private String mBtnText;
    private Runnable mRunnable;

    public UserPageWanPwd(String content) {
        this.content = content;
        parse();
    }

    @Nullable
    @Override
    public Runnable getRunnable() {
        return mRunnable;
    }

    @Override
    public String getShowText() {
        return mShowText;
    }

    @Override
    public String getBtnText() {
        return mBtnText;
    }

    private void parse() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            if (c >= '0' && c <= '9') {
                stringBuilder.append(c);
            }
        }
        int userId = -1;
        try {
            userId = Integer.parseInt(stringBuilder.toString());
        } catch (Exception ignore) {
        }
        mShowText = "你发现了一个神秘用户！\n要不要去他主页看一下？";
        mBtnText = "去主页";
        int finalUserId = userId;
        mRunnable = new Runnable() {
            @Override
            public void run() {
                RouterMap.USER_PAGE.navigation(new Param("id", "" + finalUserId));
            }
        };
    }
}
