package com.pigeon.cloud.event;

/**
 * @author yangzhikuan
 * @date 2019/5/17
 *
 */
public class BannerAutoSwitchEnableEvent extends BaseEvent {
    private final boolean enable;

    public BannerAutoSwitchEnableEvent(boolean enable) {
        this.enable = enable;
    }

    public boolean isEnable() {
        return enable;
    }
}
