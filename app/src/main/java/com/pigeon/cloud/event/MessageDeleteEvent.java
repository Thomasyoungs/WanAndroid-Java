package com.pigeon.cloud.event;

import com.pigeon.cloud.module.mine.model.MessageBean;

/**
 * @author yangzhikuan
 * @date 2019/5/17
 * 
 */
public class MessageDeleteEvent extends BaseEvent {

    private MessageBean bean;

    public static void post(MessageBean bean) {
        new MessageDeleteEvent(bean).post();
    }

    private MessageDeleteEvent(MessageBean bean) {
        this.bean = bean;
    }

    public MessageBean getMessageBean() {
        return bean;
    }
}
