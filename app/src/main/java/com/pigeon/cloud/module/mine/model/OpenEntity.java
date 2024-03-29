package com.pigeon.cloud.module.mine.model;

import com.pigeon.basic.core.base.BaseEntity;
import com.pigeon.basic.core.base.BaseEntity;
import com.pigeon.basic.core.base.BaseEntity;

/**
 * @author yangzhikuan
 * @date 2019/5/18
 * 
 */
public class OpenEntity extends BaseEntity {

    private String project;
    private String description;
    private String link;

    public OpenEntity(String project, String description, String link) {
        this.project = project;
        this.description = description;
        this.link = link;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
