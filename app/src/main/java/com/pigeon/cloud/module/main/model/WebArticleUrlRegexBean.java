package com.pigeon.cloud.module.main.model;

import per.goweii.rxhttp.request.base.BaseBean;

/**
 * @author yangzhikuan
 * @date 2019/5/19
 * 
 */
public class WebArticleUrlRegexBean extends BaseBean {
    private String name;
    private String host;
    private String regex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }
}
