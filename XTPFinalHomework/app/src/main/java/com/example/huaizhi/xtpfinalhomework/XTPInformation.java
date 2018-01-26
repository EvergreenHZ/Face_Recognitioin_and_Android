package com.example.huaizhi.xtpfinalhomework;

/**
 * Created by huaizhi on 1/6/18.
 */

public class XTPInformation {
    private  String time, name;
    private String id;
    public XTPInformation(String time, String name, String id) {
        this.time = time;
        this.name = name;
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setId(String id) {
        this.id = id;
    }
}
