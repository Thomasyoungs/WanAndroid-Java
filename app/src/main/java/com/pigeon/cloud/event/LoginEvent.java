package com.pigeon.cloud.event;

/**
 * @author yangzhikuan
 * @date 2019/5/17
 *
 */
public class LoginEvent extends BaseEvent {

    private boolean login;

    public LoginEvent(boolean login) {
        this.login = login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public boolean isLogin() {
        return login;
    }
}
