package com.goodsoft.society_zy.domain.entity.resident;

import java.io.Serializable;

/**
 * 辖区派出所信息实体
 * Created by 龙宏 on 2017/11/4.
 */
public class AreaInfo implements Serializable {
    private String id;
    private String name;
    private String code;
    private String time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
