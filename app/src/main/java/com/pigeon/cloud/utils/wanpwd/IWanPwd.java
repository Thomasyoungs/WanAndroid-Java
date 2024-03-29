package com.pigeon.cloud.utils.wanpwd;

import androidx.annotation.Nullable;

/**
 * @author yangzhikuan
 * @date 2019/12/28
 *
 */
public interface IWanPwd {
    @Nullable
    Runnable getRunnable();

    String getShowText();

    String getBtnText();
}
