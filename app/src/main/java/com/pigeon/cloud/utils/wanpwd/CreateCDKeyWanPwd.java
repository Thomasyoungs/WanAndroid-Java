package com.pigeon.cloud.utils.wanpwd;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.pigeon.basic.ui.toast.ToastMaker;
import com.pigeon.basic.utils.CopyUtils;
import com.pigeon.cloud.utils.UserUtils;
import com.pigeon.cloud.utils.cdkey.CDKeyUtils;
import com.pigeon.basic.ui.toast.ToastMaker;
import com.pigeon.basic.utils.CopyUtils;
import com.pigeon.cloud.BuildConfig;
import com.pigeon.cloud.utils.UserUtils;
import com.pigeon.cloud.utils.cdkey.CDKeyUtils;
import com.pigeon.basic.ui.toast.ToastMaker;
import com.pigeon.basic.utils.CopyUtils;
import com.pigeon.cloud.utils.UserUtils;
import com.pigeon.cloud.utils.cdkey.CDKeyUtils;

/**
 * @author yangzhikuan
 * @date 2020/1/1
 *
 */
public class CreateCDKeyWanPwd implements IWanPwd {

    private final Runnable mRunnable;

    public CreateCDKeyWanPwd(String content) {
        mRunnable = new Runnable() {
            @SuppressWarnings("StringBufferReplaceableByString")
            @Override
            public void run() {
                if (!UserUtils.getInstance().isLogin()) {
                    ToastMaker.showShort("请登录后使用该功能");
                    return;
                }
                int id = UserUtils.getInstance().getWanId();
                if (!TextUtils.equals(String.valueOf(id), BuildConfig.DEVELOPER_ID)) {
                    ToastMaker.showShort("该功能仅限开发者账号使用");
                    return;
                }
                String cdkey = CDKeyUtils.getInstance().create(content);
                StringBuilder s = new StringBuilder();
                s.append("【玩口令】这是一个激活码口令，仅限特定账号使用，户制泽条消息");
                s.append(String.format(BuildConfig.WANPWD_FORMAT, BuildConfig.WANPWD_TYPE_CDKEY, cdkey));
                s.append("打開最美玩安卓客户端激活");
                CopyUtils.copyText(s.toString());
                ToastMaker.showShort("口令已复制");
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
        return "###激活码生成###\n\n！！！警告！！！\n\n该功能仅限开发者使用";
    }

    @Override
    public String getBtnText() {
        return "复制";
    }
}
