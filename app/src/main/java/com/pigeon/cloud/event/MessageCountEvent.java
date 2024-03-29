package com.pigeon.cloud.event;

/**
 * @author yangzhikuan
 * @date 2019/5/17
 *
 */
public class MessageCountEvent extends BaseEvent {

    private int count;

    public static void post(int count) {
        new MessageCountEvent(count).post();
    }

    private MessageCountEvent(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
