package com.pigeon.cloud.event;

import org.greenrobot.eventbus.EventBus;

/**
 * @author yangzhikuan
 * @date 2019/5/18
 * 
 */
public class BaseEvent {

    public void post() {
        EventBus.getDefault().post(this);
    }

    public void postSticky() {
        EventBus.getDefault().postSticky(this);
    }

    public void removeSticky() {
        EventBus.getDefault().removeStickyEvent(this);
    }

}
