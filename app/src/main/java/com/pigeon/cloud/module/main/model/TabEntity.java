package com.pigeon.cloud.module.main.model;

/**
 * @author yangzhikuan
 * @date 2020/3/22
 */
public class TabEntity {
    private String tabName;
    private int tabIcon;
    private int msgCount;

    public TabEntity(String tabName, int tabIcon, int msgCount) {
        this.tabName = tabName;
        this.tabIcon = tabIcon;
        this.msgCount = msgCount;
    }

    public String getTabName() {
        return tabName;
    }

    public int getTabIcon() {
        return tabIcon;
    }

    public int getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(int msgCount) {
        this.msgCount = msgCount;
    }
}
