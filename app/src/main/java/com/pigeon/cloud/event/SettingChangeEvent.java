package com.pigeon.cloud.event;

/**
 * @author yangzhikuan
 * @date 2019/5/17
 * 
 */
public class SettingChangeEvent extends BaseEvent {

    private boolean showTopChanged;
    private boolean showBannerChanged;

    public SettingChangeEvent() {
    }

    @Override
    public void post() {
        if (showTopChanged || showBannerChanged) {
            super.post();
        }
    }

    public boolean isShowTopChanged() {
        return showTopChanged;
    }

    public void setShowTopChanged(boolean showTopChanged) {
        this.showTopChanged = showTopChanged;
    }

    public boolean isShowBannerChanged() {
        return showBannerChanged;
    }

    public void setShowBannerChanged(boolean showBannerChanged) {
        this.showBannerChanged = showBannerChanged;
    }
}
