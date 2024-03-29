package com.pigeon.cloud.common;

import com.pigeon.basic.core.base.BaseApp;
import com.pigeon.basic.utils.InitTaskRunner;
import com.pigeon.basic.core.base.BaseApp;
import com.pigeon.basic.utils.InitTaskRunner;
import com.pigeon.basic.core.base.BaseApp;
import com.pigeon.basic.utils.InitTaskRunner;

/**
 * @author yangzhikuan
 * @date 2019/5/12
 *
 */
public class WanApp extends BaseApp {
    @Override
    public void onCreate() {
        super.onCreate();
        new InitTaskRunner(this)
                .add(new SmartRefreshInitTask())
                .add(new CookieManagerInitTask())
                .add(new NightModeInitTask())
                .add(new ThemeInitTask())
                .add(new GrayFilterInitTask())
                .add(new SwipeBackInitTask())
                .add(new CoreInitTask())
                .add(new RxHttpInitTask())
                .add(new WanDbInitTask())
                .add(new WanCacheInitTask())
                .add(new BlurredInitTask())
                .add(new X5InitTask())
                .add(new BuglyInitTask())
                .add(new CrashInitTask())
                .add(new ReadingModeTask())
                .run();

    }
}
